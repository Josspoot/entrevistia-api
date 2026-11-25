package mx.edu.tecdesoftware.entrevistia.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesoftware.entrevistia.domain.Resume;
import mx.edu.tecdesoftware.entrevistia.domain.ResumeReview;
import mx.edu.tecdesoftware.entrevistia.domain.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * CVs del usuario autenticado. El uid sale del token de Firebase
 * (jwt.getSubject()), nunca de lo que mande la app.
 */
@RestController
@RequestMapping("/resumes")
@CrossOrigin(origins = "*")
@Tag(name = "Resume", description = "Upload a CV in PDF and get an AI review")
@SecurityRequirement(name = "firebase")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("")
    @Operation(summary = "List my CVs")
    @ApiResponse(responseCode = "200", description = "CVs of the authenticated user")
    @ApiResponse(responseCode = "401", description = "Missing or invalid Firebase token")
    public List<Resume> getAll(@AuthenticationPrincipal Jwt jwt) {
        return resumeService.getAll(jwt.getSubject());
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a CV", description = "PDF only, up to 5 MB and 10 pages")
    @ApiResponse(responseCode = "201", description = "CV uploaded and text extracted")
    @ApiResponse(responseCode = "400", description = "Not a PDF, too big or without text")
    public ResponseEntity<Resume> upload(@AuthenticationPrincipal Jwt jwt,
                                         @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.upload(jwt.getSubject(), file));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a CV and its reviews")
    @ApiResponse(responseCode = "204", description = "CV deleted")
    @ApiResponse(responseCode = "404", description = "CV not found")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int id) {
        return resumeService.delete(jwt.getSubject(), id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/reviews")
    @Operation(summary = "Review a CV", description = "Optionally against a vacancy to measure the match")
    @ApiResponse(responseCode = "201", description = "Review created")
    @ApiResponse(responseCode = "404", description = "CV not found")
    @ApiResponse(responseCode = "503", description = "AI not available")
    public ResponseEntity<ResumeReview> review(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("id") int id,
            @Parameter(description = "Vacancy to compare against", example = "1")
            @RequestParam(name = "vacancyId", required = false) Integer vacancyId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.review(jwt.getSubject(), id, vacancyId));
    }

    @GetMapping("/{id}/reviews/latest")
    @Operation(summary = "Get the latest review of a CV")
    @ApiResponse(responseCode = "200", description = "Latest review")
    @ApiResponse(responseCode = "404", description = "CV not found or not reviewed yet")
    public ResponseEntity<ResumeReview> latest(@AuthenticationPrincipal Jwt jwt, @PathVariable("id") int id) {
        return resumeService.latestReview(jwt.getSubject(), id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

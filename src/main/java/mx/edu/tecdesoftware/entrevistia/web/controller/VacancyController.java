package mx.edu.tecdesoftware.entrevistia.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.edu.tecdesoftware.entrevistia.domain.Vacancy;
import mx.edu.tecdesoftware.entrevistia.domain.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
@CrossOrigin(origins = "*")
@Tag(name = "Vacancy", description = "Manage the job vacancies catalog")
public class VacancyController {

    private static final String EXAMPLE = """
            {
              "title": "Desarrollador Flutter Jr",
              "company": "Nova Labs",
              "description": "Desarrollo de apps móviles multiplataforma.",
              "requirements": "Dart, Flutter, APIs REST, Git.",
              "categoryId": 1,
              "salary": 18000.00,
              "openings": 3,
              "mode": "Híbrido",
              "location": "Mérida, Yuc.",
              "imageUrl": null,
              "active": true
            }
            """;

    @Autowired
    private VacancyService vacancyService;

    @GetMapping("")
    @Operation(summary = "List vacancies",
            description = "Return all vacancies. Optionally filter by text in the title and/or by category")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of vacancies")
    public ResponseEntity<List<Vacancy>> getAll(
            @Parameter(description = "Text to search in the title", example = "desarrollador")
            @RequestParam(name = "search", required = false) String search,
            @Parameter(description = "Category ID to filter by", example = "1")
            @RequestParam(name = "categoryId", required = false) Integer categoryId) {
        return ResponseEntity.ok(vacancyService.search(search, categoryId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one vacancy", description = "Return the vacancy with the given ID")
    @ApiResponse(responseCode = "200", description = "Vacancy found")
    @ApiResponse(responseCode = "404", description = "Vacancy not found")
    public ResponseEntity<Vacancy> getVacancy(
            @Parameter(description = "Vacancy ID", example = "1", required = true)
            @PathVariable("id") int vacancyId) {
        return vacancyService.getVacancy(vacancyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @SecurityRequirement(name = "firebase")
    @ApiResponse(responseCode = "401", description = "Login required")
    @Operation(summary = "Create a vacancy", description = "Register a new vacancy and return it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(examples = @ExampleObject(name = "Example vacancy", value = EXAMPLE))))
    @ApiResponse(responseCode = "201", description = "Vacancy created")
    @ApiResponse(responseCode = "400", description = "Invalid vacancy data")
    public ResponseEntity<Vacancy> create(@Valid @RequestBody Vacancy vacancy) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyService.create(vacancy));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "firebase")
    @ApiResponse(responseCode = "401", description = "Login required")
    @Operation(summary = "Update a vacancy", description = "Replace the data of an existing vacancy",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(examples = @ExampleObject(name = "Example vacancy", value = EXAMPLE))))
    @ApiResponse(responseCode = "200", description = "Vacancy updated")
    @ApiResponse(responseCode = "400", description = "Invalid vacancy data")
    @ApiResponse(responseCode = "404", description = "Vacancy not found")
    public ResponseEntity<Vacancy> update(
            @Parameter(description = "Vacancy ID", example = "1", required = true)
            @PathVariable("id") int vacancyId,
            @Valid @RequestBody Vacancy vacancy) {
        return vacancyService.update(vacancyId, vacancy)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "firebase")
    @ApiResponse(responseCode = "401", description = "Login required")
    @Operation(summary = "Delete a vacancy", description = "Remove the vacancy with the given ID")
    @ApiResponse(responseCode = "204", description = "Vacancy deleted")
    @ApiResponse(responseCode = "404", description = "Vacancy not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Vacancy ID", example = "1", required = true)
            @PathVariable("id") int vacancyId) {
        if (vacancyService.delete(vacancyId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

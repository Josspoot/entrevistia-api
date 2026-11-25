package mx.edu.tecdesoftware.entrevistia.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mx.edu.tecdesoftware.entrevistia.domain.AssistantReply;
import mx.edu.tecdesoftware.entrevistia.domain.AssistantRequest;
import mx.edu.tecdesoftware.entrevistia.domain.service.AssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assistant")
@CrossOrigin(origins = "*")
@SecurityRequirement(name = "firebase")
@Tag(name = "Assistant", description = "AI assistant that answers questions about the vacancies catalog")
public class AssistantController {

    private static final String EXAMPLE = """
            {
              "messages": [
                { "role": "user", "text": "¿Qué vacante de tecnología paga más?" }
              ]
            }
            """;

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/messages")
    @Operation(summary = "Ask the assistant",
            description = "Send the conversation; the catalog is injected into the prompt and the answer only uses real vacancies",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(examples = @ExampleObject(name = "Question", value = EXAMPLE))))
    @ApiResponse(responseCode = "200", description = "Assistant answer")
    @ApiResponse(responseCode = "400", description = "Invalid conversation")
    @ApiResponse(responseCode = "429", description = "AI quota reached")
    @ApiResponse(responseCode = "503", description = "AI not configured or not available")
    public ResponseEntity<AssistantReply> ask(@Valid @RequestBody AssistantRequest request) {
        return ResponseEntity.ok(assistantService.ask(request.messages()));
    }
}

package mx.edu.tecdesoftware.entrevistia.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Un mensaje del chat con el asistente.
 * role: "user" (la persona) o "assistant" (la IA).
 */
public record AssistantMessage(
        @NotBlank @Pattern(regexp = "user|assistant") String role,
        @NotBlank @Size(max = 1000) String text) {
}

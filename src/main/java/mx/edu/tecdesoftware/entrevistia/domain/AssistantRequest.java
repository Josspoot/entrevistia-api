package mx.edu.tecdesoftware.entrevistia.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * La app manda la conversación completa (el servidor no guarda historial).
 * Se limita a 20 mensajes para controlar el consumo de la IA.
 */
public record AssistantRequest(
        @NotEmpty @Size(max = 20) List<@Valid AssistantMessage> messages) {
}

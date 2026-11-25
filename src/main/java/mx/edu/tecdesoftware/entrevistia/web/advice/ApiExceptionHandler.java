package mx.edu.tecdesoftware.entrevistia.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Respuestas de error con un mensaje claro para la app:
 * { "status": 400, "message": "...", "fields": { "salary": "..." } }
 * Solo se exponen mensajes escritos por nosotros, nunca detalles internos.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Map<String, Object>> status(ResponseStatusException e) {
        return body(HttpStatus.valueOf(e.getStatusCode().value()), e.getReason(), null);
    }

    /** Errores de @Valid: un mensaje por campo. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, Object>> validation(MethodArgumentNotValidException e) {
        Map<String, String> fields = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(f -> fields.putIfAbsent(f.getField(), f.getDefaultMessage()));
        return body(HttpStatus.BAD_REQUEST, "Datos inválidos", fields);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ResponseEntity<Map<String, Object>> tooBig() {
        return body(HttpStatus.PAYLOAD_TOO_LARGE, "El PDF pesa más de 5 MB", null);
    }

    private ResponseEntity<Map<String, Object>> body(HttpStatus status, String message,
                                                     Map<String, String> fields) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", message == null ? status.getReasonPhrase() : message);
        if (fields != null) body.put("fields", fields);
        return ResponseEntity.status(status).body(body);
    }
}

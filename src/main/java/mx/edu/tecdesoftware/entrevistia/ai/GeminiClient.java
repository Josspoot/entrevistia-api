package mx.edu.tecdesoftware.entrevistia.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * Cliente REST mínimo de la API de Gemini (generateContent).
 * La llave vive solo aquí, en el servidor: nunca viaja a la app.
 */
@Component
public class GeminiClient {

    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta";

    private final RestClient restClient;
    private final String apiKey;
    private final String model;

    public GeminiClient(@Value("${gemini.api-key:}") String apiKey,
                        @Value("${gemini.model:gemini-flash-latest}") String model) {
        this.apiKey = apiKey;
        this.model = model;
        var timeouts = new SimpleClientHttpRequestFactory();
        timeouts.setConnectTimeout(Duration.ofSeconds(10));
        timeouts.setReadTimeout(Duration.ofSeconds(40));
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .requestFactory(timeouts)
                .build();
    }

    /**
     * Envía instrucciones de sistema + historial y devuelve el texto de la respuesta.
     * Con {@code responseSchema} Gemini responde JSON que cumple ese esquema.
     */
    public String generate(String systemInstruction, List<Content> history,
                           Map<String, Object> responseSchema) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "El asistente no está configurado (falta gemini.api-key)");
        }

        var request = new Request(
                new Content("user", List.of(new Part(systemInstruction))),
                history,
                new GenerationConfig(0.4, "application/json", responseSchema));

        try {
            Response response = restClient.post()
                    .uri("/models/{model}:generateContent", model)
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(Response.class);
            return firstText(response);
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Se alcanzó el límite de uso de la IA. Intenta en un minuto.");
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "La IA no respondió. Intenta de nuevo.", e);
        }
    }

    private String firstText(Response response) {
        if (response == null || response.candidates() == null || response.candidates().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "La IA no devolvió respuesta");
        }
        var content = response.candidates().getFirst().content();
        if (content == null || content.parts() == null || content.parts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "La IA no devolvió respuesta");
        }
        return content.parts().getFirst().text();
    }

    // ── Formato JSON de la API de Gemini ──────────────────────
    // role: "user" (persona) o "model" (Gemini)
    public record Content(String role, List<Part> parts) {
        public static Content of(String role, String text) {
            return new Content(role, List.of(new Part(text)));
        }
    }

    public record Part(String text) {}

    record GenerationConfig(double temperature, String responseMimeType,
                            Map<String, Object> responseSchema) {}

    record Request(Content systemInstruction, List<Content> contents,
                   GenerationConfig generationConfig) {}

    record Response(List<Candidate> candidates) {}

    record Candidate(Content content) {}
}

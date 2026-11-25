package mx.edu.tecdesoftware.entrevistia.domain;

import java.time.OffsetDateTime;

/** CV subido por el usuario (sin el texto, que no se devuelve a la app). */
public record Resume(Integer resumeId, String fileName, int pages, OffsetDateTime createdAt) {
}

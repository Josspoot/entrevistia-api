package mx.edu.tecdesoftware.entrevistia.domain;

import java.time.OffsetDateTime;

/**
 * Revisión de un CV: puntaje de 0 a 100 (calculado por el backend),
 * su desglose y el análisis de Gemini.
 */
public record ResumeReview(
        Integer reviewId,
        Integer resumeId,
        Integer vacancyId,
        String vacancyTitle,
        int score,
        ScoreBreakdown breakdown,
        ResumeAnalysis analysis,
        OffsetDateTime createdAt) {

    /** Puntos obtenidos y máximos por bloque. match es null si no hay vacante. */
    public record ScoreBreakdown(int structure, int structureMax,
                                 int quality, int qualityMax,
                                 Integer match, Integer matchMax) {
    }

    public ResumeReview withIdAndDate(Integer id, OffsetDateTime date) {
        return new ResumeReview(id, resumeId, vacancyId, vacancyTitle, score,
                breakdown, analysis, date);
    }
}

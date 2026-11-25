package mx.edu.tecdesoftware.entrevistia.domain;

import java.util.List;

/**
 * Lo que DETECTA Gemini al leer un CV. Gemini no calcula el puntaje:
 * solo reporta evidencia; el puntaje lo calcula ResumeScorer.
 */
public record ResumeAnalysis(
        Sections sections,
        Quality quality,
        List<String> strengths,
        List<Improvement> improvements,
        List<String> skills,
        List<RequirementCheck> requirements) {

    /** Secciones presentes en el CV. */
    public record Sections(boolean contact, boolean summary, boolean experience,
                           boolean education, boolean skills, boolean projects,
                           boolean languages) {
    }

    /** Nivel de 1 (malo) a 5 (excelente) en cada criterio de calidad. */
    public record Quality(int achievements, int clarity, int spelling) {
    }

    /** Algo que mejorar y una sugerencia concreta para hacerlo. */
    public record Improvement(String issue, String suggestion) {
    }

    /** Si el CV demuestra un requisito de la vacante, con la cita como evidencia. */
    public record RequirementCheck(String requirement, boolean met, String evidence) {
    }
}

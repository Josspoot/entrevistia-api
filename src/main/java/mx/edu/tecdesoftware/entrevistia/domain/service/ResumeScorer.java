package mx.edu.tecdesoftware.entrevistia.domain.service;

import mx.edu.tecdesoftware.entrevistia.domain.ResumeAnalysis;
import mx.edu.tecdesoftware.entrevistia.domain.ResumeReview.ScoreBreakdown;
import org.springframework.stereotype.Component;

/**
 * Calcula el puntaje de un CV con una fórmula fija.
 * Gemini solo detecta evidencia; aquí se decide cuánto vale cada cosa,
 * así el mismo análisis siempre da el mismo puntaje.
 *
 * <pre>
 * Estructura  40 pts: secciones presentes (experiencia pesa más)
 * Calidad     30 pts: logros 12, claridad 10, ortografía 8
 *                     cada uno con nivel 1-5 → (nivel - 1) / 4 × peso
 * Ajuste      30 pts: requisitos de la vacante demostrados / total
 *
 * Sin vacante el ajuste no aplica y el total se escala sobre 70.
 * </pre>
 */
@Component
public class ResumeScorer {

    static final int STRUCTURE_MAX = 40;
    static final int QUALITY_MAX = 30;
    static final int MATCH_MAX = 30;

    public ScoreBreakdown breakdown(ResumeAnalysis analysis, boolean withVacancy) {
        int structure = structure(analysis.sections());
        int quality = quality(analysis.quality());
        Integer match = withVacancy ? match(analysis) : null;
        return new ScoreBreakdown(structure, STRUCTURE_MAX, quality, QUALITY_MAX,
                match, withVacancy ? MATCH_MAX : null);
    }

    /** Puntaje final de 0 a 100. */
    public int total(ScoreBreakdown b) {
        if (b.match() == null) {
            return Math.round((b.structure() + b.quality()) * 100f / (STRUCTURE_MAX + QUALITY_MAX));
        }
        return b.structure() + b.quality() + b.match();
    }

    private int structure(ResumeAnalysis.Sections s) {
        if (s == null) return 0;
        int points = 0;
        if (s.experience()) points += 10;
        if (s.education()) points += 7;
        if (s.skills()) points += 7;
        if (s.contact()) points += 6;
        if (s.summary()) points += 5;
        if (s.projects()) points += 3;
        if (s.languages()) points += 2;
        return points; // suma máxima: 40
    }

    private int quality(ResumeAnalysis.Quality q) {
        if (q == null) return 0;
        double points = level(q.achievements()) * 12
                + level(q.clarity()) * 10
                + level(q.spelling()) * 8;
        return (int) Math.round(points);
    }

    /** Convierte un nivel 1-5 en una fracción 0-1 (valores fuera de rango se ajustan). */
    private double level(int value) {
        int clamped = Math.max(1, Math.min(5, value));
        return (clamped - 1) / 4.0;
    }

    private int match(ResumeAnalysis analysis) {
        var checks = analysis.requirements();
        if (checks == null || checks.isEmpty()) return 0;
        long met = checks.stream().filter(ResumeAnalysis.RequirementCheck::met).count();
        return (int) Math.round(met * (double) MATCH_MAX / checks.size());
    }
}

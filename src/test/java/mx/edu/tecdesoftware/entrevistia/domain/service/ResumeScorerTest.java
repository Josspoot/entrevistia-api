package mx.edu.tecdesoftware.entrevistia.domain.service;

import mx.edu.tecdesoftware.entrevistia.domain.ResumeAnalysis;
import mx.edu.tecdesoftware.entrevistia.domain.ResumeAnalysis.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** La fórmula del puntaje es fija: estas pruebas no usan IA ni base de datos. */
class ResumeScorerTest {

    private final ResumeScorer scorer = new ResumeScorer();

    private ResumeAnalysis analysis(Sections sections, Quality quality, List<RequirementCheck> reqs) {
        return new ResumeAnalysis(sections, quality, List.of(), List.of(), List.of(), reqs);
    }

    private static final Sections ALL = new Sections(true, true, true, true, true, true, true);
    private static final Sections NONE = new Sections(false, false, false, false, false, false, false);

    @Test
    void perfectCvWithoutVacancyScores100() {
        var b = scorer.breakdown(analysis(ALL, new Quality(5, 5, 5), List.of()), false);
        assertEquals(40, b.structure());
        assertEquals(30, b.quality());
        assertEquals(100, scorer.total(b));
    }

    @Test
    void emptyCvScoresZero() {
        var b = scorer.breakdown(analysis(NONE, new Quality(1, 1, 1), List.of()), false);
        assertEquals(0, scorer.total(b));
    }

    @Test
    void qualityLevelsAreLinear() {
        // nivel 3 = mitad del peso: 6 + 5 + 4 = 15
        var b = scorer.breakdown(analysis(NONE, new Quality(3, 3, 3), List.of()), false);
        assertEquals(15, b.quality());
    }

    @Test
    void outOfRangeLevelsAreClamped() {
        var b = scorer.breakdown(analysis(NONE, new Quality(9, 0, 5), List.of()), false);
        assertEquals(12 + 0 + 8, b.quality());
    }

    @Test
    void matchCountsRequirementsMet() {
        var reqs = List.of(
                new RequirementCheck("Dart", true, "2 años con Dart"),
                new RequirementCheck("Flutter", true, "apps en Flutter"),
                new RequirementCheck("Git", false, ""));
        var b = scorer.breakdown(analysis(ALL, new Quality(5, 5, 5), reqs), true);
        assertEquals(20, b.match()); // 2 de 3 × 30
        assertEquals(90, scorer.total(b));
    }

    @Test
    void splitsRequirementsText() {
        assertEquals(List.of("Dart", "Flutter", "consumo de APIs REST", "Git"),
                ResumeService.splitRequirements("Dart, Flutter, consumo de APIs REST, Git."));
    }
}

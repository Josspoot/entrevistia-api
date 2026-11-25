package mx.edu.tecdesoftware.entrevistia.domain.repository;

import mx.edu.tecdesoftware.entrevistia.domain.Resume;
import mx.edu.tecdesoftware.entrevistia.domain.ResumeReview;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del dominio para CVs. Todas las búsquedas reciben el uid del
 * dueño: un usuario nunca puede ver los CVs de otro.
 */
public interface ResumeRepository {

    List<Resume> getAll(String uid);

    Optional<Resume> get(String uid, int resumeId);

    Optional<String> getText(String uid, int resumeId);

    Resume save(String uid, String fileName, int pages, String text);

    void delete(int resumeId);

    ResumeReview saveReview(ResumeReview review);

    Optional<ResumeReview> getLatestReview(int resumeId);
}

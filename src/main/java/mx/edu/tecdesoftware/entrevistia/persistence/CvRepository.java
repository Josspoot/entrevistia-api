package mx.edu.tecdesoftware.entrevistia.persistence;

import mx.edu.tecdesoftware.entrevistia.domain.Resume;
import mx.edu.tecdesoftware.entrevistia.domain.ResumeReview;
import mx.edu.tecdesoftware.entrevistia.domain.repository.ResumeRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.crud.CvCrudRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.crud.RevisionCvCrudRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.Cv;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.RevisionCv;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Optional;

/** Implementación con PostgreSQL de ResumeRepository. */
@Repository
public class CvRepository implements ResumeRepository {

    private final CvCrudRepository cvCrud;
    private final RevisionCvCrudRepository revisionCrud;
    private final JsonMapper jsonMapper;

    public CvRepository(CvCrudRepository cvCrud, RevisionCvCrudRepository revisionCrud,
                        JsonMapper jsonMapper) {
        this.cvCrud = cvCrud;
        this.revisionCrud = revisionCrud;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public List<Resume> getAll(String uid) {
        return cvCrud.findByFirebaseUidOrderByCreadoEnDesc(uid).stream()
                .map(this::toResume).toList();
    }

    @Override
    public Optional<Resume> get(String uid, int resumeId) {
        return cvCrud.findByIdCvAndFirebaseUid(resumeId, uid).map(this::toResume);
    }

    @Override
    public Optional<String> getText(String uid, int resumeId) {
        return cvCrud.findByIdCvAndFirebaseUid(resumeId, uid).map(Cv::getTextoExtraido);
    }

    @Override
    public Resume save(String uid, String fileName, int pages, String text) {
        Cv cv = new Cv();
        cv.setFirebaseUid(uid);
        cv.setNombreArchivo(fileName);
        cv.setPaginas(pages);
        cv.setTextoExtraido(text);
        Cv saved = cvCrud.save(cv);
        // creado_en lo pone la base de datos: se vuelve a leer para tenerlo
        return toResume(cvCrud.findById(saved.getIdCv()).orElse(saved));
    }

    @Override
    public void delete(int resumeId) {
        cvCrud.deleteById(resumeId); // las revisiones se borran en cascada
    }

    @Override
    public ResumeReview saveReview(ResumeReview review) {
        RevisionCv revision = new RevisionCv();
        revision.setIdCv(review.resumeId());
        revision.setIdVacante(review.vacancyId());
        revision.setPuntaje(review.score());
        revision.setResultadoJson(jsonMapper.writeValueAsString(review));
        RevisionCv saved = revisionCrud.save(revision);
        RevisionCv reloaded = revisionCrud.findById(saved.getIdRevision()).orElse(saved);
        return review.withIdAndDate(reloaded.getIdRevision(), reloaded.getCreadoEn());
    }

    @Override
    public Optional<ResumeReview> getLatestReview(int resumeId) {
        return revisionCrud.findFirstByIdCvOrderByCreadoEnDescIdRevisionDesc(resumeId)
                .map(r -> jsonMapper.readValue(r.getResultadoJson(), ResumeReview.class)
                        .withIdAndDate(r.getIdRevision(), r.getCreadoEn()));
    }

    private Resume toResume(Cv cv) {
        return new Resume(cv.getIdCv(), cv.getNombreArchivo(), cv.getPaginas(), cv.getCreadoEn());
    }
}

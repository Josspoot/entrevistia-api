package mx.edu.tecdesoftware.entrevistia.persistence.crud;

import mx.edu.tecdesoftware.entrevistia.persistence.entity.RevisionCv;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RevisionCvCrudRepository extends CrudRepository<RevisionCv, Integer> {

    Optional<RevisionCv> findFirstByIdCvOrderByCreadoEnDescIdRevisionDesc(Integer idCv);
}

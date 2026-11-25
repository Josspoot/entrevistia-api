package mx.edu.tecdesoftware.entrevistia.persistence.crud;

import mx.edu.tecdesoftware.entrevistia.persistence.entity.Cv;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CvCrudRepository extends CrudRepository<Cv, Integer> {

    List<Cv> findByFirebaseUidOrderByCreadoEnDesc(String firebaseUid);

    // Busca por id Y dueño: así nadie puede leer el CV de otro usuario
    Optional<Cv> findByIdCvAndFirebaseUid(Integer idCv, String firebaseUid);
}

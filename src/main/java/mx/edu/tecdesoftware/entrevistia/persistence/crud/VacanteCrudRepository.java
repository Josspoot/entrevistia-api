package mx.edu.tecdesoftware.entrevistia.persistence.crud;

import mx.edu.tecdesoftware.entrevistia.persistence.entity.Vacante;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Spring Data arma el SQL a partir del nombre de cada método.
 */
public interface VacanteCrudRepository extends CrudRepository<Vacante, Integer> {

    // SELECT * FROM vacantes ORDER BY puesto
    List<Vacante> findAllByOrderByPuestoAsc();

    // SELECT * FROM vacantes WHERE id_categoria = ? ORDER BY puesto
    List<Vacante> findByIdCategoriaOrderByPuestoAsc(int idCategoria);

    // SELECT * FROM vacantes WHERE puesto ILIKE '%texto%' ORDER BY puesto
    List<Vacante> findByPuestoContainingIgnoreCaseOrderByPuestoAsc(String puesto);

    // Las dos condiciones juntas
    List<Vacante> findByIdCategoriaAndPuestoContainingIgnoreCaseOrderByPuestoAsc(
            int idCategoria, String puesto);
}

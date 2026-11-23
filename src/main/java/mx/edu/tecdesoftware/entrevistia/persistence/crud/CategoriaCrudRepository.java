package mx.edu.tecdesoftware.entrevistia.persistence.crud;

import mx.edu.tecdesoftware.entrevistia.persistence.entity.Categoria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoriaCrudRepository extends CrudRepository<Categoria, Integer> {

    // SELECT * FROM categorias ORDER BY descripcion
    List<Categoria> findAllByOrderByDescripcionAsc();
}

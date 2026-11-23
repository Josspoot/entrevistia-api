package mx.edu.tecdesoftware.entrevistia.persistence;

import mx.edu.tecdesoftware.entrevistia.domain.Category;
import mx.edu.tecdesoftware.entrevistia.domain.repository.CategoryRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.crud.CategoriaCrudRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepository implements CategoryRepository {

    @Autowired
    private CategoriaCrudRepository categoriaCrudRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        return categoryMapper.toCategories(categoriaCrudRepository.findAllByOrderByDescripcionAsc());
    }

    @Override
    public boolean exists(int categoryId) {
        return categoriaCrudRepository.existsById(categoryId);
    }
}

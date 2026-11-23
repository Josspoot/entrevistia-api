package mx.edu.tecdesoftware.entrevistia.domain.service;

import mx.edu.tecdesoftware.entrevistia.domain.Category;
import mx.edu.tecdesoftware.entrevistia.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }
}

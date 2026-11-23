package mx.edu.tecdesoftware.entrevistia.domain.repository;

import mx.edu.tecdesoftware.entrevistia.domain.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> getAll();

    boolean exists(int categoryId);
}

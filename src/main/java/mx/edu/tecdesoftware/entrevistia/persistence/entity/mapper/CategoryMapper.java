package mx.edu.tecdesoftware.entrevistia.persistence.entity.mapper;

import mx.edu.tecdesoftware.entrevistia.domain.Category;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active"),
    })
    Category toCategory(Categoria categoria);

    List<Category> toCategories(List<Categoria> categorias);

    @InheritInverseConfiguration
    @Mapping(target = "vacantes", ignore = true)
    Categoria toCategoria(Category category);
}

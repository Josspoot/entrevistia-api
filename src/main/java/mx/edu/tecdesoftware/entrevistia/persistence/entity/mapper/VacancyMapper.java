package mx.edu.tecdesoftware.entrevistia.persistence.entity.mapper;

import mx.edu.tecdesoftware.entrevistia.domain.Vacancy;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.Vacante;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface VacancyMapper {

    @Mappings({
            @Mapping(source = "idVacante", target = "vacancyId"),
            @Mapping(source = "puesto", target = "title"),
            @Mapping(source = "empresa", target = "company"),
            @Mapping(source = "descripcion", target = "description"),
            @Mapping(source = "requisitos", target = "requirements"),
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "salarioMensual", target = "salary"),
            @Mapping(source = "plazas", target = "openings"),
            @Mapping(source = "modalidad", target = "mode"),
            @Mapping(source = "ubicacion", target = "location"),
            @Mapping(source = "imageUrl", target = "imageUrl"),
            @Mapping(source = "estado", target = "active"),
            @Mapping(source = "categoria", target = "category")
    })
    Vacancy toVacancy(Vacante vacante);

    List<Vacancy> toVacancies(List<Vacante> vacantes);

    // A diferencia de market-backend, el ID SÍ se copia: el PUT lo necesita
    // para actualizar en lugar de insertar. La categoría se guarda con idCategoria.
    @InheritInverseConfiguration
    @Mapping(target = "categoria", ignore = true)
    Vacante toVacante(Vacancy vacancy);
}

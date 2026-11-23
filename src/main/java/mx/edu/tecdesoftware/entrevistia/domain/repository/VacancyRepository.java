package mx.edu.tecdesoftware.entrevistia.domain.repository;

import mx.edu.tecdesoftware.entrevistia.domain.Vacancy;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del dominio: el servicio no sabe si los datos vienen de
 * PostgreSQL o de otro lugar.
 */
public interface VacancyRepository {

    List<Vacancy> getAll();

    List<Vacancy> search(String text, Integer categoryId);

    Optional<Vacancy> getVacancy(int vacancyId);

    boolean exists(int vacancyId);

    Vacancy save(Vacancy vacancy);

    void delete(int vacancyId);
}

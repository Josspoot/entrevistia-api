package mx.edu.tecdesoftware.entrevistia.persistence;

import mx.edu.tecdesoftware.entrevistia.domain.Vacancy;
import mx.edu.tecdesoftware.entrevistia.domain.repository.VacancyRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.crud.VacanteCrudRepository;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.Vacante;
import mx.edu.tecdesoftware.entrevistia.persistence.entity.mapper.VacancyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class VacanteRepository implements VacancyRepository {

    @Autowired
    private VacanteCrudRepository vacanteCrudRepository;

    @Autowired
    private VacancyMapper vacancyMapper;

    // SELECT * FROM vacantes ORDER BY puesto
    @Override
    public List<Vacancy> getAll() {
        return vacancyMapper.toVacancies(vacanteCrudRepository.findAllByOrderByPuestoAsc());
    }

    // Busca por texto en el puesto y/o por categoría (ambos opcionales)
    @Override
    public List<Vacancy> search(String text, Integer categoryId) {
        boolean hasText = text != null && !text.isBlank();
        List<Vacante> vacantes;
        if (hasText && categoryId != null) {
            vacantes = vacanteCrudRepository
                    .findByIdCategoriaAndPuestoContainingIgnoreCaseOrderByPuestoAsc(categoryId, text.trim());
        } else if (hasText) {
            vacantes = vacanteCrudRepository.findByPuestoContainingIgnoreCaseOrderByPuestoAsc(text.trim());
        } else if (categoryId != null) {
            vacantes = vacanteCrudRepository.findByIdCategoriaOrderByPuestoAsc(categoryId);
        } else {
            vacantes = vacanteCrudRepository.findAllByOrderByPuestoAsc();
        }
        return vacancyMapper.toVacancies(vacantes);
    }

    // SELECT * FROM vacantes WHERE id_vacante = ?
    @Override
    public Optional<Vacancy> getVacancy(int vacancyId) {
        return vacanteCrudRepository.findById(vacancyId).map(vacancyMapper::toVacancy);
    }

    @Override
    public boolean exists(int vacancyId) {
        return vacanteCrudRepository.existsById(vacancyId);
    }

    // INSERT si no trae ID, UPDATE si lo trae.
    // Se vuelve a leer para que la respuesta incluya la categoría completa.
    @Override
    public Vacancy save(Vacancy vacancy) {
        Vacante guardada = vacanteCrudRepository.save(vacancyMapper.toVacante(vacancy));
        return getVacancy(guardada.getIdVacante()).orElseThrow();
    }

    // DELETE FROM vacantes WHERE id_vacante = ?
    @Override
    public void delete(int vacancyId) {
        vacanteCrudRepository.deleteById(vacancyId);
    }
}

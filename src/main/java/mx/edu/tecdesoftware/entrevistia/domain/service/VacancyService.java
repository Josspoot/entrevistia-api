package mx.edu.tecdesoftware.entrevistia.domain.service;

import mx.edu.tecdesoftware.entrevistia.domain.Vacancy;
import mx.edu.tecdesoftware.entrevistia.domain.repository.CategoryRepository;
import mx.edu.tecdesoftware.entrevistia.domain.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Vacancy> search(String text, Integer categoryId) {
        return vacancyRepository.search(text, categoryId);
    }

    public Optional<Vacancy> getVacancy(int vacancyId) {
        return vacancyRepository.getVacancy(vacancyId);
    }

    /** Crea una vacante nueva. Se ignora cualquier ID que venga en el JSON. */
    public Vacancy create(Vacancy vacancy) {
        vacancy.setVacancyId(null);
        checkCategory(vacancy);
        return vacancyRepository.save(vacancy);
    }

    /** Actualiza la vacante con el ID de la ruta. Vacío si no existe. */
    public Optional<Vacancy> update(int vacancyId, Vacancy vacancy) {
        if (!vacancyRepository.exists(vacancyId)) {
            return Optional.empty();
        }
        // El ID de la ruta manda sobre el del JSON
        vacancy.setVacancyId(vacancyId);
        checkCategory(vacancy);
        return Optional.of(vacancyRepository.save(vacancy));
    }

    public boolean delete(int vacancyId) {
        if (vacancyRepository.exists(vacancyId)) {
            vacancyRepository.delete(vacancyId);
            return true;
        }
        return false;
    }

    /** Una categoría inexistente es un error del cliente: 400. */
    private void checkCategory(Vacancy vacancy) {
        if (!categoryRepository.exists(vacancy.getCategoryId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "categoryId " + vacancy.getCategoryId() + " does not exist");
        }
    }
}

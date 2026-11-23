package mx.edu.tecdesoftware.entrevistia.domain;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * Vacante del catálogo (el "producto" de la rúbrica).
 * Las anotaciones de validación se revisan con @Valid en el controlador:
 * si algo no cumple, Spring responde 400 Bad Request.
 */
public class Vacancy {

    private Integer vacancyId;

    @NotBlank(message = "title is required")
    @Size(max = 120)
    private String title;

    @NotBlank(message = "company is required")
    @Size(max = 120)
    private String company;

    private String description;

    private String requirements;

    @NotNull(message = "categoryId is required")
    private Integer categoryId;

    @NotNull(message = "salary is required")
    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    private BigDecimal salary;

    @NotNull(message = "openings is required")
    @PositiveOrZero
    private Integer openings;

    @NotBlank(message = "mode is required")
    @Pattern(regexp = "Presencial|Híbrido|Remoto",
            message = "mode must be Presencial, Híbrido or Remoto")
    private String mode;

    @NotBlank(message = "location is required")
    @Size(max = 80)
    private String location;

    private String imageUrl;

    @NotNull(message = "active is required")
    private Boolean active;

    // Solo lectura en las respuestas (se llena desde la tabla categorias)
    private Category category;

    public Integer getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Integer vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getOpenings() {
        return openings;
    }

    public void setOpenings(Integer openings) {
        this.openings = openings;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

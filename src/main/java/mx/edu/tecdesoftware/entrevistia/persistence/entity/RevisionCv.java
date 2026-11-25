package mx.edu.tecdesoftware.entrevistia.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

/** Tabla revisiones_cv: resultado de analizar un CV. */
@Entity
@Table(name = "revisiones_cv")
public class RevisionCv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_revision")
    private Integer idRevision;

    @Column(name = "id_cv")
    private Integer idCv;

    @Column(name = "id_vacante")
    private Integer idVacante;

    private Integer puntaje;

    // El análisis completo se guarda como JSON en texto
    @Column(name = "resultado_json")
    private String resultadoJson;

    @Column(name = "creado_en", insertable = false, updatable = false)
    private OffsetDateTime creadoEn;

    public Integer getIdRevision() { return idRevision; }
    public Integer getIdCv() { return idCv; }
    public void setIdCv(Integer idCv) { this.idCv = idCv; }
    public Integer getIdVacante() { return idVacante; }
    public void setIdVacante(Integer idVacante) { this.idVacante = idVacante; }
    public Integer getPuntaje() { return puntaje; }
    public void setPuntaje(Integer puntaje) { this.puntaje = puntaje; }
    public String getResultadoJson() { return resultadoJson; }
    public void setResultadoJson(String resultadoJson) { this.resultadoJson = resultadoJson; }
    public OffsetDateTime getCreadoEn() { return creadoEn; }
}

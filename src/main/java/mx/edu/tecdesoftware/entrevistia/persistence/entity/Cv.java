package mx.edu.tecdesoftware.entrevistia.persistence.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

/** Tabla cvs: CV de un usuario (solo se guarda el texto extraído del PDF). */
@Entity
@Table(name = "cvs")
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cv")
    private Integer idCv;

    @Column(name = "firebase_uid")
    private String firebaseUid;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

    private Integer paginas;

    @Column(name = "texto_extraido")
    private String textoExtraido;

    @Column(name = "creado_en", insertable = false, updatable = false)
    private OffsetDateTime creadoEn;

    public Integer getIdCv() { return idCv; }
    public void setIdCv(Integer idCv) { this.idCv = idCv; }
    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }
    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public Integer getPaginas() { return paginas; }
    public void setPaginas(Integer paginas) { this.paginas = paginas; }
    public String getTextoExtraido() { return textoExtraido; }
    public void setTextoExtraido(String textoExtraido) { this.textoExtraido = textoExtraido; }
    public OffsetDateTime getCreadoEn() { return creadoEn; }
}

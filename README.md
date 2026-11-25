# entrevistia-api

Backend REST de **EntrevistIA**, una app de preparación para entrevistas laborales.
Java + Spring Boot + PostgreSQL, con la arquitectura por capas vista en clase.

## Stack

- Spring Boot 4.0.6, Java 26 (toolchain de Gradle), Gradle 9.4.1
- Spring Data JPA + PostgreSQL 18
- MapStruct 1.6.3 (entidad ↔ dominio)
- Bean Validation (respuestas `400` ante datos inválidos)
- springdoc / Swagger UI

## Estructura

```text
src/main/java/mx/edu/tecdesoftware/entrevistia/
├── web/controller/            # VacancyController, CategoryController
├── domain/                    # Vacancy, Category (modelo en inglés)
├── domain/repository/         # Contratos del dominio
├── domain/service/            # Reglas de negocio
└── persistence/
    ├── entity/                # Vacante, Categoria (tablas en español)
    ├── entity/mapper/         # MapStruct
    ├── crud/                  # CrudRepository de Spring Data
    └── *Repository.java       # Implementaciones de los contratos
```

## Configuración

1. Crear la base de datos y cargar los datos de ejemplo:

   ```bash
   createdb -U postgres entrevistia
   psql -U postgres -d entrevistia -f db/schema.sql
   psql -U postgres -d entrevistia -f db/data.sql
   psql -U postgres -d entrevistia -f db/images.sql
   ```

   `images.sql` guarda en `image_url` los logos que están en el bucket público
   `productos` de Supabase Storage. Los PNG originales están en `db/logos/`.

2. Copiar `src/main/resources/application-dev.properties.example` como
   `application-dev.properties` y poner usuario y contraseña de PostgreSQL,
   y la llave de Gemini (`gemini.api-key`) para el asistente.
   Ese archivo está en `.gitignore` y **nunca** se sube.

3. Ejecutar:

   ```bash
   ./gradlew bootRun
   ```

   La API queda en `http://localhost:8090` y Swagger en
   `http://localhost:8090/swagger-ui/index.html`.

## Endpoints

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| `GET` | `/vacancies` | Lista; filtros opcionales `search` y `categoryId` | `200` |
| `GET` | `/vacancies/{id}` | Detalle | `200` / `404` |
| `POST` | `/vacancies` | Crear | `201` / `400` |
| `PUT` | `/vacancies/{id}` | Actualizar | `200` / `400` / `404` |
| `DELETE` | `/vacancies/{id}` | Eliminar | `204` / `404` |
| `GET` | `/categories` | Áreas de trabajo | `200` |
| `POST` | `/assistant/messages` | Pregunta al asistente con IA 🔒 | `200` / `400` / `401` / `429` / `503` |
| `GET` | `/resumes` | Mis CVs 🔒 | `200` / `401` |
| `POST` | `/resumes` | Subir CV en PDF (máx. 5 MB) 🔒 | `201` / `400` / `413` |
| `DELETE` | `/resumes/{id}` | Borrar CV y sus revisiones 🔒 | `204` / `404` |
| `POST` | `/resumes/{id}/reviews` | Revisar CV, opcional `vacancyId` 🔒 | `201` / `404` / `503` |
| `GET` | `/resumes/{id}/reviews/latest` | Última revisión 🔒 | `200` / `404` |

🔒 Requiere `Authorization: Bearer <token de Firebase>`. También `POST`, `PUT` y
`DELETE` de `/vacancies`: leer el catálogo es público, modificarlo no.

Ejemplo de vacante:

```json
{
  "vacancyId": 1,
  "title": "Desarrollador Flutter Jr",
  "company": "Nova Labs",
  "description": "Desarrollo de apps móviles multiplataforma para clientes de retail.",
  "requirements": "Dart, Flutter, consumo de APIs REST, Git.",
  "categoryId": 1,
  "salary": 18000.00,
  "openings": 3,
  "mode": "Híbrido",
  "location": "Mérida, Yuc.",
  "imageUrl": null,
  "active": true,
  "category": { "categoryId": 1, "category": "Tecnología", "active": true }
}
```

## Asistente de vacantes (Gemini)

`POST /assistant/messages` recibe la conversación y en cada pregunta le inyecta
a Gemini el catálogo activo. Gemini responde JSON con el texto y los IDs de las
vacantes que menciona; el backend descarta cualquier ID que no exista, así que
nunca se muestran vacantes inventadas. La llave vive solo en el servidor.

```json
{ "messages": [ { "role": "user", "text": "¿Qué vacante de tecnología paga más?" } ] }
```

## Revisor de CV

1. Spring valida que el archivo sea un PDF real (empieza con `%PDF`), de máximo
   5 MB y 10 páginas, y extrae el texto con PDFBox.
2. Gemini **detecta**: secciones presentes, calidad de 1 a 5 (logros, claridad,
   ortografía), fortalezas, mejoras con ejemplo y qué requisitos de la vacante
   demuestra el CV, con la cita como evidencia.
3. `ResumeScorer` **calcula** el puntaje con una fórmula fija (probada en
   `ResumeScorerTest`): estructura 40 + calidad 30 + ajuste con la vacante 30.
   Sin vacante, el total se escala sobre 70.

Cada CV está ligado al `uid` del token de Firebase: nadie puede ver ni borrar
el CV de otra persona. Para pruebas hay un CV ficticio en `db/sample/`.

## Equivalencias con la rúbrica

| Rúbrica | EntrevistIA |
|---|---|
| Producto | Vacante |
| Categoría | Área (Tecnología, Diseño, Ventas…) |
| Precio | Salario mensual (`BigDecimal`) |
| Stock | Plazas disponibles |
| `imageUrl` | Logo de la empresa (Supabase, Hito 2) |

Las empresas del catálogo son ficticias.

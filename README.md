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
   ```

2. Copiar `src/main/resources/application-dev.properties.example` como
   `application-dev.properties` y poner usuario y contraseña de PostgreSQL.
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

## Equivalencias con la rúbrica

| Rúbrica | EntrevistIA |
|---|---|
| Producto | Vacante |
| Categoría | Área (Tecnología, Diseño, Ventas…) |
| Precio | Salario mensual (`BigDecimal`) |
| Stock | Plazas disponibles |
| `imageUrl` | Logo de la empresa (Supabase, Hito 2) |

Las empresas del catálogo son ficticias.

-- EntrevistIA: estructura de la base de datos.
-- Ejecutar sobre la base "entrevistia":
--   psql -U postgres -d entrevistia -f db/schema.sql

DROP TABLE IF EXISTS vacantes;
DROP TABLE IF EXISTS categorias;

-- Áreas de trabajo (equivale a la "categoría" de la rúbrica)
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    descripcion  VARCHAR(60) NOT NULL UNIQUE,
    estado       BOOLEAN     NOT NULL DEFAULT TRUE
);

-- Vacantes (equivale al "producto" de la rúbrica)
--   salario_mensual → precio   (NUMERIC: no pierde decimales)
--   plazas          → stock
CREATE TABLE vacantes (
    id_vacante      SERIAL PRIMARY KEY,
    puesto          VARCHAR(120)  NOT NULL,
    empresa         VARCHAR(120)  NOT NULL,
    descripcion     TEXT,
    requisitos      TEXT,
    id_categoria    INTEGER       NOT NULL REFERENCES categorias (id_categoria),
    salario_mensual NUMERIC(12,2) NOT NULL CHECK (salario_mensual >= 0),
    plazas          INTEGER       NOT NULL CHECK (plazas >= 0),
    modalidad       VARCHAR(20)   NOT NULL,
    ubicacion       VARCHAR(80)   NOT NULL,
    image_url       TEXT,
    estado          BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE INDEX idx_vacantes_categoria ON vacantes (id_categoria);

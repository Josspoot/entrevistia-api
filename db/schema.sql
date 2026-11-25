-- EntrevistIA: estructura de la base de datos.
-- Ejecutar sobre la base "entrevistia":
--   psql -U postgres -d entrevistia -f db/schema.sql

DROP TABLE IF EXISTS revisiones_cv;
DROP TABLE IF EXISTS cvs;
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

-- Un CV en PDF del que solo se guarda el texto extraído.
-- firebase_uid: dueño del CV (el "sub" del token de Firebase).
CREATE TABLE IF NOT EXISTS cvs (
    id_cv          SERIAL PRIMARY KEY,
    firebase_uid   VARCHAR(128) NOT NULL,
    nombre_archivo VARCHAR(255) NOT NULL,
    paginas        INTEGER      NOT NULL,
    texto_extraido TEXT         NOT NULL,
    creado_en      TIMESTAMPTZ  NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_cvs_usuario ON cvs (firebase_uid);

-- Resultado de analizar un CV, opcionalmente contra una vacante.
-- Al borrar el CV se borran sus revisiones (ON DELETE CASCADE).
CREATE TABLE IF NOT EXISTS revisiones_cv (
    id_revision    SERIAL PRIMARY KEY,
    id_cv          INTEGER     NOT NULL REFERENCES cvs (id_cv) ON DELETE CASCADE,
    id_vacante     INTEGER     REFERENCES vacantes (id_vacante) ON DELETE SET NULL,
    puntaje        INTEGER     NOT NULL CHECK (puntaje BETWEEN 0 AND 100),
    resultado_json TEXT        NOT NULL,
    creado_en      TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_revisiones_cv ON revisiones_cv (id_cv);

-- Revisor de CV: CVs subidos por cada usuario y sus revisiones.
-- Se incluye también en schema.sql; este archivo sirve para agregarlas a una
-- base que ya tiene vacantes, sin borrarlas:
--   psql -U postgres -d entrevistia -f db/resumes.sql

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

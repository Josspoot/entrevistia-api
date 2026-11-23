-- EntrevistIA: datos de ejemplo (empresas ficticias).
--   psql -U postgres -d entrevistia -f db/data.sql
-- image_url queda vacío hasta subir los logos a Supabase (Hito 2).

INSERT INTO categorias (descripcion, estado) VALUES
    ('Tecnología', TRUE),
    ('Diseño', TRUE),
    ('Ventas', TRUE),
    ('Recursos Humanos', TRUE),
    ('Finanzas', TRUE),
    ('Marketing', TRUE);

INSERT INTO vacantes
    (puesto, empresa, descripcion, requisitos, id_categoria, salario_mensual, plazas, modalidad, ubicacion, estado)
VALUES
    ('Desarrollador Flutter Jr', 'Nova Labs',
     'Desarrollo de apps móviles multiplataforma para clientes de retail.',
     'Dart, Flutter, consumo de APIs REST, Git.', 1, 18000.00, 3, 'Híbrido', 'Mérida, Yuc.', TRUE),
    ('Backend Developer Java', 'Cobalto Systems',
     'Construcción de microservicios con Spring Boot y PostgreSQL.',
     'Java 17+, Spring Boot, SQL, pruebas unitarias.', 1, 32000.00, 2, 'Remoto', 'CDMX', TRUE),
    ('QA Tester Manual', 'Pixel Forge',
     'Diseño y ejecución de casos de prueba para apps web y móviles.',
     'Casos de prueba, reporte de bugs, atención al detalle.', 1, 14500.50, 1, 'Presencial', 'Mérida, Yuc.', TRUE),
    ('Ingeniero DevOps', 'Nube Maya Cloud',
     'Automatización de despliegues y monitoreo de infraestructura.',
     'Docker, CI/CD, Linux, nociones de AWS.', 1, 45000.00, 1, 'Remoto', 'Guadalajara, Jal.', TRUE),
    ('Desarrollador Web Frontend', 'Ceiba Digital',
     'Maquetación e interacción de sitios corporativos.',
     'HTML, CSS, JavaScript, React básico.', 1, 21000.00, 4, 'Híbrido', 'Monterrey, N.L.', TRUE),

    ('Diseñador UX/UI', 'Lumen Studio',
     'Investigación con usuarios y prototipos de alta fidelidad.',
     'Figma, pruebas de usabilidad, portafolio.', 2, 26000.00, 2, 'Remoto', 'CDMX', TRUE),
    ('Diseñador Gráfico Jr', 'Coral Creativo',
     'Piezas para redes sociales y material impreso.',
     'Illustrator, Photoshop, creatividad.', 2, 12000.00, 1, 'Presencial', 'Mérida, Yuc.', TRUE),
    ('Motion Designer', 'Órbita Media',
     'Animaciones para campañas digitales y videos cortos.',
     'After Effects, nociones de Lottie y Rive.', 2, 24500.00, 1, 'Híbrido', 'Puebla, Pue.', TRUE),

    ('Ejecutivo de Ventas B2B', 'Grupo Henequén',
     'Prospección y cierre de cuentas empresariales.',
     'Negociación, CRM, licencia de manejo.', 3, 16000.00, 5, 'Presencial', 'Mérida, Yuc.', TRUE),
    ('Asesor Comercial Telefónico', 'Conecta Plus',
     'Atención y venta de planes por teléfono.',
     'Comunicación efectiva, tolerancia a la presión.', 3, 9500.00, 8, 'Presencial', 'Cancún, Q.R.', TRUE),
    ('Key Account Manager', 'Andes Distribuciones',
     'Gestión de cuentas clave y metas trimestrales.',
     '3 años en ventas, Excel avanzado, inglés intermedio.', 3, 38000.00, 1, 'Híbrido', 'Monterrey, N.L.', TRUE),

    ('Reclutador TI', 'Talento Sur',
     'Búsqueda y entrevistas de perfiles tecnológicos.',
     'Entrevista por competencias, LinkedIn Recruiter.', 4, 17500.00, 2, 'Remoto', 'Mérida, Yuc.', TRUE),
    ('Analista de Nómina', 'Grupo Henequén',
     'Cálculo de nómina, IMSS e incidencias.',
     'Nómina, IMSS, Excel.', 4, 15000.00, 1, 'Presencial', 'Mérida, Yuc.', TRUE),
    ('Generalista de RH', 'Cobalto Systems',
     'Clima laboral, capacitación y procesos de ingreso.',
     'Psicología o afín, 2 años de experiencia.', 4, 20000.00, 1, 'Híbrido', 'CDMX', FALSE),

    ('Auxiliar Contable', 'Ámbar Consultores',
     'Registro contable y conciliaciones bancarias.',
     'Contabilidad, CONTPAQi, Excel.', 5, 13000.00, 2, 'Presencial', 'Mérida, Yuc.', TRUE),
    ('Analista Financiero', 'Andes Distribuciones',
     'Modelos financieros y reportes para dirección.',
     'Finanzas, Excel avanzado, Power BI.', 5, 29000.00, 1, 'Híbrido', 'Guadalajara, Jal.', TRUE),
    ('Tesorero Jr', 'Nova Labs',
     'Control de flujo de efectivo y pagos a proveedores.',
     'Finanzas o contaduría, banca electrónica.', 5, 19500.75, 1, 'Presencial', 'Mérida, Yuc.', TRUE),

    ('Community Manager', 'Coral Creativo',
     'Gestión de redes sociales y comunidad de marca.',
     'Redacción, Meta Business Suite, métricas.', 6, 13500.00, 2, 'Remoto', 'Mérida, Yuc.', TRUE),
    ('Especialista SEO', 'Ceiba Digital',
     'Posicionamiento orgánico de sitios de clientes.',
     'SEO técnico, Google Search Console, Analytics.', 6, 22000.00, 1, 'Remoto', 'CDMX', TRUE),
    ('Coordinador de Marketing', 'Lumen Studio',
     'Planeación de campañas y coordinación de proveedores.',
     'Marketing digital, gestión de presupuestos.', 6, 27500.00, 1, 'Híbrido', 'Querétaro, Qro.', TRUE);

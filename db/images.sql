-- Logos de las empresas (ficticias) guardados en Supabase Storage,
-- bucket público "productos". Los PNG originales están en db/logos/.
-- Ejecutar después de data.sql:
--   psql -U postgres -d entrevistia -f db/images.sql

UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/nova-labs.png' WHERE empresa = 'Nova Labs';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/cobalto-systems.png' WHERE empresa = 'Cobalto Systems';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/pixel-forge.png' WHERE empresa = 'Pixel Forge';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/nube-maya-cloud.png' WHERE empresa = 'Nube Maya Cloud';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/ceiba-digital.png' WHERE empresa = 'Ceiba Digital';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/lumen-studio.png' WHERE empresa = 'Lumen Studio';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/coral-creativo.png' WHERE empresa = 'Coral Creativo';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/orbita-media.png' WHERE empresa = 'Órbita Media';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/grupo-henequen.png' WHERE empresa = 'Grupo Henequén';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/conecta-plus.png' WHERE empresa = 'Conecta Plus';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/andes-distribuciones.png' WHERE empresa = 'Andes Distribuciones';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/talento-sur.png' WHERE empresa = 'Talento Sur';
UPDATE vacantes SET image_url = 'https://ehdttqbwvykegfvaxwdq.supabase.co/storage/v1/object/public/productos/ambar-consultores.png' WHERE empresa = 'Ámbar Consultores';

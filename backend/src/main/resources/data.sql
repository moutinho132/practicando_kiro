-- Datos iniciales para el sistema de registro de personas en catástrofes
-- Usuario administrador inicial

INSERT INTO usuario (id, username, password, email, rol, activo, fecha_creacion, fecha_actualizacion) 
VALUES (
    1, 
    'admin', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMy.MrqjHjID8Gv6nWlVmOvVF.pXP7KU6ey', -- password: 'admin123'
    'admin@catastrofes.org', 
    'ADMIN', 
    true, 
    CURRENT_TIMESTAMP, 
    CURRENT_TIMESTAMP
);

-- Ubicaciones de ejemplo
INSERT INTO ubicacion (id, pais, provincia, ciudad, parroquia, direccion_exacta, latitud, longitud, fecha_creacion)
VALUES 
    (1, 'Ecuador', 'Pichincha', 'Quito', 'Centro Histórico', 'Calle García Moreno 123', -0.2200, -78.5125, CURRENT_TIMESTAMP),
    (2, 'Ecuador', 'Guayas', 'Guayaquil', 'Centro', 'Avenida 9 de Octubre 456', -2.1700, -79.9200, CURRENT_TIMESTAMP);

-- Rasgos físicos de ejemplo
INSERT INTO rasgos_fisicos (id, color_piel, color_ojos, color_cabello, estatura_cm, complexion, rasgos_distintivos, fecha_creacion)
VALUES 
    (1, 'Mestizo', 'Negros', 'Negro', 170, 'Normal', 'Cicatriz en ceja izquierda', CURRENT_TIMESTAMP),
    (2, 'Blanco', 'Cafés', 'Castaño', 165, 'Delgada', 'Ninguno', CURRENT_TIMESTAMP);

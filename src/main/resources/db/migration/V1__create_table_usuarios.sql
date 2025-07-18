CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
);

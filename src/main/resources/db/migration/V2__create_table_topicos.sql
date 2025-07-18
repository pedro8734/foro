CREATE TABLE topicos (
    id BIGINT AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    usuario_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
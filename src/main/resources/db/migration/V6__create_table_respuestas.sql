CREATE TABLE respuestas (
    id BIGINT  AUTO_INCREMENT,
    mensaje TEXT NOT NULL,
     fecha_creacion DATETIME NOT NULL,
    solucion BOOLEAN DEFAULT FALSE,
    topico_id BIGINT NOT NULL,
 autor_id BIGINT NOT NULL,

PRIMARY KEY (id),

    CONSTRAINT fk_topico FOREIGN KEY (topico_id) REFERENCES topicos(id),
    CONSTRAINT fk_respuesta_autor FOREIGN KEY (autor_id) REFERENCES usuarios(id)
);

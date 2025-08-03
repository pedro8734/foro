package aluracursos.foro.respuesta.dto;

import aluracursos.foro.respuesta.Respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuestas(
        Long id,
        String mensaje,
        String autor,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        String tituloTopico
) {
    public DatosListaRespuestas(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getAutor().getCorreoElectronico(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                respuesta.getTopico().getTitulo()
        );
}
}

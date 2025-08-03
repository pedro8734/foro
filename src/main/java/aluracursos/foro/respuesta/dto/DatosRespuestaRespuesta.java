package aluracursos.foro.respuesta.dto;

import aluracursos.foro.respuesta.Respuesta;

import java.time.LocalDateTime;
public record DatosRespuestaRespuesta(

        Long id,
        String autor,
        String mensaje,
        LocalDateTime fechaCreacion) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getAutor().getCorreoElectronico(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion()
        );
    }
}

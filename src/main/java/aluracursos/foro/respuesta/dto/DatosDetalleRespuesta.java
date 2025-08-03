package aluracursos.foro.respuesta.dto;

import aluracursos.foro.respuesta.Respuesta;
import aluracursos.foro.topico.StatusTopico;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        String autorRespuesta,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        String titulo,
        String mensajeTopico,
        String autorTopico,
        StatusTopico estadoTopico) {
    public DatosDetalleRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getAutor().getCorreoElectronico(), // autor de la respuesta
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                respuesta.getTopico().getTitulo(),
                respuesta.getTopico().getMensaje(),
                respuesta.getTopico().getAutor().getCorreoElectronico(), // autor del topico
                respuesta.getTopico().getEstado()
                );

    }
}

package aluracursos.foro.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long idTopico,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Long idUsuario,
        StatusTopico estado

) {
    public DatosListadoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getAutor().getId(),
                topico.getEstado()
        );
    }
}

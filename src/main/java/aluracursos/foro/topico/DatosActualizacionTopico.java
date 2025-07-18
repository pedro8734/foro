package aluracursos.foro.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
         Long id,
        String titulo,
        String mensaje
) {
}

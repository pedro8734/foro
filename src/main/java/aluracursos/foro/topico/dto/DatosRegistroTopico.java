package aluracursos.foro.topico.dto;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {
}

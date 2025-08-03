package aluracursos.foro.usuario.dto;

import aluracursos.foro.usuario.Usuario;

public record DatosRespuestaUsuario(
        Long id,
        String correoElectronico
) {

    public  DatosRespuestaUsuario(Usuario usuario){
        this(usuario.getId(),
                usuario.getCorreoElectronico());
    }
}

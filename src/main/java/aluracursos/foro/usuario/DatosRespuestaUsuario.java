package aluracursos.foro.usuario;

public record DatosRespuestaUsuario(
        Long id,
        String correoElectronico
) {

    public  DatosRespuestaUsuario(Usuario usuario){
        this(usuario.getId(),
                usuario.getCorreoElectronico());
    }
}

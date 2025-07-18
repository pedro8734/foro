package aluracursos.foro.controller;


import aluracursos.foro.usuario.DatosRegistroUsuario;
import aluracursos.foro.usuario.DatosRespuestaUsuario;
import aluracursos.foro.usuario.Usuario;
import aluracursos.foro.usuario.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final  PasswordEncoder passwordEncoder;

    @Transactional
    @PostMapping
    public ResponseEntity<DatosRespuestaUsuario> registrarUsuario(@RequestBody DatosRegistroUsuario datos) {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico(datos.correoElectronico());
        usuario.setContrasena(passwordEncoder.encode(datos.contrasena()));

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new DatosRespuestaUsuario(usuario.getId(), usuario.getCorreoElectronico()));
    }

    @GetMapping
    public ResponseEntity<Page<DatosRespuestaUsuario>> ListarUsuario(@PageableDefault(size = 10, sort={"correoElectronico"}) Pageable paginacion) {
        var page = usuarioRepository.findAll(paginacion)
                .map(DatosRespuestaUsuario::new);
        return ResponseEntity.ok(page);

    }

}

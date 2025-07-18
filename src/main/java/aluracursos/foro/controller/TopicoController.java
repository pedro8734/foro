package aluracursos.foro.controller;


import aluracursos.foro.topico.*;
import aluracursos.foro.usuario.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
     @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> registrarTopico(
            @RequestBody @Valid DatosRegistroTopico datos,
            UriComponentsBuilder uriComponentsBuilder) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe un tópico con ese título y mensaje.");
        }

        // Obtener usuario autenticado
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Crear y guardar el tópico
        Topico topico = new Topico(datos, usuario);
        topicoRepository.save(topico);

        // Crear URI para el recurso creado
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        // Devolver respuesta
        return ResponseEntity.created(uri).body(new DatosRespuestaTopico(topico));
    }
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        Page<Topico> pagina = topicoRepository.findByEstadoNot(StatusTopico.ELIMINADO, paginacion);
        Page<DatosListadoTopico> dto = pagina.map(DatosListadoTopico::new);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> obtenerDetalleTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty() || optionalTopico.get().getEstado().equals(StatusTopico.ELIMINADO)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosDetalleTopico(optionalTopico.get()));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioAutenticado) {

        Optional<Topico> topicoOptional = topicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = topicoOptional.get();

        if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este tópico.");
        }

        topico.eliminar();
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id,
                                              @RequestBody @Valid DatosActualizacionTopico datos) {

        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optionalTopico.get();

        // Verificar si el usuario autenticado es el creador
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!topico.getAutor().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes modificar este tópico.");
        }

        topico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

}

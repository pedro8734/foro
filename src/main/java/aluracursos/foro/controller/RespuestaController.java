package aluracursos.foro.controller;


import aluracursos.foro.respuesta.Respuesta;
import aluracursos.foro.respuesta.RespuestaRepository;
import aluracursos.foro.respuesta.StatusRespuesta;
import aluracursos.foro.respuesta.dto.*;
import aluracursos.foro.topico.Topico;
import aluracursos.foro.topico.TopicoRepository;
import aluracursos.foro.usuario.Usuario;
import aluracursos.foro.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping
    @Transactional
    public ResponseEntity<?> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos, UriComponentsBuilder uriBuilder) {

        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topico topico = topicoRepository.getReferenceById(datos.topicoId());

        Respuesta respuesta = new Respuesta(datos, usuario, topico);

        topico.marcarComoRespondido();
        respuestaRepository.save(respuesta);

        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaRespuesta (respuesta));
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid DatosActualizarRespuesta datos) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        if (!respuesta.getAutor().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes modificar esta respuesta.");
        }

        respuesta.actualizar(datos.mensaje());
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }
    @PutMapping("/{id}/marcar-solucion")
    @Transactional
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<?> marcarComoSolucion(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.marcarComoSolucion();
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));


    }

    @GetMapping
    public ResponseEntity<Page<DatosListaRespuestas>> listarDeRespuestas(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        Page<Respuesta> pagina = respuestaRepository.findByStatusNot(StatusRespuesta.ELIMINADO, paginacion);
        Page<DatosListaRespuestas> dto = pagina.map(DatosListaRespuestas::new);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleRespuesta> obtenerDetalleRespuesta(@PathVariable Long id) {
        Optional<Respuesta> optionalRespuesta = respuestaRepository.findById(id);

        if (optionalRespuesta.isEmpty() || optionalRespuesta.get().getStatus().equals(StatusRespuesta.ELIMINADO)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosDetalleRespuesta(optionalRespuesta.get()));


    }




    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        if (!respuesta.getAutor().getId().equals(usuario.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar esta respuesta.");
        }

        respuesta.eliminar();
        return ResponseEntity.noContent().build();
    }

}

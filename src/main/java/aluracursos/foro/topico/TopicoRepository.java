package aluracursos.foro.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TopicoRepository extends JpaRepository <Topico, Long >{
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findByEstadoNot(StatusTopico estado, Pageable paginacion);


}

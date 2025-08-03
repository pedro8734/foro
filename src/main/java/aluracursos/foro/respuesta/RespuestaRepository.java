package aluracursos.foro.respuesta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long >{
    Page<Respuesta> findByStatusNot(StatusRespuesta estado, Pageable paginacion);
}

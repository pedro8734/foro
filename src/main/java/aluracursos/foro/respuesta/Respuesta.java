package aluracursos.foro.respuesta;

import aluracursos.foro.respuesta.dto.DatosRegistroRespuesta;
import aluracursos.foro.topico.Topico;
import aluracursos.foro.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    private Boolean solucion = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRespuesta status = StatusRespuesta.ACTIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    public Respuesta(DatosRegistroRespuesta datos, Usuario usuario, Topico topico){
      this.id = datos.topicoId();
      this.mensaje = datos.mensaje();
      this.fechaCreacion = LocalDateTime.now();
      this.solucion = false;
      this.status=StatusRespuesta.ACTIVO;
      this.autor = usuario;
      this.topico= topico;

    }


    public void eliminar() {
        this.status = StatusRespuesta.ELIMINADO;
    }
    public void marcarComoSolucion() {
        this.solucion = true;
        this.topico.marcarComoSolucionado();
    }
    public void actualizar(String nuevoMensaje) {
        this.mensaje = nuevoMensaje;
    }
}

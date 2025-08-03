package aluracursos.foro.topico;


import aluracursos.foro.respuesta.Respuesta;
import aluracursos.foro.topico.dto.DatosActualizacionTopico;
import aluracursos.foro.topico.dto.DatosRegistroTopico;
import aluracursos.foro.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "Topico")
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario autor;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private StatusTopico estado = StatusTopico.NO_RESPONDIDO;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(DatosRegistroTopico datos, Usuario autor) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.autor = autor;
        this.estado = StatusTopico.NO_RESPONDIDO;
    }


    public void eliminar() {
        this.estado= StatusTopico.ELIMINADO;
    }
    public void actualizarInformacion(DatosActualizacionTopico datos) {
        if (datos.titulo() != null && !datos.titulo().isBlank()) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null && !datos.mensaje().isBlank()) {
            this.mensaje = datos.mensaje();
        }

        // Actualiza la fecha de creación como "fecha de última actualización"
        this.fechaCreacion = LocalDateTime.now();
    }
    public void marcarComoRespondido() {
        this.estado = StatusTopico.RESPONDIDO;
    }
    public void marcarComoSolucionado() {
        this.estado = StatusTopico.SOLUCIONADO;
    }
}


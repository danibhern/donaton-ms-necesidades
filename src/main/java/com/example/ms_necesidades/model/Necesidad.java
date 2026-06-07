package com.example.ms_necesidades.model;

import com.example.ms_necesidades.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "necesidad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Necesidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRecurso tipoRecurso;

    @Column(nullable = false)
    private Integer cantidadSolicitada;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    private EstadoNecesidad estado;

    @Enumerated(EnumType.STRING)
    private NivelUrgencia urgencia;

    private LocalDateTime fechaReporte;
    private Long reportadoPorId;

    @PrePersist
    public void onCreate() {
        this.fechaReporte = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoNecesidad.PENDIENTE;
        }
        if (this.urgencia == null) {
            this.urgencia = NivelUrgencia.MEDIA;
        }
    }
}
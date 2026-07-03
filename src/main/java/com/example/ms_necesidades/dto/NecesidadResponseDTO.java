package com.example.ms_necesidades.dto;

import com.example.ms_necesidades.enums.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
public class NecesidadResponseDTO {
    private Long id;
    private String descripcion;
    private TipoRecurso tipoRecurso;
    private Integer cantidadSolicitada;
    private String ubicacion;
    private String region;
    private EstadoNecesidad estado;
    private NivelUrgencia urgencia;
    private LocalDateTime fechaReporte;
    private Long reportadoPorId;
}
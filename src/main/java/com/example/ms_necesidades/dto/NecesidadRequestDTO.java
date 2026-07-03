package com.example.ms_necesidades.dto;

import com.example.ms_necesidades.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NecesidadRequestDTO {
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El tipo de recurso es obligatorio")
    private TipoRecurso tipoRecurso;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidadSolicitada;

    @NotBlank(message = "La ubicación es obligatoria")
    private String ubicacion;

    @NotBlank(message = "La región es obligatoria")
    private String region;

    private NivelUrgencia urgencia;
    private Long reportadoPorId;
}
package com.example.ms_necesidades.factory;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.model.Necesidad;
import org.springframework.stereotype.Component;

@Component
public class NecesidadRopaFactory implements NecesidadFactory {

    @Override
    public Necesidad crear(NecesidadRequestDTO dto) {
        return Necesidad.builder()
                .descripcion(dto.getDescripcion())
                .tipoRecurso(dto.getTipoRecurso())
                .cantidadSolicitada(dto.getCantidadSolicitada())
                .ubicacion(dto.getUbicacion())
                .region(dto.getRegion())
                .urgencia(NivelUrgencia.BAJA) // ropa urgencia BAJA
                .reportadoPorId(dto.getReportadoPorId())
                .build();
    }
}
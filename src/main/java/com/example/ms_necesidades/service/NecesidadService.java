package com.example.ms_necesidades.service;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.dto.NecesidadResponseDTO;
import com.example.ms_necesidades.enums.EstadoNecesidad;

import java.util.List;

public interface NecesidadService {

    NecesidadResponseDTO crear (NecesidadRequestDTO dto);
    List<NecesidadResponseDTO>listarTodas();
    NecesidadResponseDTO obtenerPorId(Long id);
    List<NecesidadResponseDTO> listarPorEstado(EstadoNecesidad estado);
    List<NecesidadResponseDTO>listarPorRegion(String region);
    NecesidadResponseDTO actualizarEstado(Long id, EstadoNecesidad nuevoEstado);

}

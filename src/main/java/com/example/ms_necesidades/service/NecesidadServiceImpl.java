package com.example.ms_necesidades.service;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.dto.NecesidadResponseDTO;
import com.example.ms_necesidades.enums.EstadoNecesidad;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.exception.NecesidadNotFoundException;
import com.example.ms_necesidades.factory.NecesidadFactorySelector;
import com.example.ms_necesidades.model.Necesidad;
import com.example.ms_necesidades.repository.NecesidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NecesidadServiceImpl implements NecesidadService {

    private final NecesidadRepository necesidadRepository;
    private final NecesidadFactorySelector factorySelector;

    @Override
    public NecesidadResponseDTO crear(NecesidadRequestDTO dto) {
        Necesidad necesidad = factorySelector.crear(dto);
        return toDTO(necesidadRepository.save(necesidad));
    }


    @Override
    public NecesidadResponseDTO obtenerPorId(Long id) {
        return necesidadRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new NecesidadNotFoundException(id));
    }

    @Override
    public List<NecesidadResponseDTO> listarTodas(){
        return necesidadRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NecesidadResponseDTO> listarPorEstado(EstadoNecesidad estado){
        return necesidadRepository.findByEstado(estado)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NecesidadResponseDTO> listarPorRegion(String region) {
        return necesidadRepository.findByRegion(region)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NecesidadResponseDTO actualizarEstado(Long id, EstadoNecesidad nuevoEstado){
        Necesidad necesidad = necesidadRepository.findById(id)
                .orElseThrow(()-> new NecesidadNotFoundException(id));
        necesidad.setEstado(nuevoEstado);
        return toDTO(necesidadRepository.save(necesidad));
    }

    private NecesidadResponseDTO toDTO (Necesidad n){
        return NecesidadResponseDTO.builder()
                .id(n.getId())
                .descripcion(n.getDescripcion())
                .tipoRecurso(n.getTipoRecurso())
                .cantidadSolicitada(n.getCantidadSolicitada())
                .ubicacion(n.getUbicacion())
                .region(n.getRegion())
                .estado(n.getEstado())
                .urgencia(n.getUrgencia())
                .fechaReporte(n.getFechaReporte())
                .reportadoPorId(n.getReportadoPorId())
                .build();
    }
}

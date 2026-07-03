package com.example.ms_necesidades;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.dto.NecesidadResponseDTO;
import com.example.ms_necesidades.enums.EstadoNecesidad;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.enums.TipoRecurso;
import com.example.ms_necesidades.exception.NecesidadNotFoundException;
import com.example.ms_necesidades.model.Necesidad;
import com.example.ms_necesidades.repository.NecesidadRepository;
import com.example.ms_necesidades.service.NecesidadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NecesidadServiceImplTest {
    @Mock
    private NecesidadRepository necesidadRepository;

    @InjectMocks
    private NecesidadServiceImpl necesidadService;

    private NecesidadRequestDTO buildResquestDTO(){
        NecesidadRequestDTO dto=new NecesidadRequestDTO();
        dto.setDescripcion("Ropa de abrigo urgente");
        dto.setTipoRecurso(TipoRecurso.ROPA);
        dto.setCantidadSolicitada(50);
        dto.setUbicacion("Concepción centro");
        dto.setRegion("Biobío");
        dto.setUrgencia(NivelUrgencia.ALTA);
        return dto;

    }

    private Necesidad buildNecesidad() {
        return Necesidad.builder()
                .id(1L)
                .descripcion("Ropa de abrigo urgente")
                .tipoRecurso(TipoRecurso.ROPA)
                .cantidadSolicitada(50)
                .ubicacion("Concepción centro")
                .region("Biobío")
                .estado(EstadoNecesidad.PENDIENTE)
                .urgencia(NivelUrgencia.ALTA)
                .build();
    }

    @Test
    void testCrearNecesidadRetornaDTO(){
        NecesidadRequestDTO dto =buildResquestDTO();
        NecesidadRequestDTO saved= buildResquestDTO();
        when(necesidadRepository.save(any())).thenReturn(saved);

        NecesidadResponseDTO resultado = necesidadService.crear(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(EstadoNecesidad.PENDIENTE, resultado.getEstado());
        assertEquals(NivelUrgencia.ALTA, resultado.getUrgencia());
        verify(necesidadRepository, times(1)).save(any());

    }
    @Test
    void testCrearUsaUrgenciaMediaSiNoSeEnvia() {
        NecesidadRequestDTO dto = buildResquestDTO();
        dto.setUrgencia(null);

        Necesidad saved = buildNecesidad();
        saved.setUrgencia(NivelUrgencia.MEDIA);

        when(necesidadRepository.save(any())).thenReturn(saved);

        NecesidadResponseDTO resultado = necesidadService.crear(dto);
        assertEquals(NivelUrgencia.MEDIA, resultado.getUrgencia());
    }

    @Test
    void testListarTodasDevuelveLista() {
        when(necesidadRepository.findAll()).thenReturn(List.of(buildNecesidad()));

        List<NecesidadResponseDTO> resultado = necesidadService.listarTodas();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void testListarTodasDevuelveListaVacia() {
        when(necesidadRepository.findAll()).thenReturn(List.of());

        List<NecesidadResponseDTO> resultado = necesidadService.listarTodas();

        assertTrue(resultado.isEmpty());
    }

    @Test
    void testObtenerPorIdExistente() {
        when(necesidadRepository.findById(1L)).thenReturn(Optional.of(buildNecesidad()));

        NecesidadResponseDTO resultado = necesidadService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerPorIdNoExistenteLanzaExcepcion() {
        when(necesidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NecesidadNotFoundException.class, () -> necesidadService.obtenerPorId(99L));
    }

    @Test
    void testListarPorEstado() {
        when(necesidadRepository.findByEstado(EstadoNecesidad.PENDIENTE))
                .thenReturn(List.of(buildNecesidad()));

        List<NecesidadResponseDTO> resultado = necesidadService.listarPorEstado(EstadoNecesidad.PENDIENTE);

        assertFalse(resultado.isEmpty());
        assertEquals(EstadoNecesidad.PENDIENTE, resultado.get(0).getEstado());
    }

    @Test
    void testListarPorRegion() {
        when(necesidadRepository.findByRegion("Biobío"))
                .thenReturn(List.of(buildNecesidad()));

        List<NecesidadResponseDTO> resultado = necesidadService.listarPorRegion("Biobío");

        assertFalse(resultado.isEmpty());
        assertEquals("Biobío", resultado.get(0).getRegion());
    }

    @Test
    void testActualizarEstadoExitoso() {
        Necesidad n = buildNecesidad();

        when(necesidadRepository.findById(1L)).thenReturn(Optional.of(n));
        when(necesidadRepository.save(any())).thenReturn(n);

        NecesidadResponseDTO resultado = necesidadService.actualizarEstado(1L, EstadoNecesidad.EN_ATENCION);

        assertNotNull(resultado);
        verify(necesidadRepository, times(1)).save(any());
    }

    @Test
    void testActualizarEstadoNoExistenteLanzaExcepcion() {
        when(necesidadRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NecesidadNotFoundException.class,
                () -> necesidadService.actualizarEstado(99L, EstadoNecesidad.RESUELTA));
    }
}




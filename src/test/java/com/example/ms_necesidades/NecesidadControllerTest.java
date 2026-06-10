package com.example.ms_necesidades;

import com.example.ms_necesidades.controller.NecesidadController;
import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.dto.NecesidadResponseDTO;
import com.example.ms_necesidades.enums.EstadoNecesidad;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.enums.TipoRecurso;
import com.example.ms_necesidades.service.NecesidadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser; // ← nuevo
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // ← nuevo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NecesidadController.class)
@WithMockUser // ← nuevo: simula usuario autenticado en todos los tests
class NecesidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private NecesidadService necesidadService;

    private NecesidadResponseDTO buildResponseDTO() {
        return NecesidadResponseDTO.builder()
                .id(1L)
                .descripcion("Ropa de abrigo")
                .tipoRecurso(TipoRecurso.ROPA)
                .cantidadSolicitada(50)
                .ubicacion("Valparaíso")
                .region("Valparaíso")
                .estado(EstadoNecesidad.PENDIENTE)
                .urgencia(NivelUrgencia.BAJA)
                .fechaReporte(LocalDateTime.now())
                .build();
    }

    private NecesidadRequestDTO buildRequestDTO() {
        NecesidadRequestDTO dto = new NecesidadRequestDTO();
        dto.setDescripcion("Ropa de abrigo");
        dto.setTipoRecurso(TipoRecurso.ROPA);
        dto.setCantidadSolicitada(50);
        dto.setUbicacion("Valparaíso");
        dto.setRegion("Valparaíso");
        dto.setUrgencia(NivelUrgencia.BAJA);
        return dto;
    }

    @Test
    void testCrearNecesidad_retorna201() throws Exception {
        when(necesidadService.crear(any())).thenReturn(buildResponseDTO());

        mockMvc.perform(post("/api/necesidades")
                        .with(csrf()) // ← nuevo
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildRequestDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.descripcion").value("Ropa de abrigo"));
    }

    @Test
    void testListarTodas_retorna200() throws Exception {
        when(necesidadService.listarTodas()).thenReturn(List.of(buildResponseDTO()));

        mockMvc.perform(get("/api/necesidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testObtenerPorId_retorna200() throws Exception {
        when(necesidadService.obtenerPorId(1L)).thenReturn(buildResponseDTO());

        mockMvc.perform(get("/api/necesidades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testFiltrarPorEstado_retorna200() throws Exception {
        when(necesidadService.listarPorEstado(EstadoNecesidad.PENDIENTE))
                .thenReturn(List.of(buildResponseDTO()));

        mockMvc.perform(get("/api/necesidades/estado/PENDIENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testFiltrarPorRegion_retorna200() throws Exception {
        when(necesidadService.listarPorRegion("Valparaíso"))
                .thenReturn(List.of(buildResponseDTO()));

        mockMvc.perform(get("/api/necesidades/region/Valparaíso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testActualizarEstado_retorna200() throws Exception {
        NecesidadResponseDTO updated = buildResponseDTO();
        updated.setEstado(EstadoNecesidad.EN_ATENCION);

        when(necesidadService.actualizarEstado(eq(1L), eq(EstadoNecesidad.EN_ATENCION)))
                .thenReturn(updated);

        mockMvc.perform(patch("/api/necesidades/1/estado")
                        .with(csrf()) // ← nuevo
                        .param("nuevoEstado", "EN_ATENCION"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_ATENCION"));
    }

    @Test
    void testCrearNecesidad_sinDescripcion_retorna400() throws Exception {
        NecesidadRequestDTO dto = buildRequestDTO();
        dto.setDescripcion(null);

        mockMvc.perform(post("/api/necesidades")
                        .with(csrf()) // ← nuevo
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
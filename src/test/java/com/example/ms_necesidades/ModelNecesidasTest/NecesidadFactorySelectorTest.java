package com.example.ms_necesidades.ModelNecesidasTest;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.enums.TipoRecurso;
import com.example.ms_necesidades.factory.*;
import com.example.ms_necesidades.model.Necesidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NecesidadFactorySelectorTest {
    private NecesidadFactorySelector selector;

    @BeforeEach
    void setUp() {
        selector = new NecesidadFactorySelector(
                new NecesidadMedicaFactory(),
                new NecesidadAlimentoFactory(),
                new NecesidadRopaFactory(),
                new NecesidadHigieneFactory(),
                new NecesidadOtroFactory()
        );
    }

    private NecesidadRequestDTO buildDTO(TipoRecurso tipo, NivelUrgencia urgencia) {
        NecesidadRequestDTO dto = new NecesidadRequestDTO();
        dto.setDescripcion("Test descripcion");
        dto.setTipoRecurso(tipo);
        dto.setCantidadSolicitada(10);
        dto.setUbicacion("Santiago");
        dto.setRegion("Metropolitana");
        dto.setUrgencia(urgencia);
        return dto;
    }

    @Test
    void testInsumoMedicoSiempreUrgenciaAlta(){
        NecesidadRequestDTO dto =buildDTO(TipoRecurso.INSUMO_MEDICO,NivelUrgencia.ALTA);
        Necesidad result = selector.crear(dto);
        assertEquals(NivelUrgencia.ALTA, result.getUrgencia());
        assertEquals(TipoRecurso.INSUMO_MEDICO, result.getTipoRecurso());
    }

    @Test
    void testAlimentoSiempreUrgenciaMedia() {
        NecesidadRequestDTO dto = buildDTO(TipoRecurso.ALIMENTO_NO_PERECIBLE, NivelUrgencia.ALTA);
        Necesidad result = selector.crear(dto);
        assertEquals(NivelUrgencia.MEDIA, result.getUrgencia());
        assertEquals(TipoRecurso.ALIMENTO_NO_PERECIBLE, result.getTipoRecurso());
    }

    @Test
    void testRopaSiempreUrgenciaBaja() {
        NecesidadRequestDTO dto = buildDTO(TipoRecurso.ROPA, NivelUrgencia.ALTA);
        Necesidad result = selector.crear(dto);
        assertEquals(NivelUrgencia.BAJA, result.getUrgencia());
        assertEquals(TipoRecurso.ROPA, result.getTipoRecurso());
    }

    @Test
    void testHigieneSiempreUrgenciaMedia() {
        NecesidadRequestDTO dto = buildDTO(TipoRecurso.HIGIENE, NivelUrgencia.ALTA);
        Necesidad result = selector.crear(dto);
        assertEquals(NivelUrgencia.MEDIA, result.getUrgencia());
        assertEquals(TipoRecurso.HIGIENE, result.getTipoRecurso());
    }

    @Test
    void testOtroUsaUrgenciaBajaSiDTOEsNull() {
        NecesidadRequestDTO dto = buildDTO(TipoRecurso.OTRO, null);
        Necesidad result = selector.crear(dto);
        assertEquals(NivelUrgencia.BAJA, result.getUrgencia());
    }

    @Test
    void testFactoryMapea_DescripcionUbicacionRegion() {
        NecesidadRequestDTO dto = buildDTO(TipoRecurso.INSUMO_MEDICO, NivelUrgencia.ALTA);
        Necesidad result = selector.crear(dto);
        assertEquals("Test descripcion", result.getDescripcion());
        assertEquals("Santiago", result.getUbicacion());
        assertEquals("Metropolitana", result.getRegion());
        assertEquals(10, result.getCantidadSolicitada());
    }



}

package com.example.ms_necesidades.repository;

import com.example.ms_necesidades.enums.*;
import com.example.ms_necesidades.model.Necesidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class NecesidadRepositoryTests {

    @Autowired
    private NecesidadRepository necesidadRepository;

    private Necesidad necesidad1;
    private Necesidad necesidad2;

    @BeforeEach
    void setUp() {
        necesidadRepository.deleteAll();

        necesidad1 = Necesidad.builder()
                .descripcion("Falta agua embotellada")
                .tipoRecurso(TipoRecurso.ALIMENTO_NO_PERECIBLE)
                .cantidadSolicitada(100)
                .ubicacion("Gimnasio Municipal")
                .region("Valparaíso")
                .estado(EstadoNecesidad.PENDIENTE)
                .urgencia(NivelUrgencia.ALTA)
                .reportadoPorId(10L)
                .build();

        necesidad2 = Necesidad.builder()
                .descripcion("Ropa de invierno abrigo")
                .tipoRecurso(TipoRecurso.ROPA)
                .cantidadSolicitada(50)
                .ubicacion("Escuela Alemania")
                .region("Biobío")
                .estado(EstadoNecesidad.EN_ATENCION)
                .urgencia(NivelUrgencia.MEDIA)
                .reportadoPorId(10L)
                .build();

        necesidadRepository.save(necesidad1);
        necesidadRepository.save(necesidad2);
    }

    @Test
    void testGuardar_Exito() {
        Necesidad nueva = Necesidad.builder()
                .descripcion("Kits de aseo personal")
                .tipoRecurso(TipoRecurso.HIGIENE)
                .cantidadSolicitada(20)
                .ubicacion("Sede vecinal")
                .region("Metropolitana")
                .build();

        Necesidad guardada = necesidadRepository.save(nueva);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getEstado()).isEqualTo(EstadoNecesidad.PENDIENTE);
    }

    @Test
    void testBuscarPorEstado_FiltroPendiente() {
        List<Necesidad> result = necesidadRepository.findByEstado(EstadoNecesidad.PENDIENTE);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescripcion()).contains("agua");
    }

    @Test
    void testBuscarPorRegion_FiltroBiobio() {
        List<Necesidad> result = necesidadRepository.findByRegion("Biobío");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTipoRecurso()).isEqualTo(TipoRecurso.ROPA);
    }

    @Test
    void testBuscarPorTipoRecurso_FiltroAlimento() {
        List<Necesidad> result = necesidadRepository.findByTipoRecurso(TipoRecurso.ALIMENTO_NO_PERECIBLE);
        assertThat(result).hasSize(1);
    }

    @Test
    void testBuscarPorReportadoPorId_FiltroUsuario() {
        List<Necesidad> result = necesidadRepository.findByReportadoPorId(10L);
        assertThat(result).hasSize(2);
    }
}
package com.example.ms_necesidades.ModelNecesidasTest;

import com.example.ms_necesidades.enums.EstadoNecesidad;
import com.example.ms_necesidades.enums.NivelUrgencia;
import com.example.ms_necesidades.enums.TipoRecurso;
import com.example.ms_necesidades.model.Necesidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class NesecidadTest {

    @Test
    void testOnCreateEstablecePendienteYFecha(){
        Necesidad n =new Necesidad();
        n.onCreate();
        assertEquals(EstadoNecesidad.PENDIENTE ,n.getEstado());
        assertNotNull(n.getFechaReporte());
    }

    @Test
    void testOnCreateEstableceUrgenciaMediaPorDefecto(){
        Necesidad n =new Necesidad();
        n.onCreate();
        assertEquals(NivelUrgencia.MEDIA, n.getUrgencia());
    }

    @Test
    void testOnCreateNoSobreescribeUrgenciaExistente(){
        Necesidad n =new Necesidad();
        n.setUrgencia(NivelUrgencia.ALTA);
        n.onCreate();
        assertEquals(NivelUrgencia.ALTA,n.getUrgencia());
    }

    @Test
    void testConstructorYGetters() {
        Necesidad n = Necesidad.builder()
                .id(1L)
                .descripcion("Ropa de abrigo")
                .tipoRecurso(TipoRecurso.ROPA)
                .cantidadSolicitada(50)
                .ubicacion("Valparaíso")
                .region("Valparaíso")
                .build();

        assertEquals(1L, n.getId());
        assertEquals("Ropa de abrigo", n.getDescripcion());
        assertEquals(TipoRecurso.ROPA, n.getTipoRecurso());
        assertEquals(50, n.getCantidadSolicitada());
    }

    @Test
    void testEqualsHashCode() {
        Necesidad n1 = Necesidad.builder().id(1L).descripcion("Test").build();
        Necesidad n2 = Necesidad.builder().id(1L).descripcion("Test").build();
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

}

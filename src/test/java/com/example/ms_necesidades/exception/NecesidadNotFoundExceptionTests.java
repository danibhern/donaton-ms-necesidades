package com.example.ms_necesidades.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class NecesidadNotFoundExceptionTests {

    @Test
    void obtenerMensaje_Exito() {
        Long idInexistente = 404L;
        NecesidadNotFoundException exception = new NecesidadNotFoundException(idInexistente);

        assertThat(exception.getMessage()).isEqualTo("Necesidad no encontrada con ID: 404");
    }
}
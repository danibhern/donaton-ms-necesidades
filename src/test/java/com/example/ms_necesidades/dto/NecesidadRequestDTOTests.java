package com.example.ms_necesidades.dto;

import com.example.ms_necesidades.enums.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class NecesidadRequestDTOTests {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidacion_DtoCorrecto() {
        NecesidadRequestDTO dto = new NecesidadRequestDTO();
        dto.setDescripcion("Ayuda para damnificados");
        dto.setTipoRecurso(TipoRecurso.ALIMENTO_NO_PERECIBLE);
        dto.setCantidadSolicitada(10);
        dto.setUbicacion("Centro de acopio");
        dto.setRegion("Metropolitana");

        Set<ConstraintViolation<NecesidadRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void testValidacion_CamposVacios() {
        NecesidadRequestDTO dto = new NecesidadRequestDTO();
        dto.setDescripcion("");
        dto.setTipoRecurso(TipoRecurso.ALIMENTO_NO_PERECIBLE);
        dto.setCantidadSolicitada(10);
        dto.setUbicacion("Centro de acopio");
        dto.setRegion("   ");

        Set<ConstraintViolation<NecesidadRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(2);
    }

    @Test
    void testValidacion_CantidadInvalida() {
        NecesidadRequestDTO dto = new NecesidadRequestDTO();
        dto.setDescripcion("Medicamentos");
        dto.setTipoRecurso(TipoRecurso.INSUMO_MEDICO);
        dto.setCantidadSolicitada(0);
        dto.setUbicacion("Hospital");
        dto.setRegion("Biobío");

        Set<ConstraintViolation<NecesidadRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(1);

        String mensajeError = violations.iterator().next().getMessage();
        assertThat(mensajeError).isEqualTo("La cantidad debe ser mayor a 0");
    }
}
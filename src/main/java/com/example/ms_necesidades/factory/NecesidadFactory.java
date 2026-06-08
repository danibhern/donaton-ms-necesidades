package com.example.ms_necesidades.factory;

import com.example.ms_necesidades.dto.NecesidadRequestDTO;
import com.example.ms_necesidades.model.Necesidad;

public interface NecesidadFactory {
    Necesidad crear(NecesidadRequestDTO dto);
}

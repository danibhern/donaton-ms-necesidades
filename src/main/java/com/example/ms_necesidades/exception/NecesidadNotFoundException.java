package com.example.ms_necesidades.exception;

public class NecesidadNotFoundException extends RuntimeException {
    public NecesidadNotFoundException(Long id) {
        super("Necesidad no encontrada con ID: " + id);
    }

}
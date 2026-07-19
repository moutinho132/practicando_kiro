package com.catastrofes.registro.domain.exception;

/**
 * Excepción de dominio lanzada cuando se intenta registrar
 * una persona que ya existe en el sistema.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public class PersonaYaRegistradaException extends RuntimeException {
    
    private final String cedula;

    public PersonaYaRegistradaException(String cedula) {
        super(String.format("La persona con cédula %s ya está registrada en el sistema", cedula));
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }
}

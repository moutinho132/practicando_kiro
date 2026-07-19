package com.catastrofes.registro.domain.exception;

/**
 * Excepción de dominio lanzada cuando se intenta registrar
 * un familiar que ya existe en el sistema.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public class FamiliarYaRegistradoException extends RuntimeException {
    
    private final String cedula;

    public FamiliarYaRegistradoException(String cedula) {
        super(String.format("El familiar con cédula %s ya está registrado en el sistema", cedula));
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }
}

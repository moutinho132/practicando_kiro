package com.catastrofes.registro.domain.exception;

/**
 * Excepción de dominio lanzada cuando no se encuentra
 * una persona en el sistema.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public class PersonaNoEncontradaException extends RuntimeException {
    
    private final Long personaId;
    private final String cedula;

    public PersonaNoEncontradaException(Long personaId) {
        super(String.format("No se encontró la persona con ID %d", personaId));
        this.personaId = personaId;
        this.cedula = null;
    }

    public PersonaNoEncontradaException(String cedula) {
        super(String.format("No se encontró la persona con cédula %s", cedula));
        this.cedula = cedula;
        this.personaId = null;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public String getCedula() {
        return cedula;
    }
}

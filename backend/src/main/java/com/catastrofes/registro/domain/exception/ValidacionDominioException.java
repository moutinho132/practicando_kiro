package com.catastrofes.registro.domain.exception;

/**
 * Excepción de dominio lanzada cuando falla una validación
 * de reglas de negocio.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public class ValidacionDominioException extends RuntimeException {
    
    private final String campo;

    public ValidacionDominioException(String mensaje) {
        super(mensaje);
        this.campo = null;
    }

    public ValidacionDominioException(String campo, String mensaje) {
        super(mensaje);
        this.campo = campo;
    }

    public String getCampo() {
        return campo;
    }
}

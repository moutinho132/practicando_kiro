package com.catastrofes.registro.adapter.in.web.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para errores de la API.
 * Proporciona información estructurada sobre el error ocurrido.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public record ErrorResponse(
    
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<FieldError> fieldErrors
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }
    
    public record FieldError(
        String field,
        String message
    ) {}
}

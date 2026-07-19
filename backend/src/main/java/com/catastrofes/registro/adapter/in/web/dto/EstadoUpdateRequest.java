package com.catastrofes.registro.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de petición para actualizar el estado de una persona.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public record EstadoUpdateRequest(
    
    @NotBlank(message = "El nuevo estado es obligatorio")
    String nuevoEstado,
    
    boolean confirmado,
    
    String motivo
) {}

package com.catastrofes.registro.adapter.in.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * DTO de petición para búsqueda de personas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public record BusquedaRequest(
    
    String nombres,
    String apellidos,
    String cedula,
    String provincia,
    String canton,
    
    @Min(value = 0, message = "La edad mínima debe ser mayor o igual a 0")
    Integer edadMinima,
    
    @Max(value = 120, message = "La edad máxima debe ser menor o igual a 120")
    Integer edadMaxima,
    
    String genero,
    String estado,
    String colorCabello,
    String grupoSanguineo,
    
    @Min(value = 0, message = "La página debe ser mayor o igual a 0")
    Integer pagina,
    
    @Min(value = 1, message = "El tamaño de página debe ser mayor o igual a 1")
    @Max(value = 100, message = "El tamaño de página debe ser menor o igual a 100")
    Integer tamanio
) {
    public BusquedaRequest {
        // Valores por defecto para paginación
        if (pagina == null) {
            pagina = 0;
        }
        if (tamanio == null) {
            tamanio = 20;
        }
    }
}

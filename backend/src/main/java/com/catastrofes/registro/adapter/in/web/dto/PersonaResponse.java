package com.catastrofes.registro.adapter.in.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para datos de una persona.
 * No incluye información sensible ni datos de eliminación.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public record PersonaResponse(
    
    Long id,
    String nombres,
    String apellidos,
    String nombreCompleto,
    String cedula,
    LocalDate fechaNacimiento,
    int edad,
    String genero,
    String telefono,
    
    // Ubicación
    String provincia,
    String canton,
    String parroquia,
    String direccion,
    String latitud,
    String longitud,
    
    // Rasgos físicos
    String colorPiel,
    String colorOjos,
    String colorCabello,
    String estatura,
    String complexion,
    String senasParticulares,
    String discapacidad,
    String grupoSanguineo,
    
    // Estado y fechas
    String estado,
    String notas,
    boolean esMenor,
    Long adultoResponsableId,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {
    /**
     * Retorna una respuesta simplificada para búsqueda pública.
     */
    public PersonaResponse toPublicResponse() {
        return new PersonaResponse(
            this.id, this.nombres, this.apellidos, this.nombreCompleto,
            null, // No mostrar cédula en búsqueda pública
            this.fechaNacimiento, this.edad, this.genero,
            null, // No mostrar teléfono
            this.provincia, this.canton, this.parroquia, this.direccion,
            null, null, // No mostrar coordenadas
            null, null, null, null, null,
            null, null, null, // No mostrar rasgos detallados
            this.estado, null, this.esMenor, this.adultoResponsableId,
            this.fechaRegistro, null
        );
    }
}

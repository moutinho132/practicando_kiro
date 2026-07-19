package com.catastrofes.registro.adapter.in.web.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO de petición para el registro de una persona adulta.
 * Utiliza Java Records para inmutabilidad.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public record PersonaRegistroRequest(
    
    @NotBlank(message = "Los nombres son obligatorios")
    @Size(min = 2, max = 100, message = "Los nombres deben tener entre 2 y 100 caracteres")
    String nombres,
    
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(min = 2, max = 100, message = "Los apellidos deben tener entre 2 y 100 caracteres")
    String apellidos,
    
    @NotBlank(message = "La cédula es obligatoria para adultos")
    @Pattern(regexp = "^\\d{10}$", message = "La cédula debe tener 10 dígitos")
    String cedula,
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    LocalDate fechaNacimiento,
    
    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "^[MFO]$", message = "El género debe ser M, F u O")
    String genero,
    
    @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Formato de teléfono inválido")
    String telefono,
    
    // Ubicación
    @NotBlank(message = "La provincia es obligatoria")
    String provincia,
    
    @NotBlank(message = "El cantón es obligatorio")
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
    
    // Estado y notas
    @NotBlank(message = "El estado es obligatorio")
    String estado,
    
    String notas
) {}

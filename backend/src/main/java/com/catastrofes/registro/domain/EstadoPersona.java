package com.catastrofes.registro.domain;

/**
 * Enumeración que define los estados posibles de una persona
 * en el sistema de catástrofes.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public enum EstadoPersona {
    /**
     * Persona reportada como desaparecida
     */
    DESAPARECIDO,
    
    /**
     * Persona encontrada con vida
     */
    ENCONTRADO,
    
    /**
     * Persona identificada y confirmada su identidad
     */
    IDENTIFICADO,
    
    /**
     * Persona fallecida
     */
    FALLECIDO
}

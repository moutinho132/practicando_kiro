package com.catastrofes.registro.port.in;

import com.catastrofes.registro.domain.Persona;

/**
 * Puerto de entrada (Use Case) para el registro de personas en el sistema.
 * Define las operaciones disponibles para registrar y gestionar personas
 * afectadas por catástrofes.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public interface RegistrarPersonaUseCase {
    
    /**
     * Registra una nueva persona en el sistema.
     * 
     * @param persona La persona a registrar
     * @return La persona registrada con su ID asignado
     * @throws com.catastrofes.registro.domain.exception.PersonaYaRegistradaException 
     *         si la persona ya existe (cédula duplicada)
     * @throws com.catastrofes.registro.domain.exception.ValidacionDominioException 
     *         si los datos no cumplen las reglas de validación
     */
    Persona registrarPersona(Persona persona);
    
    /**
     * Actualiza los datos de una persona existente.
     * 
     * @param id El ID de la persona a actualizar
     * @param persona Los nuevos datos de la persona
     * @return La persona actualizada
     * @throws com.catastrofes.registro.domain.exception.PersonaNoEncontradaException 
     *         si la persona no existe
     */
    Persona actualizarPersona(Long id, Persona persona);
    
    /**
     * Actualiza el estado de una persona.
     * 
     * @param id El ID de la persona
     * @param nuevoEstado El nuevo estado (DESAPARECIDO, ENCONTRADO, IDENTIFICADO, FALLECIDO)
     * @param confirmado Si el cambio requiere confirmación (para IDENTIFICADO)
     * @return La persona con el estado actualizado
     */
    Persona actualizarEstado(Long id, String nuevoEstado, boolean confirmado);
    
    /**
     * Busca una persona por su ID.
     * 
     * @param id El ID de la persona
     * @return La persona encontrada
     * @throws com.catastrofes.registro.domain.exception.PersonaNoEncontradaException 
     *         si la persona no existe
     */
    Persona buscarPorId(Long id);
    
    /**
     * Busca una persona por su número de cédula.
     * 
     * @param cedula El número de cédula
     * @return La persona encontrada
     * @throws com.catastrofes.registro.domain.exception.PersonaNoEncontradaException 
     *         si la persona no existe
     */
    Persona buscarPorCedula(String cedula);
    
    /**
     * Realiza eliminación lógica de una persona.
     * La persona no se elimina físicamente, solo se marca como eliminada.
     * 
     * @param id El ID de la persona a eliminar
     */
    void eliminarLogicamente(Long id);
    
    /**
     * Restaura una persona que fue eliminada lógicamente.
     * 
     * @param id El ID de la persona a restaurar
     * @return La persona restaurada
     */
    Persona restaurar(Long id);
}

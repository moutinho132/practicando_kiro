package com.catastrofes.registro.port.out;

import com.catastrofes.registro.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (Repository Port) para la persistencia de personas.
 * Define las operaciones de persistencia necesarias para el dominio,
 * independientemente de la implementación concreta (JPA, MongoDB, etc.).
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public interface PersonaRepositoryPort {
    
    /**
     * Guarda una nueva persona o actualiza una existente.
     * 
     * @param persona La persona a guardar
     * @return La persona guardada con su ID asignado
     */
    Persona save(Persona persona);
    
    /**
     * Busca una persona por su ID.
     * 
     * @param id El ID de la persona
     * @return Optional con la persona si existe, vacío si no
     */
    Optional<Persona> findById(Long id);
    
    /**
     * Busca una persona por su número de cédula.
     * 
     * @param cedula El número de cédula
     * @return Optional con la persona si existe, vacío si no
     */
    Optional<Persona> findByCedula(String cedula);
    
    /**
     * Verifica si existe una persona con la cédula dada.
     * 
     * @param cedula El número de cédula
     * @return true si existe, false si no
     */
    boolean existsByCedula(String cedula);
    
    /**
     * Busca todas las personas que coinciden con los nombres dados.
     * 
     * @param nombres Texto a buscar en nombres o apellidos
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> findByNombresOrApellidosContainingIgnoreCase(String nombres, Pageable pageable);
    
    /**
     * Busca personas por provincia y/o cantón.
     * 
     * @param provincia La provincia (puede ser null)
     * @param canton El cantón (puede ser null)
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> findByUbicacion(String provincia, String canton, Pageable pageable);
    
    /**
     * Busca todas las personas activas (no eliminadas lógicamente).
     * 
     * @param pageable Información de paginación
     * @return Página de todas las personas activas
     */
    Page<Persona> findAllActivos(Pageable pageable);
    
    /**
     * Busca personas por estado.
     * 
     * @param estado El estado a buscar
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> findByEstado(String estado, Pageable pageable);
    
    /**
     * Elimina una persona (soft delete).
     * 
     * @param id El ID de la persona a eliminar
     */
    void deleteById(Long id);
    
    /**
     * Restaura una persona eliminada lógicamente.
     * 
     * @param id El ID de la persona a restaurar
     * @return La persona restaurada
     */
    Persona restoreById(Long id);
    
    /**
     * Cuenta el total de personas registradas.
     * 
     * @return Número total de personas
     */
    long count();
    
    /**
     * Cuenta las personas por estado.
     * 
     * @param estado El estado
     * @return Número de personas en ese estado
     */
    long countByEstado(String estado);
}

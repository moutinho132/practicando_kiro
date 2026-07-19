package com.catastrofes.registro.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad PersonaJpaEntity.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Repository
public interface PersonaJpaRepository extends JpaRepository<PersonaJpaEntity, Long> {
    
    /**
     * Busca una persona por su número de cédula.
     */
    Optional<PersonaJpaEntity> findByCedula(String cedula);
    
    /**
     * Verifica si existe una persona con la cédula dada.
     */
    boolean existsByCedula(String cedula);
    
    /**
     * Busca personas por nombres o apellidos (búsqueda parcial, insensible a mayúsculas).
     */
    @Query("SELECT p FROM PersonaJpaEntity p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<PersonaJpaEntity> findByNombresOrApellidosContainingIgnoreCase(
        @Param("query") String query, Pageable pageable);
    
    /**
     * Busca personas por provincia y/o cantón.
     */
    @Query("SELECT p FROM PersonaJpaEntity p WHERE " +
           "(:provincia IS NULL OR LOWER(p.ubicacion.provincia) = LOWER(:provincia)) AND " +
           "(:canton IS NULL OR LOWER(p.ubicacion.canton) = LOWER(:canton)) AND " +
           "p.eliminadoLogico = false")
    Page<PersonaJpaEntity> findByUbicacion(
        @Param("provincia") String provincia, 
        @Param("canton") String canton, 
        Pageable pageable);
    
    /**
     * Busca todas las personas activas (no eliminadas lógicamente).
     */
    Page<PersonaJpaEntity> findByEliminadoLogicoFalse(Pageable pageable);
    
    /**
     * Busca personas por estado.
     */
    Page<PersonaJpaEntity> findByEstado(PersonaJpaEntity.EstadoPersonaEnum estado, Pageable pageable);
    
    /**
     * Cuenta personas por estado.
     */
    long countByEstado(PersonaJpaEntity.EstadoPersonaEnum estado);
    
    /**
     * Busca personas por provincia.
     */
    @Query("SELECT p FROM PersonaJpaEntity p WHERE " +
           "LOWER(p.ubicacion.provincia) = LOWER(:provincia) AND " +
           "p.eliminadoLogico = false")
    Page<PersonaJpaEntity> findByProvincia(@Param("provincia") String provincia, Pageable pageable);
    
    /**
     * Búsqueda avanzada con múltiples filtros.
     */
    @Query("SELECT p FROM PersonaJpaEntity p WHERE " +
           "(:nombres IS NULL OR LOWER(p.nombres) LIKE LOWER(CONCAT('%', :nombres, '%'))) AND " +
           "(:apellidos IS NULL OR LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :apellidos, '%'))) AND " +
           "(:cedula IS NULL OR p.cedula = :cedula) AND " +
           "(:provincia IS NULL OR LOWER(p.ubicacion.provincia) = LOWER(:provincia)) AND " +
           "(:canton IS NULL OR LOWER(p.ubicacion.canton) = LOWER(:canton)) AND " +
           "(:genero IS NULL OR p.genero = :genero) AND " +
           "(:estado IS NULL OR p.estado = :estado) AND " +
           "p.eliminadoLogico = false")
    Page<PersonaJpaEntity> buscarAvanzado(
        @Param("nombres") String nombres,
        @Param("apellidos") String apellidos,
        @Param("cedula") String cedula,
        @Param("provincia") String provincia,
        @Param("canton") String canton,
        @Param("genero") String genero,
        @Param("estado") PersonaJpaEntity.EstadoPersonaEnum estado,
        Pageable pageable);
}

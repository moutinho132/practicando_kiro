package com.catastrofes.registro.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad UbicacionJpaEntity.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Repository
public interface UbicacionJpaRepository extends JpaRepository<UbicacionJpaEntity, Long> {
    // Métodos específicos de ubicación si son necesarios
}

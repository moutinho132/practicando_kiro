package com.catastrofes.registro.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JPA para la entidad RasgosFisicosJpaEntity.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Repository
public interface RasgosFisicosJpaRepository extends JpaRepository<RasgosFisicosJpaEntity, Long> {
    // Métodos específicos de rasgos físicos si son necesarios
}

package com.catastrofes.registro.adapter.out.persistence;

import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.port.out.PersonaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador que implementa PersonaRepositoryPort usando Spring Data JPA.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Repository
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {
    
    @Override
    public Persona save(Persona persona) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Optional<Persona> findById(Long id) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Optional<Persona> findByCedula(String cedula) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public boolean existsByCedula(String cedula) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Page<Persona> findByNombresOrApellidosContainingIgnoreCase(String nombres, Pageable pageable) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Page<Persona> findByUbicacion(String provincia, String canton, Pageable pageable) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Page<Persona> findAllActivos(Pageable pageable) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Page<Persona> findByEstado(String estado, Pageable pageable) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public Persona restoreById(Long id) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public long count() {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
    
    @Override
    public long countByEstado(String estado) {
        throw new UnsupportedOperationException("Implementación pendiente en Fase 3");
    }
}

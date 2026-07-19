package com.catastrofes.registro.adapter.out.persistence;

import com.catastrofes.registro.adapter.out.persistence.mapper.PersonaMapper;
import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.port.out.PersonaRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Adaptador que implementa PersonaRepositoryPort usando Spring Data JPA.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Repository
@Transactional
public class PersonaRepositoryAdapter implements PersonaRepositoryPort {
    
    private final PersonaJpaRepository personaJpaRepository;
    private final PersonaMapper personaMapper;
    
    public PersonaRepositoryAdapter(PersonaJpaRepository personaJpaRepository,
                                   PersonaMapper personaMapper) {
        this.personaJpaRepository = personaJpaRepository;
        this.personaMapper = personaMapper;
    }
    
    @Override
    public Persona save(Persona persona) {
        PersonaJpaEntity entity = personaMapper.toEntity(persona);
        PersonaJpaEntity savedEntity = personaJpaRepository.save(entity);
        return personaMapper.toDomain(savedEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findById(Long id) {
        return personaJpaRepository.findById(id)
            .map(personaMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findByCedula(String cedula) {
        return personaJpaRepository.findByCedula(cedula)
            .map(personaMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCedula(String cedula) {
        return personaJpaRepository.existsByCedula(cedula);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findByNombresOrApellidosContainingIgnoreCase(String query, Pageable pageable) {
        return personaJpaRepository.findByNombresOrApellidosContainingIgnoreCase(query, pageable)
            .map(personaMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findByUbicacion(String provincia, String canton, Pageable pageable) {
        return personaJpaRepository.findByUbicacion(provincia, canton, pageable)
            .map(personaMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findAllActivos(Pageable pageable) {
        return personaJpaRepository.findByEliminadoLogicoFalse(pageable)
            .map(personaMapper::toDomain);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Persona> findByEstado(String estado, Pageable pageable) {
        PersonaJpaEntity.EstadoPersonaEnum estadoEnum = 
            PersonaJpaEntity.EstadoPersonaEnum.valueOf(estado);
        return personaJpaRepository.findByEstado(estadoEnum, pageable)
            .map(personaMapper::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        personaJpaRepository.findById(id).ifPresent(entity -> {
            entity.setEliminadoLogico(true);
            personaJpaRepository.save(entity);
        });
    }
    
    @Override
    public Persona restoreById(Long id) {
        return personaJpaRepository.findById(id).map(entity -> {
            entity.setEliminadoLogico(false);
            PersonaJpaEntity restored = personaJpaRepository.save(entity);
            return personaMapper.toDomain(restored);
        }).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long count() {
        return personaJpaRepository.count();
    }
    
    @Override
    @Transactional(readOnly = true)
    public long countByEstado(String estado) {
        PersonaJpaEntity.EstadoPersonaEnum estadoEnum = 
            PersonaJpaEntity.EstadoPersonaEnum.valueOf(estado);
        return personaJpaRepository.countByEstado(estadoEnum);
    }
}


package com.catastrofes.registro.application;

import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.domain.exception.PersonaNoEncontradaException;
import com.catastrofes.registro.domain.exception.PersonaYaRegistradaException;
import com.catastrofes.registro.domain.exception.ValidacionDominioException;
import com.catastrofes.registro.domain.service.ValidadorPersonaService;
import com.catastrofes.registro.port.in.RegistrarPersonaUseCase;
import com.catastrofes.registro.port.out.PersonaRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio de aplicación para gestión de personas.
 * Implementa los casos de uso relacionados con el registro y consulta de personas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Service
@Transactional
public class PersonaService implements RegistrarPersonaUseCase {
    
    private final PersonaRepositoryPort personaRepository;
    private final ValidadorPersonaService validadorPersona;
    
    public PersonaService(PersonaRepositoryPort personaRepository,
                         ValidadorPersonaService validadorPersona) {
        this.personaRepository = personaRepository;
        this.validadorPersona = validadorPersona;
    }
    
    @Override
    public Persona registrarPersona(Persona persona) {
        // Validar que la persona tenga todos los campos obligatorios
        validadorPersona.validarPersonaCompleta(persona);
        
        // Validar cédula duplicada
        if (persona.tieneCedula() && personaRepository.existsByCedula(persona.getCedula())) {
            throw new PersonaYaRegistradaException(persona.getCedula());
        }
        
        // Establecer fecha de registro
        Persona personaARegistrar = new Persona.Builder()
            .id(persona.getId())
            .nombres(persona.getNombres())
            .apellidos(persona.getApellidos())
            .cedula(persona.getCedula())
            .fechaNacimiento(persona.getFechaNacimiento())
            .genero(persona.getGenero())
            .telefono(persona.getTelefono())
            .ubicacion(persona.getUbicacion())
            .rasgosFisicos(persona.getRasgosFisicos())
            .estado(persona.getEstado())
            .notas(persona.getNotas())
            .fechaRegistro(LocalDateTime.now())
            .fechaActualizacion(LocalDateTime.now())
            .esMenor(validadorPersona.esMenorDeEdad(persona.getFechaNacimiento()))
            .adultoResponsableId(persona.getAdultoResponsableId())
            .eliminadoLogico(false)
            .build();
        
        return personaRepository.save(personaARegistrar);
    }
    
    @Override
    public Persona actualizarPersona(Long id, Persona persona) {
        Persona personaExistente = personaRepository.findById(id)
            .orElseThrow(() -> new PersonaNoEncontradaException(id));
        
        // Validar los nuevos datos
        validadorPersona.validarPersonaCompleta(persona);
        
        // Verificar si la cédula está siendo cambiada y si ya existe
        if (persona.tieneCedula() && 
            !persona.getCedula().equals(personaExistente.getCedula()) &&
            personaRepository.existsByCedula(persona.getCedula())) {
            throw new PersonaYaRegistradaException(persona.getCedula());
        }
        
        // Actualizar la persona
        Persona personaActualizada = new Persona.Builder()
            .id(id)
            .nombres(persona.getNombres())
            .apellidos(persona.getApellidos())
            .cedula(persona.getCedula())
            .fechaNacimiento(persona.getFechaNacimiento())
            .genero(persona.getGenero())
            .telefono(persona.getTelefono())
            .ubicacion(persona.getUbicacion())
            .rasgosFisicos(persona.getRasgosFisicos())
            .estado(persona.getEstado())
            .notas(persona.getNotas())
            .fechaRegistro(personaExistente.getFechaRegistro())
            .fechaActualizacion(LocalDateTime.now())
            .esMenor(validadorPersona.esMenorDeEdad(persona.getFechaNacimiento()))
            .adultoResponsableId(persona.getAdultoResponsableId())
            .eliminadoLogico(false)
            .build();
        
        return personaRepository.save(personaActualizada);
    }
    
    @Override
    public Persona actualizarEstado(Long id, String nuevoEstado, boolean confirmado) {
        Persona persona = personaRepository.findById(id)
            .orElseThrow(() -> new PersonaNoEncontradaException(id));
        
        // Validar transición de estado
        validarTransicionEstado(persona.getEstado().name(), nuevoEstado, confirmado);
        
        Persona personaActualizada = new Persona.Builder()
            .id(persona.getId())
            .nombres(persona.getNombres())
            .apellidos(persona.getApellidos())
            .cedula(persona.getCedula())
            .fechaNacimiento(persona.getFechaNacimiento())
            .genero(persona.getGenero())
            .telefono(persona.getTelefono())
            .ubicacion(persona.getUbicacion())
            .rasgosFisicos(persona.getRasgosFisicos())
            .estado(com.catastrofes.registro.domain.EstadoPersona.valueOf(nuevoEstado))
            .notas(persona.getNotas())
            .fechaRegistro(persona.getFechaRegistro())
            .fechaActualizacion(LocalDateTime.now())
            .esMenor(persona.isEsMenor())
            .adultoResponsableId(persona.getAdultoResponsableId())
            .eliminadoLogico(persona.isEliminadoLogico())
            .build();
        
        return personaRepository.save(personaActualizada);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Persona buscarPorId(Long id) {
        return personaRepository.findById(id)
            .orElseThrow(() -> new PersonaNoEncontradaException(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public Persona buscarPorCedula(String cedula) {
        return personaRepository.findByCedula(cedula)
            .orElseThrow(() -> new PersonaNoEncontradaException(cedula));
    }
    
    @Override
    public void eliminarLogicamente(Long id) {
        personaRepository.deleteById(id);
    }
    
    @Override
    public Persona restaurar(Long id) {
        Persona persona = personaRepository.restoreById(id);
        if (persona == null) {
            throw new PersonaNoEncontradaException(id);
        }
        return persona;
    }
    
    /**
     * Valida la transición de estado según las reglas de negocio.
     */
    private void validarTransicionEstado(String estadoActual, String nuevoEstado, boolean confirmado) {
        // El estado IDENTIFICADO requiere confirmación
        if ("IDENTIFICADO".equals(nuevoEstado) && !confirmado) {
            throw new ValidacionDominioException(
                "confirmado",
                "El cambio a IDENTIFICADO requiere confirmación de un administrador"
            );
        }
        
        // Validar que el nuevo estado sea válido
        try {
            com.catastrofes.registro.domain.EstadoPersona.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            throw new ValidacionDominioException("estado", "Estado no válido: " + nuevoEstado);
        }
    }
}

package com.catastrofes.registro.adapter.out.persistence.mapper;

import com.catastrofes.registro.adapter.out.persistence.PersonaJpaEntity;
import com.catastrofes.registro.adapter.out.persistence.RasgosFisicosJpaEntity;
import com.catastrofes.registro.adapter.out.persistence.UbicacionJpaEntity;
import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.domain.RasgosFisicos;
import com.catastrofes.registro.domain.Ubicacion;
import com.catastrofes.registro.adapter.in.web.dto.PersonaResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades JPA y objetos del dominio.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Component
public class PersonaMapper {
    
    /**
     * Convierte una entidad JPA a un objeto del dominio.
     */
    public Persona toDomain(PersonaJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Persona.Builder()
            .id(entity.getId())
            .nombres(entity.getNombres())
            .apellidos(entity.getApellidos())
            .cedula(entity.getCedula())
            .fechaNacimiento(entity.getFechaNacimiento())
            .genero(entity.getGenero())
            .telefono(entity.getTelefono())
            .ubicacion(toUbicacionDomain(entity.getUbicacion()))
            .rasgosFisicos(toRasgosFisicosDomain(entity.getRasgosFisicos()))
            .estado(convertirEstado(entity.getEstado()))
            .notas(entity.getNotas())
            .fechaRegistro(entity.getFechaRegistro())
            .fechaActualizacion(entity.getFechaActualizacion())
            .esMenor(entity.isEsMenor())
            .adultoResponsableId(entity.getAdultoResponsableId())
            .eliminadoLogico(entity.isEliminadoLogico())
            .build();
    }
    
    /**
     * Convierte un objeto del dominio a una entidad JPA.
     */
    public PersonaJpaEntity toEntity(Persona persona) {
        if (persona == null) {
            return null;
        }
        
        PersonaJpaEntity entity = new PersonaJpaEntity();
        entity.setId(persona.getId());
        entity.setNombres(persona.getNombres());
        entity.setApellidos(persona.getApellidos());
        entity.setCedula(persona.getCedula());
        entity.setFechaNacimiento(persona.getFechaNacimiento());
        entity.setGenero(persona.getGenero());
        entity.setTelefono(persona.getTelefono());
        entity.setUbicacion(toUbicacionEntity(persona.getUbicacion()));
        entity.setRasgosFisicos(toRasgosFisicosEntity(persona.getRasgosFisicos()));
        entity.setEstado(convertirEstado(persona.getEstado()));
        entity.setNotas(persona.getNotas());
        entity.setFechaRegistro(persona.getFechaRegistro());
        entity.setFechaActualizacion(persona.getFechaActualizacion());
        entity.setEsMenor(persona.isEsMenor());
        entity.setAdultoResponsableId(persona.getAdultoResponsableId());
        entity.setEliminadoLogico(persona.isEliminadoLogico());
        
        return entity;
    }
    
    /**
     * Convierte una entidad JPA a un DTO de respuesta.
     */
    public PersonaResponse toResponse(PersonaJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        UbicacionJpaEntity ubicacion = entity.getUbicacion();
        RasgosFisicosJpaEntity rasgos = entity.getRasgosFisicos();
        
        return new PersonaResponse(
            entity.getId(),
            entity.getNombres(),
            entity.getApellidos(),
            entity.getNombres() + " " + entity.getApellidos(),
            entity.getCedula(),
            entity.getFechaNacimiento(),
            entity.getFechaNacimiento() != null ? 
                java.time.LocalDate.now().getYear() - entity.getFechaNacimiento().getYear() : 0,
            entity.getGenero(),
            entity.getTelefono(),
            ubicacion != null ? ubicacion.getProvincia() : null,
            ubicacion != null ? ubicacion.getCanton() : null,
            ubicacion != null ? ubicacion.getParroquia() : null,
            ubicacion != null ? ubicacion.getDireccion() : null,
            ubicacion != null && ubicacion.getLatitud() != null ? 
                ubicacion.getLatitud().toString() : null,
            ubicacion != null && ubicacion.getLongitud() != null ? 
                ubicacion.getLongitud().toString() : null,
            rasgos != null ? rasgos.getColorPiel() : null,
            rasgos != null ? rasgos.getColorOjos() : null,
            rasgos != null ? rasgos.getColorCabello() : null,
            rasgos != null ? rasgos.getEstatura() : null,
            rasgos != null ? rasgos.getComplexion() : null,
            rasgos != null ? rasgos.getSenasParticulares() : null,
            rasgos != null ? rasgos.getDiscapacidad() : null,
            rasgos != null ? rasgos.getGrupoSanguineo() : null,
            entity.getEstado() != null ? entity.getEstado().name() : null,
            entity.getNotas(),
            entity.isEsMenor(),
            entity.getAdultoResponsableId(),
            entity.getFechaRegistro(),
            entity.getFechaActualizacion()
        );
    }
    
    // Métodos privados de conversión
    
    private Ubicacion toUbicacionDomain(UbicacionJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Ubicacion.Builder()
            .id(entity.getId())
            .latitud(entity.getLatitud())
            .longitud(entity.getLongitud())
            .provincia(entity.getProvincia())
            .canton(entity.getCanton())
            .parroquia(entity.getParroquia())
            .direccion(entity.getDireccion())
            .referencia(entity.getReferencia())
            .build();
    }
    
    private UbicacionJpaEntity toUbicacionEntity(Ubicacion ubicacion) {
        if (ubicacion == null) {
            return null;
        }
        
        UbicacionJpaEntity entity = new UbicacionJpaEntity();
        entity.setId(ubicacion.getId());
        entity.setLatitud(ubicacion.getLatitud());
        entity.setLongitud(ubicacion.getLongitud());
        entity.setProvincia(ubicacion.getProvincia());
        entity.setCanton(ubicacion.getCanton());
        entity.setParroquia(ubicacion.getParroquia());
        entity.setDireccion(ubicacion.getDireccion());
        entity.setReferencia(ubicacion.getReferencia());
        
        return entity;
    }
    
    private RasgosFisicos toRasgosFisicosDomain(RasgosFisicosJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new RasgosFisicos.Builder()
            .id(entity.getId())
            .colorPiel(entity.getColorPiel())
            .colorOjos(entity.getColorOjos())
            .colorCabello(entity.getColorCabello())
            .estatura(entity.getEstatura())
            .complexion(entity.getComplexion())
            .senasParticulares(entity.getSenasParticulares())
            .discapacidad(entity.getDiscapacidad())
            .grupoSanguineo(entity.getGrupoSanguineo())
            .build();
    }
    
    private RasgosFisicosJpaEntity toRasgosFisicosEntity(RasgosFisicos rasgos) {
        if (rasgos == null) {
            return null;
        }
        
        RasgosFisicosJpaEntity entity = new RasgosFisicosJpaEntity();
        entity.setId(rasgos.getId());
        entity.setColorPiel(rasgos.getColorPiel());
        entity.setColorOjos(rasgos.getColorOjos());
        entity.setColorCabello(rasgos.getColorCabello());
        entity.setEstatura(rasgos.getEstatura());
        entity.setComplexion(rasgos.getComplexion());
        entity.setSenasParticulares(rasgos.getSenasParticulares());
        entity.setDiscapacidad(rasgos.getDiscapacidad());
        entity.setGrupoSanguineo(rasgos.getGrupoSanguineo());
        
        return entity;
    }
    
    private com.catastrofes.registro.domain.EstadoPersona convertirEstado(
            PersonaJpaEntity.EstadoPersonaEnum estadoEnum) {
        if (estadoEnum == null) {
            return null;
        }
        
        return com.catastrofes.registro.domain.EstadoPersona.valueOf(estadoEnum.name());
    }
    
    private PersonaJpaEntity.EstadoPersonaEnum convertirEstado(
            com.catastrofes.registro.domain.EstadoPersona estado) {
        if (estado == null) {
            return null;
        }
        
        return PersonaJpaEntity.EstadoPersonaEnum.valueOf(estado.name());
    }
}

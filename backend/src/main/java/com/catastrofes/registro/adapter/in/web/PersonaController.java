package com.catastrofes.registro.adapter.in.web;

import com.catastrofes.registro.adapter.in.web.dto.EstadoUpdateRequest;
import com.catastrofes.registro.adapter.in.web.dto.PersonaRegistroRequest;
import com.catastrofes.registro.adapter.in.web.dto.PersonaResponse;
import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.domain.Ubicacion;
import com.catastrofes.registro.port.in.RegistrarPersonaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

/**
 * Controlador REST para gestión de personas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/personas")
public class PersonaController {
    
    private final RegistrarPersonaUseCase registrarPersonaUseCase;
    
    public PersonaController(RegistrarPersonaUseCase registrarPersonaUseCase) {
        this.registrarPersonaUseCase = registrarPersonaUseCase;
    }
    
    /**
     * Registra una nueva persona adulta.
     */
    @PostMapping
    public ResponseEntity<PersonaResponse> registrarPersona(
            @Valid @RequestBody PersonaRegistroRequest request) {
        
        Persona persona = convertirRequestADominio(request);
        Persona personaRegistrada = registrarPersonaUseCase.registrarPersona(persona);
        
        return ResponseEntity
            .created(URI.create("/api/v1/personas/" + personaRegistrada.getId()))
            .body(convertirAResponse(personaRegistrada));
    }
    
    /**
     * Busca una persona por su ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonaResponse> buscarPorId(@PathVariable Long id) {
        Persona persona = registrarPersonaUseCase.buscarPorId(id);
        return ResponseEntity.ok(convertirAResponse(persona));
    }
    
    /**
     * Actualiza el estado de una persona.
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<PersonaResponse> actualizarEstado(
            @PathVariable Long id,
            @Valid @RequestBody EstadoUpdateRequest request) {
        
        Persona persona = registrarPersonaUseCase.actualizarEstado(
            id, request.nuevoEstado(), request.confirmado());
        
        return ResponseEntity.ok(convertirAResponse(persona));
    }
    
    /**
     * Elimina lógicamente una persona.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPersona(@PathVariable Long id) {
        registrarPersonaUseCase.eliminarLogicamente(id);
    }
    
    /**
     * Restaura una persona eliminada.
     */
    @PostMapping("/{id}/restaurar")
    public ResponseEntity<PersonaResponse> restaurarPersona(@PathVariable Long id) {
        Persona persona = registrarPersonaUseCase.restaurar(id);
        return ResponseEntity.ok(convertirAResponse(persona));
    }
    
    // Métodos privados de conversión
    
    private Persona convertirRequestADominio(PersonaRegistroRequest request) {
        Ubicacion ubicacion = new Ubicacion.Builder()
            .provincia(request.provincia())
            .canton(request.canton())
            .parroquia(request.parroquia())
            .direccion(request.direccion())
            .latitud(request.latitud() != null ? new BigDecimal(request.latitud()) : null)
            .longitud(request.longitud() != null ? new BigDecimal(request.longitud()) : null)
            .build();
        
        return new Persona.Builder()
            .nombres(request.nombres())
            .apellidos(request.apellidos())
            .cedula(request.cedula())
            .fechaNacimiento(request.fechaNacimiento())
            .genero(request.genero())
            .telefono(request.telefono())
            .ubicacion(ubicacion)
            .estado(com.catastrofes.registro.domain.EstadoPersona.valueOf(request.estado()))
            .notas(request.notas())
            .build();
    }
    
    private PersonaResponse convertirAResponse(Persona persona) {
        return new PersonaResponse(
            persona.getId(),
            persona.getNombres(),
            persona.getApellidos(),
            persona.getNombreCompleto(),
            persona.getCedula(),
            persona.getFechaNacimiento(),
            persona.calcularEdad(),
            persona.getGenero(),
            persona.getTelefono(),
            persona.getUbicacion() != null ? persona.getUbicacion().getProvincia() : null,
            persona.getUbicacion() != null ? persona.getUbicacion().getCanton() : null,
            persona.getUbicacion() != null ? persona.getUbicacion().getParroquia() : null,
            persona.getUbicacion() != null ? persona.getUbicacion().getDireccion() : null,
            persona.getUbicacion() != null && persona.getUbicacion().getLatitud() != null ? 
                persona.getUbicacion().getLatitud().toString() : null,
            persona.getUbicacion() != null && persona.getUbicacion().getLongitud() != null ? 
                persona.getUbicacion().getLongitud().toString() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getColorPiel() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getColorOjos() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getColorCabello() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getEstatura() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getComplexion() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getSenasParticulares() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getDiscapacidad() : null,
            persona.getRasgosFisicos() != null ? persona.getRasgosFisicos().getGrupoSanguineo() : null,
            persona.getEstado() != null ? persona.getEstado().name() : null,
            persona.getNotas(),
            persona.isEsMenor(),
            persona.getAdultoResponsableId(),
            persona.getFechaRegistro(),
            persona.getFechaActualizacion()
        );
    }
}

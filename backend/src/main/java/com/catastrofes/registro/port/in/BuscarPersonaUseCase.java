package com.catastrofes.registro.port.in;

import com.catastrofes.registro.domain.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Puerto de entrada (Use Case) para la búsqueda de personas en el sistema.
 * Define las operaciones de búsqueda disponibles tanto para usuarios
 * públicos como para rescatistas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public interface BuscarPersonaUseCase {
    
    /**
     * Búsqueda pública simple por nombre o cédula.
     * No incluye información sensible y solo retorna personas no eliminadas.
     * 
     * @param query Texto de búsqueda (nombre, apellido o cédula)
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> busquedaPublica(String query, Pageable pageable);
    
    /**
     * Búsqueda avanzada para rescatistas con múltiples filtros.
     * 
     * @param filtro Objeto con los criterios de búsqueda
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> busquedaAvanzada(FiltroBusqueda filtro, Pageable pageable);
    
    /**
     * Busca personas por ubicación (provincia y/o cantón).
     * 
     * @param provincia La provincia (opcional)
     * @param canton El cantón (opcional)
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> buscarPorUbicacion(String provincia, String canton, Pageable pageable);
    
    /**
     * Busca personas por estado.
     * 
     * @param estado El estado a buscar
     * @param pageable Información de paginación
     * @return Página de resultados
     */
    Page<Persona> buscarPorEstado(String estado, Pageable pageable);
    
    /**
     * Obtiene el historial de cambios de estado de una persona.
     * 
     * @param personaId El ID de la persona
     * @return Lista de cambios de estado
     */
    List<CambioEstado> obtenerHistorialEstados(Long personaId);
    
    /**
     * Clase record para encapsular los filtros de búsqueda.
     */
    record FiltroBusqueda(
        String nombres,
        String apellidos,
        String cedula,
        String provincia,
        String canton,
        Integer edadMinima,
        Integer edadMaxima,
        String genero,
        String estado,
        String colorCabello,
        String grupoSanguineo
    ) {}
    
    /**
     * Clase record para representar un cambio de estado.
     */
    record CambioEstado(
        Long id,
        Long personaId,
        String estadoAnterior,
        String estadoNuevo,
        String usuarioResponsable,
        String motivo,
        java.time.LocalDateTime fechaCambio
    ) {}
}

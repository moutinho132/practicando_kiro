package com.catastrofes.registro.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de dominio que representa a una persona registrada en el sistema
 * de catástrofes. Esta clase es inmutable y contiene toda la información
 * necesaria para identificar y localizar a una persona afectada.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public final class Persona {
    
    private final Long id;
    private final String nombres;
    private final String apellidos;
    private final String cedula;
    private final LocalDate fechaNacimiento;
    private final String genero;
    private final String telefono;
    private final Ubicacion ubicacion;
    private final RasgosFisicos rasgosFisicos;
    private final EstadoPersona estado;
    private final String notas;
    private final LocalDateTime fechaRegistro;
    private final LocalDateTime fechaActualizacion;
    private final boolean esMenor;
    private final Long adultoResponsableId;
    private final boolean eliminadoLogico;

    /**
     * Constructor completo para crear una instancia de Persona.
     */
    private Persona(Builder builder) {
        this.id = builder.id;
        this.nombres = builder.nombres;
        this.apellidos = builder.apellidos;
        this.cedula = builder.cedula;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.genero = builder.genero;
        this.telefono = builder.telefono;
        this.ubicacion = builder.ubicacion;
        this.rasgosFisicos = builder.rasgosFisicos;
        this.estado = builder.estado;
        this.notas = builder.notas;
        this.fechaRegistro = builder.fechaRegistro;
        this.fechaActualizacion = builder.fechaActualizacion;
        this.esMenor = builder.esMenor;
        this.adultoResponsableId = builder.adultoResponsableId;
        this.eliminadoLogico = builder.eliminadoLogico;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCedula() { return cedula; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getGenero() { return genero; }
    public String getTelefono() { return telefono; }
    public Ubicacion getUbicacion() { return ubicacion; }
    public RasgosFisicos getRasgosFisicos() { return rasgosFisicos; }
    public EstadoPersona getEstado() { return estado; }
    public String getNotas() { return notas; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public boolean isEsMenor() { return esMenor; }
    public Long getAdultoResponsableId() { return adultoResponsableId; }
    public boolean isEliminadoLogico() { return eliminadoLogico; }

    /**
     * Calcula la edad de la persona basándose en su fecha de nacimiento.
     */
    public int calcularEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    /**
     * Retorna el nombre completo de la persona.
     */
    public String getNombreCompleto() {
        return String.format("%s %s", nombres, apellidos);
    }

    /**
     * Verifica si la persona tiene una cédula registrada.
     */
    public boolean tieneCedula() {
        return cedula != null && !cedula.trim().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(id, persona.id) &&
               Objects.equals(cedula, persona.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cedula);
    }

    @Override
    public String toString() {
        return String.format("Persona{id=%d, nombre='%s', estado=%s}", 
            id, getNombreCompleto(), estado);
    }

    /**
     * Builder para crear instancias de Persona de forma inmutable.
     */
    public static class Builder {
        private Long id;
        private String nombres;
        private String apellidos;
        private String cedula;
        private LocalDate fechaNacimiento;
        private String genero;
        private String telefono;
        private Ubicacion ubicacion;
        private RasgosFisicos rasgosFisicos;
        private EstadoPersona estado = EstadoPersona.DESAPARECIDO;
        private String notas;
        private LocalDateTime fechaRegistro;
        private LocalDateTime fechaActualizacion;
        private boolean esMenor;
        private Long adultoResponsableId;
        private boolean eliminadoLogico;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nombres(String nombres) {
            this.nombres = nombres;
            return this;
        }

        public Builder apellidos(String apellidos) {
            this.apellidos = apellidos;
            return this;
        }

        public Builder cedula(String cedula) {
            this.cedula = cedula;
            return this;
        }

        public Builder fechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder genero(String genero) {
            this.genero = genero;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder ubicacion(Ubicacion ubicacion) {
            this.ubicacion = ubicacion;
            return this;
        }

        public Builder rasgosFisicos(RasgosFisicos rasgosFisicos) {
            this.rasgosFisicos = rasgosFisicos;
            return this;
        }

        public Builder estado(EstadoPersona estado) {
            this.estado = estado;
            return this;
        }

        public Builder notas(String notas) {
            this.notas = notas;
            return this;
        }

        public Builder fechaRegistro(LocalDateTime fechaRegistro) {
            this.fechaRegistro = fechaRegistro;
            return this;
        }

        public Builder fechaActualizacion(LocalDateTime fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
            return this;
        }

        public Builder esMenor(boolean esMenor) {
            this.esMenor = esMenor;
            return this;
        }

        public Builder adultoResponsableId(Long adultoResponsableId) {
            this.adultoResponsableId = adultoResponsableId;
            return this;
        }

        public Builder eliminadoLogico(boolean eliminadoLogico) {
            this.eliminadoLogico = eliminadoLogico;
            return this;
        }

        public Persona build() {
            Objects.requireNonNull(nombres, "Los nombres son obligatorios");
            Objects.requireNonNull(apellidos, "Los apellidos son obligatorios");
            Objects.requireNonNull(fechaNacimiento, "La fecha de nacimiento es obligatoria");
            Objects.requireNonNull(estado, "El estado es obligatorio");
            
            return new Persona(this);
        }
    }
}

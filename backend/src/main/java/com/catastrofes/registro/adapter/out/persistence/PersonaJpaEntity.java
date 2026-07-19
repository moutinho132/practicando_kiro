package com.catastrofes.registro.adapter.out.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad JPA para persistencia de personas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Entity
@Table(name = "personas", indexes = {
    @Index(name = "idx_persona_cedula", columnList = "cedula", unique = true),
    @Index(name = "idx_persona_estado", columnList = "estado"),
    @Index(name = "idx_persona_provincia", columnList = "provincia")
})
public class PersonaJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    private String nombres;
    
    @Column(length = 100, nullable = false)
    private String apellidos;
    
    @Column(length = 10, unique = true)
    private String cedula;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(length = 1, nullable = false)
    private String genero;
    
    @Column(length = 20)
    private String telefono;
    
    // Relaciones con Ubicación y Rasgos Físicos
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id")
    private UbicacionJpaEntity ubicacion;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rasgos_fisicos_id")
    private RasgosFisicosJpaEntity rasgosFisicos;
    
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPersonaEnum estado;
    
    @Column(columnDefinition = "TEXT")
    private String notas;
    
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "es_menor", nullable = false)
    private boolean esMenor = false;
    
    @Column(name = "adulto_responsable_id")
    private Long adultoResponsableId;
    
    @Column(name = "eliminado_logico", nullable = false)
    private boolean eliminadoLogico = false;
    
    @Column(name = "foto_url", length = 500)
    private String fotoUrl;
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Constructores
    public PersonaJpaEntity() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public UbicacionJpaEntity getUbicacion() { return ubicacion; }
    public void setUbicacion(UbicacionJpaEntity ubicacion) { this.ubicacion = ubicacion; }
    
    public RasgosFisicosJpaEntity getRasgosFisicos() { return rasgosFisicos; }
    public void setRasgosFisicos(RasgosFisicosJpaEntity rasgosFisicos) { this.rasgosFisicos = rasgosFisicos; }
    
    public EstadoPersonaEnum getEstado() { return estado; }
    public void setEstado(EstadoPersonaEnum estado) { this.estado = estado; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
    
    public boolean isEsMenor() { return esMenor; }
    public void setEsMenor(boolean esMenor) { this.esMenor = esMenor; }
    
    public Long getAdultoResponsableId() { return adultoResponsableId; }
    public void setAdultoResponsableId(Long adultoResponsableId) { this.adultoResponsableId = adultoResponsableId; }
    
    public boolean isEliminadoLogico() { return eliminadoLogico; }
    public void setEliminadoLogico(boolean eliminadoLogico) { this.eliminadoLogico = eliminadoLogico; }
    
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    
    /**
     * Enum para el estado de persona en la entidad JPA.
     */
    public enum EstadoPersonaEnum {
        DESAPARECIDO,
        ENCONTRADO,
        IDENTIFICADO,
        FALLECIDO,
        HERIDO,
        HOSPITALIZADO,
        RESGUARDADO
    }
}

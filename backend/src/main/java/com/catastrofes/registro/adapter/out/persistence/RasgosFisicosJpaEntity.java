package com.catastrofes.registro.adapter.out.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa la tabla de rasgos físicos en la base de datos.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Entity
@Table(name = "rasgos_fisicos")
public class RasgosFisicosJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "color_piel", length = 50)
    private String colorPiel;
    
    @Column(name = "color_ojos", length = 50)
    private String colorOjos;
    
    @Column(name = "color_cabello", length = 50)
    private String colorCabello;
    
    @Column(length = 10)
    private String estatura;
    
    @Column(length = 50)
    private String complexion;
    
    @Column(name = "senas_particulares", columnDefinition = "TEXT")
    private String senasParticulares;
    
    @Column(length = 255)
    private String discapacidad;
    
    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;
    
    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
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
    public RasgosFisicosJpaEntity() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getColorPiel() { return colorPiel; }
    public void setColorPiel(String colorPiel) { this.colorPiel = colorPiel; }
    
    public String getColorOjos() { return colorOjos; }
    public void setColorOjos(String colorOjos) { this.colorOjos = colorOjos; }
    
    public String getColorCabello() { return colorCabello; }
    public void setColorCabello(String colorCabello) { this.colorCabello = colorCabello; }
    
    public String getEstatura() { return estatura; }
    public void setEstatura(String estatura) { this.estatura = estatura; }
    
    public String getComplexion() { return complexion; }
    public void setComplexion(String complexion) { this.complexion = complexion; }
    
    public String getSenasParticulares() { return senasParticulares; }
    public void setSenasParticulares(String senasParticulares) { this.senasParticulares = senasParticulares; }
    
    public String getDiscapacidad() { return discapacidad; }
    public void setDiscapacidad(String discapacidad) { this.discapacidad = discapacidad; }
    
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}

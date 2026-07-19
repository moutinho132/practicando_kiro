package com.catastrofes.registro.domain;

import java.util.Objects;

/**
 * Value Object que representa los rasgos físicos de una persona
 * para facilitar su identificación en situaciones de catástrofe.
 * Esta clase es inmutable.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public final class RasgosFisicos {
    
    private final Long id;
    private final String colorPiel;
    private final String colorOjos;
    private final String colorCabello;
    private final String estatura;
    private final String complexion;
    private final String senasParticulares;
    private final String discapacidad;
    private final String grupoSanguineo;

    /**
     * Constructor completo para crear una instancia de RasgosFisicos.
     */
    private RasgosFisicos(Builder builder) {
        this.id = builder.id;
        this.colorPiel = builder.colorPiel;
        this.colorOjos = builder.colorOjos;
        this.colorCabello = builder.colorCabello;
        this.estatura = builder.estatura;
        this.complexion = builder.complexion;
        this.senasParticulares = builder.senasParticulares;
        this.discapacidad = builder.discapacidad;
        this.grupoSanguineo = builder.grupoSanguineo;
    }

    // Getters
    public Long getId() { return id; }
    public String getColorPiel() { return colorPiel; }
    public String getColorOjos() { return colorOjos; }
    public String getColorCabello() { return colorCabello; }
    public String getEstatura() { return estatura; }
    public String getComplexion() { return complexion; }
    public String getSenasParticulares() { return senasParticulares; }
    public String getDiscapacidad() { return discapacidad; }
    public String getGrupoSanguineo() { return grupoSanguineo; }

    /**
     * Retorna una descripción resumida de los rasgos físicos.
     */
    public String getDescripcionResumida() {
        StringBuilder sb = new StringBuilder();
        if (estatura != null) sb.append(estatura).append("m, ");
        if (complexion != null) sb.append(complexion).append(", ");
        if (colorCabello != null) sb.append("cabello ").append(colorCabello);
        return sb.toString();
    }

    /**
     * Verifica si hay señas particulares registradas.
     */
    public boolean tieneSenasParticulares() {
        return senasParticulares != null && !senasParticulares.trim().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RasgosFisicos that = (RasgosFisicos) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("RasgosFisicos{estatura='%s', complexion='%s'}", 
            estatura, complexion);
    }

    /**
     * Builder para crear instancias de RasgosFisicos de forma inmutable.
     */
    public static class Builder {
        private Long id;
        private String colorPiel;
        private String colorOjos;
        private String colorCabello;
        private String estatura;
        private String complexion;
        private String senasParticulares;
        private String discapacidad;
        private String grupoSanguineo;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder colorPiel(String colorPiel) {
            this.colorPiel = colorPiel;
            return this;
        }

        public Builder colorOjos(String colorOjos) {
            this.colorOjos = colorOjos;
            return this;
        }

        public Builder colorCabello(String colorCabello) {
            this.colorCabello = colorCabello;
            return this;
        }

        public Builder estatura(String estatura) {
            this.estatura = estatura;
            return this;
        }

        public Builder complexion(String complexion) {
            this.complexion = complexion;
            return this;
        }

        public Builder senasParticulares(String senasParticulares) {
            this.senasParticulares = senasParticulares;
            return this;
        }

        public Builder discapacidad(String discapacidad) {
            this.discapacidad = discapacidad;
            return this;
        }

        public Builder grupoSanguineo(String grupoSanguineo) {
            this.grupoSanguineo = grupoSanguineo;
            return this;
        }

        public RasgosFisicos build() {
            return new RasgosFisicos(this);
        }
    }
}

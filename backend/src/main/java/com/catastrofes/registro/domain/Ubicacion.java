package com.catastrofes.registro.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object que representa la ubicación geográfica de una persona.
 * Esta clase es inmutable y contiene información de coordenadas GPS
 * y dirección administrativa.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public final class Ubicacion {
    
    private final Long id;
    private final BigDecimal latitud;
    private final BigDecimal longitud;
    private final String provincia;
    private final String canton;
    private final String parroquia;
    private final String direccion;
    private final String referencia;

    /**
     * Constructor completo para crear una instancia de Ubicacion.
     */
    private Ubicacion(Builder builder) {
        this.id = builder.id;
        this.latitud = builder.latitud;
        this.longitud = builder.longitud;
        this.provincia = builder.provincia;
        this.canton = builder.canton;
        this.parroquia = builder.parroquia;
        this.direccion = builder.direccion;
        this.referencia = builder.referencia;
    }

    // Getters
    public Long getId() { return id; }
    public BigDecimal getLatitud() { return latitud; }
    public BigDecimal getLongitud() { return longitud; }
    public String getProvincia() { return provincia; }
    public String getCanton() { return canton; }
    public String getParroquia() { return parroquia; }
    public String getDireccion() { return direccion; }
    public String getReferencia() { return referencia; }

    /**
     * Verifica si la ubicación tiene coordenadas GPS válidas.
     */
    public boolean tieneCoordenadas() {
        return latitud != null && longitud != null;
    }

    /**
     * Retorna la ubicación en formato legible.
     */
    public String getDireccionCompleta() {
        StringBuilder sb = new StringBuilder();
        if (direccion != null && !direccion.isEmpty()) {
            sb.append(direccion);
        }
        if (parroquia != null && !parroquia.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(parroquia);
        }
        if (canton != null && !canton.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(canton);
        }
        if (provincia != null && !provincia.isEmpty()) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(provincia);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ubicacion ubicacion = (Ubicacion) o;
        return Objects.equals(latitud, ubicacion.latitud) &&
               Objects.equals(longitud, ubicacion.longitud) &&
               Objects.equals(provincia, ubicacion.provincia) &&
               Objects.equals(canton, ubicacion.canton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitud, longitud, provincia, canton);
    }

    @Override
    public String toString() {
        return String.format("Ubicacion{provincia='%s', canton='%s', coords=%s,%s}", 
            provincia, canton, latitud, longitud);
    }

    /**
     * Builder para crear instancias de Ubicacion de forma inmutable.
     */
    public static class Builder {
        private Long id;
        private BigDecimal latitud;
        private BigDecimal longitud;
        private String provincia;
        private String canton;
        private String parroquia;
        private String direccion;
        private String referencia;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder latitud(BigDecimal latitud) {
            this.latitud = latitud;
            return this;
        }

        public Builder longitud(BigDecimal longitud) {
            this.longitud = longitud;
            return this;
        }

        public Builder provincia(String provincia) {
            this.provincia = provincia;
            return this;
        }

        public Builder canton(String canton) {
            this.canton = canton;
            return this;
        }

        public Builder parroquia(String parroquia) {
            this.parroquia = parroquia;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder referencia(String referencia) {
            this.referencia = referencia;
            return this;
        }

        public Ubicacion build() {
            return new Ubicacion(this);
        }
    }
}

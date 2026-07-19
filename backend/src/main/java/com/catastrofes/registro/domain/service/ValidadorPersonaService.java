package com.catastrofes.registro.domain.service;

import com.catastrofes.registro.domain.Persona;
import com.catastrofes.registro.domain.exception.ValidacionDominioException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Servicio de dominio encargado de validar las reglas de negocio
 * relacionadas con el registro de personas.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Service
public class ValidadorPersonaService {
    
    private static final Pattern CEDULA_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern TELEFONO_PATTERN = Pattern.compile("^\\+?\\d{9,15}$");
    private static final int EDAD_MAXIMA_MENOR = 18;

    /**
     * Valida que una persona tenga todos los campos obligatorios completos.
     * 
     * @param persona La persona a validar
     * @throws ValidacionDominioException si la validación falla
     */
    public void validarPersonaCompleta(Persona persona) {
        Objects.requireNonNull(persona, "La persona no puede ser nula");
        
        validarNombres(persona.getNombres());
        validarApellidos(persona.getApellidos());
        validarFechaNacimiento(persona.getFechaNacimiento());
        validarGenero(persona.getGenero());
        
        // Validar cédula obligatoria para adultos
        if (!persona.isEsMenor()) {
            validarCedulaObligatoria(persona.getCedula());
        }
        
        // Validar adulto responsable para menores
        if (persona.isEsMenor() && persona.getAdultoResponsableId() == null) {
            throw new ValidacionDominioException(
                "adultoResponsableId", 
                "Los menores de edad deben tener un adulto responsable asignado"
            );
        }
    }

    /**
     * Valida los nombres de una persona.
     */
    public void validarNombres(String nombres) {
        if (nombres == null || nombres.trim().isEmpty()) {
            throw new ValidacionDominioException("nombres", "Los nombres son obligatorios");
        }
        if (nombres.length() < 2) {
            throw new ValidacionDominioException("nombres", "Los nombres deben tener al menos 2 caracteres");
        }
        if (nombres.length() > 100) {
            throw new ValidacionDominioException("nombres", "Los nombres no pueden exceder 100 caracteres");
        }
    }

    /**
     * Valida los apellidos de una persona.
     */
    public void validarApellidos(String apellidos) {
        if (apellidos == null || apellidos.trim().isEmpty()) {
            throw new ValidacionDominioException("apellidos", "Los apellidos son obligatorios");
        }
        if (apellidos.length() < 2) {
            throw new ValidacionDominioException("apellidos", "Los apellidos deben tener al menos 2 caracteres");
        }
        if (apellidos.length() > 100) {
            throw new ValidacionDominioException("apellidos", "Los apellidos no pueden exceder 100 caracteres");
        }
    }

    /**
     * Valida la cédula de identidad (formato ecuatoriano: 10 dígitos).
     */
    public void validarCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            return; // La cédula puede ser null para menores
        }
        
        if (!CEDULA_PATTERN.matcher(cedula).matches()) {
            throw new ValidacionDominioException(
                "cedula", 
                "La cédula debe tener exactamente 10 dígitos"
            );
        }
        
        // Validar dígito verificador (algoritmo ecuatoriano)
        if (!validarDigitoVerificador(cedula)) {
            throw new ValidacionDominioException(
                "cedula", 
                "La cédula ingresada no es válida"
            );
        }
    }

    /**
     * Valida que la cédula sea obligatoria (para adultos).
     */
    public void validarCedulaObligatoria(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new ValidacionDominioException(
                "cedula", 
                "La cédula es obligatoria para personas adultas"
            );
        }
        validarCedula(cedula);
    }

    /**
     * Valida la fecha de nacimiento.
     */
    public void validarFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new ValidacionDominioException("fechaNacimiento", "La fecha de nacimiento es obligatoria");
        }
        
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new ValidacionDominioException(
                "fechaNacimiento", 
                "La fecha de nacimiento no puede ser futura"
            );
        }
        
        if (fechaNacimiento.isBefore(LocalDate.of(1900, 1, 1))) {
            throw new ValidacionDominioException(
                "fechaNacimiento", 
                "La fecha de nacimiento no es válida"
            );
        }
    }

    /**
     * Valida el género.
     */
    public void validarGenero(String genero) {
        if (genero == null || genero.trim().isEmpty()) {
            throw new ValidacionDominioException("genero", "El género es obligatorio");
        }
        
        if (!genero.equals("M") && !genero.equals("F") && !genero.equals("O")) {
            throw new ValidacionDominioException(
                "genero", 
                "El género debe ser M (Masculino), F (Femenino) u O (Otro)"
            );
        }
    }

    /**
     * Valida el teléfono de contacto.
     */
    public void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return; // El teléfono es opcional
        }
        
        if (!TELEFONO_PATTERN.matcher(telefono).matches()) {
            throw new ValidacionDominioException(
                "telefono", 
                "El formato de teléfono no es válido"
            );
        }
    }

    /**
     * Determina si una persona es menor de edad basándose en su fecha de nacimiento.
     */
    public boolean esMenorDeEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            return false;
        }
        int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();
        return edad < EDAD_MAXIMA_MENOR;
    }

    /**
     * Valida el dígito verificador de la cédula ecuatoriana.
     */
    private boolean validarDigitoVerificador(String cedula) {
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) { // Posiciones impares (0, 2, 4, 6, 8)
                digito *= 2;
                if (digito > 9) {
                    digito -= 9;
                }
            }
            suma += digito;
        }
        
        int decenaSuperior = ((suma / 10) + 1) * 10;
        int digitoVerificador = decenaSuperior - suma;
        
        if (digitoVerificador == 10) {
            digitoVerificador = 0;
        }
        
        return digitoVerificador == Character.getNumericValue(cedula.charAt(9));
    }
}

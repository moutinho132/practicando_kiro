package com.catastrofes.registro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación de Registro de Personas en Catástrofes.
 * 
 * Esta aplicación permite registrar y buscar personas afectadas por catástrofes
 * naturales, facilitando la labor de rescatistas y el reencuentro familiar.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@SpringBootApplication
public class RegistroPersonasApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistroPersonasApplication.class, args);
    }
}

package com.catastrofes.registro.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de Spring Security para la aplicación.
 * 
 * <p>Esta configuración establece las reglas de seguridad para los endpoints
 * de la API, incluyendo:
 * <ul>
 *   <li>Desactivación de CSRF para API REST</li>
 *   <li>Sesiones sin estado (stateless)</li>
 *   <li>Control de acceso basado en roles</li>
 *   <li>Acceso a la consola H2 para desarrollo</li>
 * </ul>
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * @param http el objeto HttpSecurity para configurar
     * @return la cadena de filtros de seguridad configurada
     * @throws Exception si ocurre un error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactivar CSRF para API REST
            .csrf(AbstractHttpConfigurer::disable)
            
            // Configurar gestión de sesiones sin estado
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // Configurar autorización de endpoints según especificación
            .authorizeHttpRequests(auth -> auth
                // ========== ENDPOINTS PÚBLICOS ==========
                // Autenticación
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/auth/login").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/auth/refresh").authenticated()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/auth/register").hasRole("ADMIN")
                
                // Búsqueda pública
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/busqueda/**").permitAll()
                
                // Registro de familiares (público)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/familiares").permitAll()
                
                // Subir fotos (público)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/fotos").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/fotos/**").permitAll()
                
                // Consola H2 (solo desarrollo)
                .requestMatchers("/h2-console/**").permitAll()
                
                // ========== ENDPOINTS DE PERSONAS ==========
                // Crear persona - RESCATISTA
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/v1/personas").hasRole("RESCATISTA")
                
                // Obtener persona por ID - RESCATISTA, ADMIN
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/personas/{id}").hasAnyRole("RESCATISTA", "ADMIN")
                
                // Actualizar estado de persona - RESCATISTA
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/personas/{id}/estado").hasRole("RESCATISTA")
                
                // Búsqueda avanzada de personas - RESCATISTA
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/personas/buscar").hasRole("RESCATISTA")
                
                // Eliminación lógica - ADMIN
                .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/v1/personas/{id}").hasRole("ADMIN")
                
                // Restaurar persona eliminada - ADMIN
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/personas/{id}/restaurar").hasRole("ADMIN")
                
                // ========== ENDPOINTS DE FAMILIARES ==========
                // Obtener familiar por ID - RESCATISTA, ADMIN
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/familiares/{id}").hasAnyRole("RESCATISTA", "ADMIN")
                
                // Actualizar datos de familiar - FAMILIAR, ADMIN
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/v1/familiares/{id}").hasAnyRole("FAMILIAR", "ADMIN")
                
                // ========== ENDPOINTS ADMIN ==========
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                // ========== ENDPOINTS GENERALES ==========
                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            
            // Permitir visualización de frames para la consola H2
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            );
        
        return http.build();
    }
}

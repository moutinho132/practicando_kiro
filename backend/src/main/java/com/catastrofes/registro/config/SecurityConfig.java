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
            
            // Configurar autorización de endpoints
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers(
                    "/api/v1/auth/**",
                    "/api/v1/busqueda/**",
                    "/api/v1/familiares",
                    "/api/v1/fotos",
                    "/h2-console/**"
                ).permitAll()
                
                // Endpoints que requieren rol ADMIN
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                
                // Endpoints que requieren autenticación con cualquier rol
                .requestMatchers("/api/v1/rescatistas/**").hasAnyRole("ADMIN", "RESCATISTA")
                .requestMatchers("/api/v1/personas/**").hasAnyRole("ADMIN", "RESCATISTA", "FAMILIAR")
                
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

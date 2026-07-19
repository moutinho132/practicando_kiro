# Paquete: config

## Responsabilidad

Contiene las **configuraciones** de Spring y beans de la aplicación.

## Contenido

### Configuraciones
- `SecurityConfig` - Configuración de Spring Security
- `OpenApiConfig` - Configuración de Swagger/OpenAPI
- `CorsConfig` - Configuración de CORS
- `JpaConfig` - Configuración de JPA
- `BeanConfig` - Definición de beans adicionales

## Principios

1. **Centralización**: Toda la configuración en un solo lugar
2. **Perfilado**: Usar perfiles para diferentes ambientes
3. **Documentado**: Documentar decisiones de configuración
4. **Seguro**: Configuraciones seguras por defecto

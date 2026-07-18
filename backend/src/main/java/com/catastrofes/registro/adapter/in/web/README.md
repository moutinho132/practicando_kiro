# Paquete: adapter.in.web (Adaptadores de Entrada - Web)

## Responsabilidad

Contiene los **controladores REST** que exponen la API del sistema. Transforma peticiones HTTP en llamadas a casos de uso.

## Contenido

### Controladores
- `PersonaController` - Endpoints para gestión de personas
- `FamiliarController` - Endpoints para gestión de familiares
- `BusquedaController` - Endpoints para búsqueda pública
- `AuthController` - Endpoints para autenticación

### DTOs de Petición
- `PersonaRegistroRequest`
- `MenorRegistroRequest`
- `FamiliarRegistroRequest`
- `BusquedaRequest`

### DTOs de Respuesta
- `PersonaResponse`
- `FamiliarResponse`
- `BusquedaResultResponse`
- `ErrorResponse`

### Manejadores de Excepciones
- `GlobalExceptionHandler`

## Principios

1. **Delgado**: Controladores con lógica mínima, delegan a casos de uso
2. **Validación**: Usar Bean Validation para DTOs
3. **Documentación**: Documentar endpoints con OpenAPI/Swagger
4. **Seguridad**: Aplicar restricciones de seguridad

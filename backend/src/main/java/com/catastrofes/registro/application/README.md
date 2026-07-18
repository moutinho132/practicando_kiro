# Paquete: application

## Responsabilidad

Contiene la **implementación de los casos de uso** definidos en los puertos de entrada. Coordina el flujo de la aplicación y orquesta las operaciones de dominio.

## Contenido

### Servicios de Aplicación
- `PersonaService` - Implementa `RegistrarPersonaUseCase`
- `FamiliarService` - Implementa `RegistrarFamiliarUseCase`
- `BusquedaService` - Implementa `BuscarPersonaUseCase`
- `UsuarioService` - Implementa `GestionarUsuarioUseCase`

## Principios

1. **Orquestación**: Coordina entidades de dominio y servicios
2. **Transaccionalidad**: Define límites de transacción
3. **Seguridad**: Aplica reglas de autorización
4. **Sin lógica de negocio**: La lógica pertenece al dominio
5. **Depende de puertos**: Usa interfaces, no implementaciones concretas

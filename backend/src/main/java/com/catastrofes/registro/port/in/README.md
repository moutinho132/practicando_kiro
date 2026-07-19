# Paquete: port.in (Puertos de Entrada)

## Responsabilidad

Define los **casos de uso** de la aplicación. Estas interfaces representan las operaciones que el sistema ofrece a sus clientes (controllers, otros sistemas).

## Contenido

### Interfaces de Caso de Uso
- `RegistrarPersonaUseCase` - Registro de nuevas personas en el sistema
- `RegistrarFamiliarUseCase` - Registro de familiares que buscan personas
- `BuscarPersonaUseCase` - Búsqueda de personas por diferentes criterios
- `ActualizarEstadoPersonaUseCase` - Actualización del estado de una persona
- `GestionarUsuarioUseCase` - Gestión de usuarios del sistema

## Principios

1. **Definidos por el dominio**: Estas interfaces pertenecen al dominio
2. **Orientados al negocio**: Nombres que reflejan operaciones de negocio
3. **Genéricos**: No deben incluir detalles de HTTP, REST, o JSON
4. **Una responsabilidad**: Cada caso de uso tiene una única responsabilidad

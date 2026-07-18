# Paquete: port.out (Puertos de Salida)

## Responsabilidad

Define las interfaces que el dominio necesita para interactuar con el mundo exterior. Estas son abstracciones de infraestructura externa.

## Contenido

### Interfaces de Repositorio
- `PersonaRepositoryPort` - Operaciones de persistencia para personas
- `FamiliarRepositoryPort` - Operaciones de persistencia para familiares
- `UsuarioRepositoryPort` - Operaciones de persistencia para usuarios

### Interfaces de Servicios Externos
- `NotificacionPort` - Envío de notificaciones (email, SMS)
- `GeolocalizacionPort` - Servicios de geolocalización

## Principios

1. **Definidos por el dominio**: Estas interfaces pertenecen al dominio
2. **Inversión de dependencias**: El dominio define la interfaz, la infraestructura la implementa
3. **Genéricos**: No deben incluir detalles de implementación (SQL, HTTP, etc.)
4. **Testeable**: Permiten crear mocks fácilmente para pruebas

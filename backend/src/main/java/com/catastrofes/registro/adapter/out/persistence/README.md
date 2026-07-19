# Paquete: adapter.out.persistence (Adaptadores de Salida - Persistencia)

## Responsabilidad

Implementa los puertos de salida para persistencia de datos. Contiene entidades JPA, repositorios, y mapeadores.

## Contenido

### Entidades JPA
- `PersonaJpaEntity`
- `FamiliarJpaEntity`
- `UsuarioJpaEntity`
- `UbicacionJpaEntity`
- `RasgosFisicosJpaEntity`

### Repositorios Spring Data JPA
- `PersonaJpaRepository`
- `FamiliarJpaRepository`
- `UsuarioJpaRepository`

### Mapeadores
- `PersonaMapper`
- `FamiliarMapper`
- `UbicacionMapper`
- `RasgosFisicosMapper`

### Adaptadores
- `PersonaRepositoryAdapter` - Implementa `PersonaRepositoryPort`
- `FamiliarRepositoryAdapter` - Implementa `FamiliarRepositoryPort`
- `UsuarioRepositoryAdapter` - Implementa `UsuarioRepositoryPort`

## Principios

1. **Aislamiento**: Entidades JPA no contaminan el dominio
2. **Mapeo explícito**: Usar mapeadores manuales (no MapStruct ni otros frameworks)
3. **Transaccionalidad**: Manejar transacciones correctamente
4. **Separación**: Cada adaptador implementa un único puerto

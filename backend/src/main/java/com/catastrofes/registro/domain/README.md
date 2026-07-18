# Paquete: domain

## Responsabilidad

Este paquete contiene el **núcleo del dominio** de la aplicación. Es el corazón del sistema y debe estar completamente aislado de preocupaciones técnicas como bases de datos, frameworks web, o APIs externas.

## Contenido

### Entidades de Dominio
- `Persona` - Entidad que representa a una persona registrada en el sistema
- `PersonaMenor` - Extensión de Persona para menores de edad
- `FamiliarRegistrado` - Persona que busca a familiares afectados
- `Ubicacion` - Valor objeto para representar ubicaciones geográficas
- `RasgosFisicos` - Valor objeto para características físicas
- `RegistroVivienda` - Información sobre vivienda de la persona
- `Auditoria` - Registro de cambios en el sistema

### Enumeraciones
- `EstadoPersona` - Estados posibles: DESAPARECIDO, ENCONTRADO, IDENTIFICADO, FALLECIDO

### Servicios de Dominio
- `ValidadorPersonaService` - Validaciones de reglas de negocio para personas
- `ValidadorFamiliarService` - Validaciones para familiares

### Excepciones de Dominio
- `PersonaYaRegistradaException`
- `PersonaNoEncontradaException`
- `ValidacionDominioException`
- `FamiliarYaRegistradoException`

## Principios

1. **Inmutabilidad**: Todas las entidades deben ser inmutables (usar `record` o clases con campos final)
2. **Sin dependencias externas**: No depender de Spring, JPA, ni frameworks
3. **Auto-contenido**: Toda la lógica de negocio debe estar aquí
4. **Testeable**: Debe poder probarse sin infraestructura

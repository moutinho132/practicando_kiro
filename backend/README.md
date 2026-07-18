# Sistema de Registro de Personas en Catástrofes - Backend

## Descripción

Este es el backend del Sistema de Registro de Personas en Catástrofes, una aplicación diseñada para facilitar el registro y búsqueda de personas afectadas por catástrofes naturales.

## Arquitectura Hexagonal

El proyecto sigue una arquitectura hexagonal (también conocida como arquitectura de puertos y adaptadores), que permite mantener el núcleo del dominio aislado de las preocupaciones técnicas.

### Estructura de Paquetes

```
com.catastrofes.registro
├── domain                    # Núcleo del dominio
│   ├── Persona              # Entidades de dominio
│   └── EstadoPersona        # Enumeraciones
├── port
│   ├── in                   # Puertos de entrada (casos de uso)
│   │   └── RegistrarPersonaUseCase
│   └── out                  # Puertos de salida (repositorios, servicios externos)
│       └── PersonaRepositoryPort
├── application              # Implementación de casos de uso
│   └── PersonaService
├── adapter
│   ├── in
│   │   └── web              # Adaptadores de entrada (controladores REST)
│   │       └── PersonaController
│   └── out
│       └── persistence      # Adaptadores de salida (persistencia)
│           ├── PersonaJpaEntity
│           ├── PersonaJpaRepository
│           └── PersonaRepositoryAdapter
└── config                   # Configuración de Spring
    └── JpaConfig
```

### Descripción de Paquetes

- **domain**: Contiene las entidades de dominio, enumeraciones, excepciones y servicios de dominio. Este paquete no debe tener dependencias de frameworks.
- **port.in**: Define los casos de uso (interfaces) que el sistema ofrece.
- **port.out**: Define las interfaces que el dominio necesita para interactuar con el mundo exterior.
- **application**: Implementa los casos de uso definidos en `port.in`.
- **adapter.in.web**: Controladores REST que exponen la API.
- **adapter.out.persistence**: Implementación de persistencia con JPA/H2.
- **config**: Configuraciones de Spring Boot.

## Tecnologías

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **H2 Database** (desarrollo y pruebas)
- **Maven**

## Requisitos Previos

- JDK 17 o superior
- Maven 3.6+

## Ejecución

### Modo Desarrollo

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

### Consola H2

En modo desarrollo, la consola H2 está disponible en: http://localhost:8080/h2-console

**Credenciales de conexión:**
- JDBC URL: `jdbc:h2:mem:catastrofes_db`
- Usuario: `sa`
- Password: (vacío)

## Perfiles de Configuración

El proyecto tiene tres perfiles configurados:

- **dev**: Perfil de desarrollo con H2 en memoria y consola habilitada
- **test**: Perfil de pruebas con configuración optimizada para tests
- **prod**: Perfil de producción (configuración mediante variables de entorno)

Para cambiar el perfil:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Endpoints Disponibles

Por el momento, el proyecto está en construcción. Los endpoints se documentarán en fases posteriores.

## Pruebas

```bash
mvn test
```

## Construcción

```bash
mvn clean package
```

## Estado del Proyecto

✅ Tarea 1.1: Proyecto Spring Boot inicializado
✅ Tarea 1.2: Estructura de paquetes hexagonal configurada
✅ Tarea 1.3: Base de datos H2 configurada

## Próximos Pasos

Ver [tasks.md](.kiro/specs/sistema-registro-catastrofes/tasks.md) para el plan completo de implementación.

## Licencia

Este proyecto es de uso interno para el Sistema de Registro de Personas en Catástrofes.

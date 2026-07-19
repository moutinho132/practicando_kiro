# Design Document - Sistema de Registro de Personas en Catástrofes

## 1. Visión General de la Arquitectura

### 1.1 Diagrama de Arquitectura General

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           FRONTEND (Micro Frontends)                         │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────────────────┐  │
│  │ MFE Captura     │  │ MFE Consulta    │  │ Shell Angular (Container)   │  │
│  │ (Rescatistas)   │  │ (Público)       │  │ Module Federation           │  │
│  │ Angular + BS    │  │ Angular + BS    │  │ Routing Global              │  │
│  └────────┬────────┘  └────────┬────────┘  └─────────────────────────────┘  │
└───────────┼────────────────────┼─────────────────────────────────────────────┘
            │                    │
            │ REST API           │ REST API
            ▼                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         BACKEND (Monolito Hexagonal)                         │
├─────────────────────────────────────────────────────────────────────────────┤
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │                    ADAPTERS (Capa de Infraestructura)                 │  │
│  │  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────────┐   │  │
│  │  │ REST Controllers│  │ JPA Repositories│  │ Security Config     │   │  │
│  │  │ (DTOs Records)  │  │ (H2 Database)   │  │ (Spring Security)   │   │  │
│  │  └────────┬────────┘  └────────┬────────┘  └─────────────────────┘   │  │
│  └───────────┼────────────────────┼───────────────────────────────────────┘  │
│              │                    │                                            │
│  ┌───────────┼────────────────────┼───────────────────────────────────────┐  │
│  │           │        PORTS (Interfaces)                                   │  │
│  │  ┌────────▼────────┐  ┌────────▼────────┐                              │  │
│  │  │ Inbound Ports   │  │ Outbound Ports  │                              │  │
│  │  │ (Use Cases)     │  │ (Repositories)  │                              │  │
│  │  └─────────────────┘  └─────────────────┘                              │  │
│  └───────────┼────────────────────┼───────────────────────────────────────┘  │
│              │                    │                                            │
│  ┌───────────┼────────────────────┼───────────────────────────────────────┐  │
│  │           │        DOMAIN (Núcleo)                                      │  │
│  │  ┌────────▼────────────────────────────┐                               │  │
│  │  │ Entities: Persona, Familiar,        │                               │  │
│  │  │ Ubicacion, EstadoPersona, etc.      │                               │  │
│  │  │ Services: Lógica de negocio pura    │                               │  │
│  │  └─────────────────────────────────────┘                               │  │
│  └─────────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 1.2 Estructura de Paquetes Backend

```
com.catastrofes.registro/
├── domain/                          # Capa de Dominio (Sin dependencias de framework)
│   ├── model/                       # Entidades de dominio puras
│   │   ├── Persona.java
│   │   ├── PersonaMenor.java
│   │   ├── FamiliarRegistrado.java
│   │   ├── Ubicacion.java
│   │   ├── EstadoPersona.java
│   │   ├── RasgosFisicos.java
│   │   ├── RegistroVivienda.java
│   │   └── Auditoria.java
│   ├── exception/                   # Excepciones de dominio
│   │   ├── PersonaYaRegistradaException.java
│   │   ├── PersonaNoEncontradaException.java
│   │   └── ValidacionDominioException.java
│   └── service/                     # Servicios de dominio
│       └── ValidadorPersonaService.java
│
├── port/                            # Capa de Puertos (Interfaces)
│   ├── in/                          # Puertos de entrada (Use Cases)
│   │   ├── RegistrarPersonaUseCase.java
│   │   ├── RegistrarFamiliarUseCase.java
│   │   ├── BuscarPersonaUseCase.java
│   │   ├── ActualizarEstadoPersonaUseCase.java
│   │   └── GestionarUsuarioUseCase.java
│   └── out/                         # Puertos de salida (Repositories)
│       ├── PersonaRepositoryPort.java
│       ├── FamiliarRepositoryPort.java
│       ├── UsuarioRepositoryPort.java
│       └── NotificacionPort.java
│
├── adapter/                         # Capa de Adaptadores (Implementaciones)
│   ├── in/                          # Adaptadores de entrada
│   │   ├── rest/
│   │   │   ├── PersonaController.java
│   │   │   ├── FamiliarController.java
│   │   │   ├── BusquedaController.java
│   │   │   └── AuthController.java
│   │   └── dto/                     # DTOs (Java Records)
│   │       ├── request/
│   │       │   ├── PersonaRegistroRequest.java
│   │       │   ├── FamiliarRegistroRequest.java
│   │       │   ├── BusquedaRequest.java
│   │       │   └── EstadoUpdateRequest.java
│   │       └── response/
│   │           ├── PersonaResponse.java
│   │           ├── FamiliarResponse.java
│   │           ├── BusquedaResultResponse.java
│   │           └── ErrorResponse.java
│   │
│   ├── out/                         # Adaptadores de salida
│   │   ├── persistence/
│   │   │   ├── entity/              # Entidades JPA
│   │   │   │   ├── PersonaJpaEntity.java
│   │   │   │   ├── FamiliarJpaEntity.java
│   │   │   │   ├── UsuarioJpaEntity.java
│   │   │   │   └── AuditoriaJpaEntity.java
│   │   │   ├── repository/          # Spring Data JPA Repositories
│   │   │   │   ├── PersonaJpaRepository.java
│   │   │   │   ├── FamiliarJpaRepository.java
│   │   │   │   └── UsuarioJpaRepository.java
│   │   │   ├── mapper/              # Mapeadores manuales
│   │   │   │   ├── PersonaMapper.java
│   │   │   │   ├── FamiliarMapper.java
│   │   │   │   └── UbicacionMapper.java
│   │   │   └── adapter/             # Implementaciones de puertos
│   │   │       ├── PersonaRepositoryAdapter.java
│   │   │       └── FamiliarRepositoryAdapter.java
│   │   └── notification/
│   │       ├── EmailNotificationAdapter.java
│   │       └── SmsNotificationAdapter.java
│   │
│   └── security/
│       ├── SecurityConfig.java
│       ├── JwtTokenProvider.java
│       ├── UserDetailsServiceImpl.java
│       └── Role.java
│
├── application/                     # Casos de uso (Implementaciones)
│   ├── PersonaService.java
│   ├── FamiliarService.java
│   ├── BusquedaService.java
│   └── UsuarioService.java
│
└── config/
    ├── H2Config.java
    └── OpenApiConfig.java
```

---

## 2. Modelo de Datos

### 2.1 Diagrama Entidad-Relación

```
┌─────────────────────────┐       ┌─────────────────────────┐
│      PERSONA            │       │   FAMILIAR_REGISTRADO   │
├─────────────────────────┤       ├─────────────────────────┤
│ id (PK)                 │       │ id (PK)                 │
│ cedula                  │       │ cedula                  │
│ nombres                 │       │ nombres                 │
│ edad                    │       │ numero_contacto         │
│ fecha_nacimiento        │◄──────│ relacion_persona_id(FK) │
│ es_menor                │       │ adulto_responsable      │
│ estado                  │       │ rasgos_fisicos_id(FK)   │
│ fecha_registro          │       │ fecha_registro          │
│ evaluacion_trauma       │       │ activo                  │
│ identificador_gps       │       └─────────────────────────┘
│                         │
│ ubicacion_id (FK)       │       ┌─────────────────────────┐
│ rasgos_fisicos_id (FK)  │       │      RASGOS_FISICOS     │
│ vivienda_id (FK)        │       ├─────────────────────────┤
│ foto_url                │       │ id (PK)                 │
│ activo                  │       │ color_piel              │
│ eliminado_logico        │       │ color_cabello           │
│ fecha_eliminacion       │       │ estatura_aprox          │
│ usuario_registro_id(FK) │       │ peso_aprox              │
└─────────────────────────┘       │ senas_particulares      │
         │                        │ descripcion_general     │
         │                        └─────────────────────────┘
         │
         ▼                        ┌─────────────────────────┐
┌─────────────────────────┐       │      UBICACION          │
│   HISTORIAL_ESTADO      │       ├─────────────────────────┤
├─────────────────────────┤       │ id (PK)                 │
│ id (PK)                 │       │ latitud                 │
│ persona_id (FK)         │       │ longitud                │
│ estado_anterior         │       │ direccion_texto         │
│ estado_nuevo            │       │ region                  │
│ fecha_cambio            │       │ ciudad                  │
│ usuario_cambio_id (FK)  │       │ fuente_info             │
│ ubicacion_hallazgo_id   │       │ es_gps_rescatista       │
│ confirmado_admin        │       └─────────────────────────┘
└─────────────────────────┘
                                  ┌─────────────────────────┐
┌─────────────────────────┐       │   REGISTRO_VIVIENDA     │
│      USUARIO            │       ├─────────────────────────┤
├─────────────────────────┤       │ id (PK)                 │
│ id (PK)                 │       │ direccion_completa      │
│ username                │       │ referencia              │
│ password_hash           │       │ coordenadas_lat         │
│ rol                     │       │ coordenadas_lon         │
│ email                   │       │ tipo_vivienda           │
│ activo                  │       └─────────────────────────┘
│ fecha_creacion          │
└─────────────────────────┘       ┌─────────────────────────┐
                                  │      AUDITORIA          │
┌─────────────────────────┐       ├─────────────────────────┤
│      NOTIFICACION       │       │ id (PK)                 │
├─────────────────────────┤       │ tabla_afectada          │
│ id (PK)                 │       │ registro_id             │
│ familiar_id (FK)        │       │ operacion               │
│ persona_id (FK)         │       │ usuario_operacion       │
│ tipo_notificacion       │       │ fecha_operacion         │
│ mensaje                 │       │ datos_anteriores        │
│ fecha_envio             │       │ datos_nuevos            │
│ estado_envio            │       └─────────────────────────┘
│ canal                    │
│ reintentos              │
└─────────────────────────┘
```

### 2.2 Definición de Entidades JPA

#### PersonaJpaEntity

```java
@Entity
@Table(name = "personas")
@Data
public class PersonaJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = true) // Null para menores
    private String cedula;
    
    @Column(nullable = false)
    private String nombres;
    
    @Column(nullable = false)
    private Integer edad;
    
    private LocalDate fechaNacimiento;
    
    @Column(nullable = false)
    private Boolean esMenor = false;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPersona estado = EstadoPersona.DESAPARECIDO;
    
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
    
    private String evaluacionTrauma;
    private String identificadorGps;
    private String fotoUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ubicacion_id")
    private UbicacionJpaEntity ubicacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rasgos_fisicos_id")
    private RasgosFisicosJpaEntity rasgosFisicos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vivienda_id")
    private RegistroViviendaJpaEntity vivienda;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_registro_id")
    private UsuarioJpaEntity usuarioRegistro;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    private Boolean eliminadoLogico = false;
    private LocalDateTime fechaEliminacion;
}
```

#### EstadoPersona (Enum)

```java
public enum EstadoPersona {
    DESAPARECIDO,
    ENCONTRADO_VIVO,
    ENCONTRADO_FALLECIDO,
    IDENTIFICADO
}
```

---

## 3. API REST Endpoints

### 3.1 Endpoints de Personas

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| POST | `/api/v1/personas` | Registrar persona desaparecida | RESCATISTA |
| GET | `/api/v1/personas/{id}` | Obtener persona por ID | RESCATISTA, ADMIN |
| PUT | `/api/v1/personas/{id}/estado` | Actualizar estado de persona | RESCATISTA |
| GET | `/api/v1/personas/buscar` | Búsqueda avanzada de personas | RESCATISTA |
| DELETE | `/api/v1/personas/{id}` | Eliminación lógica | ADMIN |
| PUT | `/api/v1/personas/{id}/restaurar` | Restaurar persona eliminada | ADMIN |

### 3.2 Endpoints de Familiares

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| POST | `/api/v1/familiares` | Registrar familiar buscador | PUBLICO |
| GET | `/api/v1/familiares/{id}` | Obtener familiar por ID | RESCATISTA, ADMIN |
| PUT | `/api/v1/familiares/{id}` | Actualizar datos de familiar | FAMILIAR, ADMIN |

### 3.3 Endpoints de Búsqueda Pública

| Method | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| GET | `/api/v1/busqueda` | Búsqueda pública de personas | PUBLICO |
| GET | `/api/v1/busqueda/{id}` | Ver detalle de persona | PUBLICO |

### 3.4 Endpoints de Fotos

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| POST | `/api/v1/fotos` | Subir foto de persona | PUBLICO |
| GET | `/api/v1/fotos/{id}` | Obtener foto | PUBLICO |

### 3.5 Endpoints de Autenticación

| Método | Endpoint | Descripción | Rol |
|--------|----------|-------------|-----|
| POST | `/api/v1/auth/login` | Iniciar sesión | PUBLICO |
| POST | `/api/v1/auth/register` | Registrar usuario | ADMIN |
| POST | `/api/v1/auth/refresh` | Renovar token | AUTENTICADO |

---

## 4. DTOs (Java Records)

### 4.1 Request DTOs

```java
// Registro de persona adulta
public record PersonaRegistroRequest(
    @NotBlank String cedula,
    @NotBlank String nombres,
    @NotNull Integer edad,
    String fechaNacimiento,
    @NotNull UbicacionRequest ubicacion,
    RasgosFisicosRequest rasgosFisicos,
    ViviendaRequest vivienda
) {}

// Registro de menor de edad
public record MenorRegistroRequest(
    @NotBlank String nombres,
    @NotNull Integer edadAproximada,
    String evaluacionTrauma,
    @NotNull Long familiarId
) {}

// Registro de familiar
public record FamiliarRegistroRequest(
    @NotBlank String cedula,
    @NotBlank String nombres,
    @NotBlank String numeroContacto,
    @NotBlank String relacionPersona,
    Boolean esMenorEdad,
    String adultoResponsable,
    RasgosFisicosRequest rasgosFisicos
) {}

// Búsqueda
public record BusquedaRequest(
    String nombres,
    String cedula,
    String ubicacion,
    Integer edadMinima,
    Integer edadMaxima,
    String rasgosFisicos,
    EstadoPersona estado,
    Integer pagina,
    Integer tamanioPagina
) {}

// Actualización de estado
public record EstadoUpdateRequest(
    @NotNull EstadoPersona nuevoEstado,
    UbicacionRequest ubicacionHallazgo
) {}

// Ubicación
public record UbicacionRequest(
    Double latitud,
    Double longitud,
    String direccionTexto,
    String region,
    String ciudad,
    String fuenteInfo,
    Boolean esGpsRescatista
) {}
```

### 4.2 Response DTOs

```java
// Respuesta de persona
public record PersonaResponse(
    Long id,
    String cedula,
    String nombres,
    Integer edad,
    EstadoPersona estado,
    LocalDateTime fechaRegistro,
    String fotoUrl,
    UbicacionResponse ubicacion,
    RasgosFisicosResponse rasgosFisicos,
    Boolean esMenor
) {}

// Respuesta de búsqueda paginada
public record BusquedaResultResponse(
    List<PersonaResponse> resultados,
    Integer totalElementos,
    Integer paginaActual,
    Integer totalPaginas
) {}

// Respuesta de error
public record ErrorResponse(
    String codigo,
    String mensaje,
    List<String> detalles,
    LocalDateTime timestamp
) {}
```

---

## 5. Mapeadores Manuales

### 5.1 PersonaMapper

```java
@Component
public class PersonaMapper {
    
    public Persona toDomain(PersonaJpaEntity entity) {
        return new Persona(
            entity.getId(),
            entity.getCedula(),
            entity.getNombres(),
            entity.getEdad(),
            entity.getFechaNacimiento(),
            entity.getEsMenor(),
            entity.getEstado(),
            entity.getFechaRegistro(),
            toUbicacionDomain(entity.getUbicacion()),
            toRasgosDomain(entity.getRasgosFisicos()),
            entity.getFotoUrl(),
            entity.getActivo()
        );
    }
    
    public PersonaJpaEntity toEntity(Persona domain) {
        PersonaJpaEntity entity = new PersonaJpaEntity();
        entity.setCedula(domain.cedula());
        entity.setNombres(domain.nombres());
        entity.setEdad(domain.edad());
        entity.setEstado(domain.estado());
        entity.setFechaRegistro(domain.fechaRegistro());
        entity.setEsMenor(domain.esMenor());
        // ... mapeo completo
        return entity;
    }
    
    public PersonaResponse toResponse(Persona domain) {
        return new PersonaResponse(
            domain.id(),
            domain.cedula(),
            domain.nombres(),
            domain.edad(),
            domain.estado(),
            domain.fechaRegistro(),
            domain.fotoUrl(),
            toUbicacionResponse(domain.ubicacion()),
            toRasgosResponse(domain.rasgosFisicos()),
            domain.esMenor()
        );
    }
}
```

---

## 6. Seguridad

### 6.1 Roles y Permisos

| Rol | Descripción | Permisos |
|-----|-------------|----------|
| `RESCATISTA` | Personal de emergencia en campo | Crear, editar, consultar personas; búsquedas avanzadas |
| `FAMILIAR` | Familiar afectado | Consultar registros asociados; actualizar sus datos |
| `ADMIN` | Administrador del sistema | Gestión completa; eliminación lógica; gestión de usuarios |
| `PUBLICO` | Usuario no autenticado | Búsqueda pública; registro de familiares; subir fotos |

### 6.2 Configuración de Security

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/busqueda/**").permitAll()
                .requestMatchers("/api/v1/familiares").permitAll()
                .requestMatchers("/api/v1/fotos").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/v1/personas/**").hasAnyRole("RESCATISTA", "ADMIN")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthenticationFilter(
                jwtTokenProvider, userDetailsService), 
                UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

## 7. Base de Datos H2

### 7.1 Configuración

```yaml
# application.yml
spring:
  datasource:
    url: jdbc:h2:mem:catastrofes_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password: 
    
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.H2Dialect
        
  h2:
    console:
      enabled: true
      path: /h2-console
```

### 7.2 Scripts de Inicialización

```sql
-- data.sql (opcional para datos iniciales)
INSERT INTO usuarios (username, password_hash, rol, email, activo, fecha_creacion)
VALUES ('admin', '$2a$10$...', 'ADMIN', 'admin@catastrofes.org', true, NOW());

INSERT INTO usuarios (username, password_hash, rol, email, activo, fecha_creacion)
VALUES ('rescatista1', '$2a$10$...', 'RESCATISTA', 'rescatista@catastrofes.org', true, NOW());
```

---

## 8. Frontend - Micro Frontends

### 8.1 Arquitectura MFE con Module Federation

```
┌─────────────────────────────────────────────────────────────┐
│                    SHELL (Container App)                    │
│  ┌───────────────────────────────────────────────────────┐  │
│  │  AppModule                                            │  │
│  │  ├── AppRoutingModule (Routing Global)                │  │
│  │  ├── ShellComponent (Layout Principal)                │  │
│  │  └── AuthService (Autenticación Global)               │  │
│  └───────────────────────────────────────────────────────┘  │
│                                                              │
│  ┌─────────────────────┐    ┌──────────────────────────┐   │
│  │  MFE Captura        │    │  MFE Consulta            │   │
│  │  (Remote)           │    │  (Remote)                │   │
│  │  - Formularios      │    │  - Búsqueda pública      │   │
│  │  - GPS              │    │  - Resultados            │   │
│  │  - Offline Cache    │    │  - Detalle persona       │   │
│  └─────────────────────┘    └──────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

### 8.2 Configuración webpack.config.js (Shell)

```javascript
// shell/webpack.config.js
const ModuleFederationPlugin = require('@angular-architects/module-federation/webpack');

module.exports = {
  plugins: [
    new ModuleFederationPlugin({
      name: 'shell',
      remotes: {
        mfeCaptura: 'http://localhost:4201/remoteEntry.js',
        mfeConsulta: 'http://localhost:4202/remoteEntry.js'
      },
      shared: {
        '@angular/core': { singleton: true, strictVersion: true },
        '@angular/common': { singleton: true, strictVersion: true },
        '@angular/router': { singleton: true, strictVersion: true }
      }
    })
  ]
};
```

### 8.3 Estructura Frontend

```
frontend/
├── shell/                          # Aplicación contenedora
│   ├── src/
│   │   ├── app/
│   │   │   ├── app.component.ts
│   │   │   ├── app-routing.module.ts
│   │   │   └── app.module.ts
│   │   └── assets/
│   └── webpack.config.js
│
├── mfe-captura/                    # Micro frontend de captura
│   ├── src/
│   │   ├── app/
│   │   │   ├── captura/
│   │   │   │   ├── persona-form/
│   │   │   │   ├── menor-form/
│   │   │   │   └── gps-capture/
│   │   │   ├── services/
│   │   │   │   ├── offline-cache.service.ts
│   │   │   │   └── persona-api.service.ts
│   │   │   └── captura.module.ts
│   │   └── assets/
│   └── webpack.config.js
│
└── mfe-consulta/                   # Micro frontend de consulta
    ├── src/
    │   ├── app/
    │   │   ├── consulta/
    │   │   │   ├── busqueda/
    │   │   │   ├── resultados/
    │   │   │   └── detalle-persona/
    │   │   ├── services/
    │   │   │   └── busqueda-api.service.ts
    │   │   └── consulta.module.ts
    │   └── assets/
    └── webpack.config.js
```

---

## 9. Flujo de Datos

### 9.1 Flujo de Registro de Persona

```
┌──────────┐     ┌─────────────┐     ┌────────────┐     ┌────────────┐
│ Rescatista│     │ MFE Captura │     │ Controller │     │ UseCase    │
└─────┬────┘     └──────┬──────┘     └─────┬──────┘     └─────┬──────┘
      │                 │                   │                  │
      │ 1. Llena form   │                   │                  │
      │────────────────>│                   │                  │
      │                 │                   │                  │
      │                 │ 2. POST /personas │                  │
      │                 │ PersonaRegistroReq│                  │
      │                 │──────────────────>│                  │
      │                 │                   │                  │
      │                 │                   │ 3. Validar DTO   │
      │                 │                   │─────────────────>│
      │                 │                   │                  │
      │                 │                   │                  │ 4. Crear Persona (Domain)
      │                 │                   │                  │────────────┐
      │                 │                   │                  │            │
      │                 │                   │                  │<───────────┘
      │                 │                   │                  │
      │                 │                   │                  │ 5. Save via Port
      │                 │                   │                  │────────────┐
      │                 │                   │                  │            │
      │                 │                   │                  │            ▼
      │                 │                   │                  │     ┌──────────────┐
      │                 │                   │                  │     │ Repository   │
      │                 │                   │                  │     │ Adapter      │
      │                 │                   │                  │     └──────┬───────┘
      │                 │                   │                  │            │
      │                 │                   │                  │            │ 6. Map & Save
      │                 │                   │                  │            │    to H2
      │                 │                   │                  │            │
      │                 │                   │ 7. PersonaResponse│            │
      │                 │                   │<──────────────────│────────────┘
      │                 │                   │                  │
      │                 │ 8. JSON Response  │                  │
      │                 │<──────────────────│                  │
      │                 │                   │                  │
      │ 9. Confirmación │                   │                  │
      │<────────────────│                   │                  │
      │                 │                   │                  │
```

---

## 10. Consideraciones Técnicas

### 10.1 Principios SOLID Aplicados

| Principio | Aplicación |
|-----------|------------|
| **S** - Single Responsibility | Cada clase tiene una única responsabilidad: Controllers manejan HTTP, Services lógica de negocio, Mappers conversión |
| **O** - Open/Closed | Nuevos adaptadores pueden agregarse sin modificar el dominio |
| **L** - Liskov Substitution | Cualquier implementación de un Puerto puede sustituirse |
| **I** - Interface Segregation | Interfaces de puertos específicas y cohesivas |
| **D** - Dependency Inversion | Dominio depende de abstracciones (Ports), no de implementaciones |

### 10.2 Patrones de Diseño Utilizados

- **Repository Pattern**: Abstracción del acceso a datos
- **Adapter Pattern**: Adaptadores de entrada y salida
- **DTO Pattern**: Transferencia de datos entre capas
- **Factory Pattern**: Creación de entidades de dominio
- **Observer Pattern**: Notificaciones a familiares

### 10.3 Manejo de Excepciones

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PersonaYaRegistradaException.class)
    public ResponseEntity<ErrorResponse> handlePersonaYaRegistrada(PersonaYaRegistradaException ex) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponse("PERSONA_YA_REGISTRADA", ex.getMessage(), null, LocalDateTime.now()));
    }
    
    @ExceptionHandler(ValidacionDominioException.class)
    public ResponseEntity<ErrorResponse> handleValidacionDominio(ValidacionDominioException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("VALIDACION_ERROR", ex.getMessage(), ex.getDetalles(), LocalDateTime.now()));
    }
}
```

---

## 11. Dependencias Maven

```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- H2 Database -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- JWT -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    
    <!-- OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.2.0</version>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 12. Próximos Pasos de Implementación

Ver archivo `tasks.md` para el desglose detallado de tareas por fase.

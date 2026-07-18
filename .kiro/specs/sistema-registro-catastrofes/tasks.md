# Tasks Document - Sistema de Registro de Personas en Catástrofes

## Overview

Este documento desglosa el trabajo de implementación en tareas específicas, organizadas por fases según el plan de trabajo de 10 semanas.

---

## Fase 1: Arquitectura Base e Infraestructura Inicial (Semanas 1-2)

### Backend

#### Tarea 1.1: Inicializar proyecto Spring Boot
- [x] Crear proyecto Spring Boot con Java 17
- [x] Configurar Maven con dependencias necesarias
- [x] Configurar application.yml con perfiles (dev, test, prod)
- [x] Verificar que la aplicación inicia correctamente

#### Tarea 1.2: Configurar estructura de paquetes hexagonal
- [x] Crear estructura de paquetes: domain, port, adapter, application, config
- [x] Documentar la responsabilidad de cada paquete en README interno
- [x] Crear paquetes para submódulos (in/out dentro de port y adapter)

#### Tarea 1.3: Configurar base de datos H2
- [x] Agregar dependencia H2 en pom.xml
- [x] Configurar datasource H2 en memoria en application.yml
- [x] Habilitar consola H2 en modo desarrollo
- [x] Crear archivo data.sql con usuario admin inicial
- [x] Verificar conexión desde consola H2

#### Tarea 1.4: Configurar Spring Security básico
- [x] Agregar dependencia spring-boot-starter-security
- [x] Crear clase SecurityConfig con configuración inicial
- [x] Deshabilitar CSRF para API REST
- [ ] Configurar sesiones stateless
- [ ] Crear enum Role con roles: RESCATISTA, FAMILIAR, ADMIN
- [ ] Configurar permisos básicos por endpoint

### Frontend

#### Tarea 1.5: Inicializar proyecto Angular Shell
- [ ] Crear proyecto Angular con Angular CLI
- [ ] Configurar TypeScript strict mode
- [ ] Configurar routing básico en AppModule
- [ ] Crear ShellComponent como layout principal

#### Tarea 1.6: Configurar Module Federation
- [ ] Instalar @angular-architects/module-federation
- [ ] Configurar webpack.config.js para Shell
- [ ] Crear estructura de proyectos MFE (mfe-captura, mfe-consulta)
- [ ] Configurar remotes en Shell
- [ ] Verificar carga lazy de módulos remotos

#### Tarea 1.7: Integrar Bootstrap
- [ ] Instalar Bootstrap via npm
- [ ] Configurar estilos globales en angular.json
- [ ] Crear sistema de diseño con paleta accesible
- [ ] Crear componentes base: Navbar, Footer, Loading

---

## Fase 2: Definición de Modelos Inmutables y Dominio (Semanas 3-4)

### Backend - Dominio

#### Tarea 2.1: Crear entidades de dominio
- [ ] Crear clase Persona (record o clase inmutable)
- [ ] Crear clase PersonaMenor extends Persona
- [ ] Crear clase FamiliarRegistrado
- [ ] Crear clase Ubicacion
- [ ] Crear clase RasgosFisicos
- [ ] Crear clase RegistroVivienda
- [ ] Crear clase Auditoria
- [ ] Crear enum EstadoPersona

#### Tarea 2.2: Crear excepciones de dominio
- [ ] Crear PersonaYaRegistradaException
- [ ] Crear PersonaNoEncontradaException
- [ ] Crear ValidacionDominioException
- [ ] Crear FamiliarYaRegistradoException

#### Tarea 2.3: Crear servicios de dominio
- [ ] Crear ValidadorPersonaService con validaciones de negocio
- [ ] Crear ValidadorFamiliarService
- [ ] Implementar validación de cédula duplicada
- [ ] Implementar validación de edad para menores

### Backend - Puertos

#### Tarea 2.4: Definir puertos de entrada (Inbound)
- [ ] Crear interfaz RegistrarPersonaUseCase
- [ ] Crear interfaz RegistrarFamiliarUseCase
- [ ] Crear interfaz BuscarPersonaUseCase
- [ ] Crear interfaz ActualizarEstadoPersonaUseCase
- [ ] Crear interfaz GestionarUsuarioUseCase

#### Tarea 2.5: Definir puertos de salida (Outbound)
- [ ] Crear interfaz PersonaRepositoryPort
- [ ] Crear interfaz FamiliarRepositoryPort
- [ ] Crear interfaz UsuarioRepositoryPort
- [ ] Crear interfaz NotificacionPort

### Backend - DTOs

#### Tarea 2.6: Crear DTOs de petición (Java Records)
- [ ] Crear PersonaRegistroRequest record
- [ ] Crear MenorRegistroRequest record
- [ ] Crear FamiliarRegistroRequest record
- [ ] Crear BusquedaRequest record
- [ ] Crear EstadoUpdateRequest record
- [ ] Crear UbicacionRequest record
- [ ] Crear RasgosFisicosRequest record
- [ ] Agregar validaciones Bean Validation (@NotBlank, @NotNull, etc.)

#### Tarea 2.7: Crear DTOs de respuesta (Java Records)
- [ ] Crear PersonaResponse record
- [ ] Crear FamiliarResponse record
- [ ] Crear BusquedaResultResponse record
- [ ] Crear ErrorResponse record
- [ ] Crear UbicacionResponse record
- [ ] Crear RasgosFisicosResponse record

### Frontend - MFE Captura

#### Tarea 2.8: Crear MFE Captura - Estructura base
- [ ] Crear módulo CapturaModule
- [ ] Configurar routing interno del MFE
- [ ] Crear servicio PersonaApiService para llamadas HTTP
- [ ] Crear modelos TypeScript para DTOs

#### Tarea 2.9: Crear formulario de registro de persona adulta
- [ ] Crear componente PersonaFormComponent
- [ ] Implementar formulario reactivo con validaciones
- [ ] Implementar campo de captura GPS
- [ ] Implementar campo de ubicación manual
- [ ] Crear servicio GpsService para geolocalización
- [ ] Optimizar para dispositivos móviles

#### Tarea 2.10: Crear formulario de registro de menor
- [ ] Crear componente MenorFormComponent
- [ ] Implementar formulario simplificado para menores
- [ ] Campo de evaluación de trauma
- [ ] Captura automática de GPS

---

## Fase 3: Capa de Persistencia H2 y Spring Data JPA (Semanas 5-6)

### Backend - Entidades JPA

#### Tarea 3.1: Crear entidades JPA
- [ ] Crear PersonaJpaEntity con todas las anotaciones
- [ ] Crear FamiliarJpaEntity
- [ ] Crear UsuarioJpaEntity
- [ ] Crear UbicacionJpaEntity
- [ ] Crear RasgosFisicosJpaEntity
- [ ] Crear RegistroViviendaJpaEntity
- [ ] Crear HistorialEstadoJpaEntity
- [ ] Crear AuditoriaJpaEntity
- [ ] Crear NotificacionJpaEntity

#### Tarea 3.2: Configurar relaciones JPA
- [ ] Configurar relación Persona -> Ubicacion (ManyToOne)
- [ ] Configurar relación Persona -> RasgosFisicos (ManyToOne)
- [ ] Configurar relación Persona -> Vivienda (ManyToOne)
- [ ] Configurar relación Familiar -> Persona (ManyToOne)
- [ ] Configurar relación HistorialEstado -> Persona (ManyToOne)
- [ ] Configurar relación Notificacion -> Familiar (ManyToOne)

### Backend - Repositorios

#### Tarea 3.3: Crear repositorios Spring Data JPA
- [ ] Crear PersonaJpaRepository con métodos de búsqueda
- [ ] Crear FamiliarJpaRepository
- [ ] Crear UsuarioJpaRepository
- [ ] Crear UbicacionJpaRepository
- [ ] Crear AuditoriaJpaRepository
- [ ] Crear métodos de búsqueda personalizados (findByCedula, findByEstado, etc.)

### Backend - Mapeadores

#### Tarea 3.4: Crear mapeadores manuales
- [ ] Crear PersonaMapper con métodos toDomain, toEntity, toResponse
- [ ] Crear FamiliarMapper
- [ ] Crear UbicacionMapper
- [ ] Crear RasgosFisicosMapper
- [ ] Crear ViviendaMapper
- [ ] Crear UsuarioMapper

### Backend - Adaptadores de Persistencia

#### Tarea 3.5: Implementar adaptadores de repositorio
- [ ] Crear PersonaRepositoryAdapter implementando PersonaRepositoryPort
- [ ] Crear FamiliarRepositoryAdapter implementando FamiliarRepositoryPort
- [ ] Crear UsuarioRepositoryAdapter implementando UsuarioRepositoryPort
- [ ] Inyectar repositorios JPA y mapeadores
- [ ] Implementar métodos CRUD con mapeo

### Backend - Servicios de Aplicación

#### Tarea 3.6: Implementar casos de uso
- [ ] Crear PersonaService implementando RegistrarPersonaUseCase
- [ ] Crear FamiliarService implementando RegistrarFamiliarUseCase
- [ ] Crear BusquedaService implementando BuscarPersonaUseCase
- [ ] Crear UsuarioService implementando GestionarUsuarioUseCase
- [ ] Agregar lógica de negocio en cada servicio

### Backend - Controladores

#### Tarea 3.7: Crear controladores REST
- [ ] Crear PersonaController con endpoints CRUD
- [ ] Crear FamiliarController
- [ ] Crear BusquedaController para búsqueda pública
- [ ] Crear AuthController para autenticación
- [ ] Configurar validación de DTOs
- [ ] Crear GlobalExceptionHandler para manejo de errores

### Backend - JWT y Seguridad

#### Tarea 3.8: Implementar autenticación JWT
- [ ] Crear JwtTokenProvider para generación y validación de tokens
- [ ] Crear JwtAuthenticationFilter
- [ ] Implementar UserDetailsServiceImpl
- [ ] Configurar SecurityFilterChain con JWT
- [ ] Crear endpoint de login

### Frontend - Integración

#### Tarea 3.9: Conectar MFE Captura con Backend
- [ ] Implementar HttpClientModule en MFE
- [ ] Crear interceptors para JWT
- [ ] Implementar llamada POST /api/v1/personas
- [ ] Manejar respuestas y errores
- [ ] Mostrar confirmación de registro exitoso

---

## Fase 4: Búsqueda, Filtros y Micro Frontend Público (Semanas 7-8)

### Backend - Búsqueda Avanzada

#### Tarea 4.1: Implementar búsqueda con filtros
- [ ] Crear especificaciones JPA para búsqueda dinámica
- [ ] Implementar búsqueda por nombres (LIKE)
- [ ] Implementar búsqueda por cédula
- [ ] Implementar búsqueda por ubicación
- [ ] Implementar búsqueda por rango de edad
- [ ] Implementar búsqueda por rasgos físicos
- [ ] Implementar búsqueda por estado
- [ ] Implementar paginación con Pageable

#### Tarea 4.2: Implementar búsqueda avanzada para rescatistas
- [ ] Crear endpoint de búsqueda avanzada /api/v1/personas/buscar
- [ ] Implementar filtro por fecha de registro
- [ ] Implementar filtro por radio de distancia
- [ ] Calcular distancia desde ubicación del rescatista
- [ ] Optimizar consultas para respuesta < 3 segundos

#### Tarea 4.3: Implementar historial de estados
- [ ] Crear endpoint PUT /api/v1/personas/{id}/estado
- [ ] Registrar cambio en HistorialEstadoJpaEntity
- [ ] Implementar validación de confirmación para IDENTIFICADO
- [ ] Implementar notificación a familiares asociados

### Backend - Notificaciones

#### Tarea 4.4: Implementar sistema de notificaciones
- [ ] Crear NotificacionPort con métodos sendEmail, sendSms
- [ ] Crear EmailNotificationAdapter (stub o implementación real)
- [ ] Crear SmsNotificationAdapter (stub o implementación real)
- [ ] Implementar lógica de reintentos (máximo 3)
- [ ] Registrar estado de envío en NotificacionJpaEntity

### Frontend - MFE Consulta

#### Tarea 4.5: Crear MFE Consulta - Estructura base
- [ ] Crear módulo ConsultaModule
- [ ] Configurar routing interno
- [ ] Crear BusquedaApiService
- [ ] Crear modelos TypeScript

#### Tarea 4.6: Crear componente de búsqueda pública
- [ ] Crear BusquedaComponent con formulario simple
- [ ] Implementar búsqueda por nombre
- [ ] Implementar búsqueda por cédula
- [ ] Implementar búsqueda por ubicación

#### Tarea 4.7: Crear componente de resultados
- [ ] Crear ResultadosComponent
- [ ] Mostrar resultados en formato tarjeta
- [ ] Implementar paginación
- [ ] Implementar carga lazy de imágenes

#### Tarea 4.8: Crear componente de detalle de persona
- [ ] Crear DetallePersonaComponent
- [ ] Mostrar información completa
- [ ] Mostrar foto si existe
- [ ] Mostrar estado actual

### Frontend - Integración Shell

#### Tarea 4.9: Integrar MFEs en Shell
- [ ] Configurar routing en Shell para cargar MFEs
- [ ] Crear menú de navegación
- [ ] Implementar autenticación global en Shell
- [ ] Implementar guard de autenticación para rutas protegidas

---

## Fase 5: Pruebas Unitarias con Mockito (Semanas 9-10)

### Backend - Pruebas de Dominio

#### Tarea 5.1: Configurar entorno de pruebas
- [ ] Agregar dependencias de test (JUnit 5, Mockito, AssertJ)
- [ ] Crear configuración de test en src/test
- [ ] Configurar perfil de test en application-test.yml

#### Tarea 5.2: Pruebas de ValidadorPersonaService
- [ ] Test: validación de persona adulta con datos válidos
- [ ] Test: validación de menor sin cédula (debe ser válido)
- [ ] Test: validación de cédula duplicada (debe lanzar excepción)
- [ ] Test: validación de campos nulos (debe lanzar excepción)

#### Tarea 5.3: Pruebas de ValidadorFamiliarService
- [ ] Test: validación de familiar adulto con datos válidos
- [ ] Test: validación de familiar menor con adulto responsable
- [ ] Test: validación de cédula duplicada de familiar

### Backend - Pruebas de Casos de Uso

#### Tarea 5.4: Pruebas de PersonaService
- [ ] Test: registro exitoso de persona adulta
- [ ] Test: registro exitoso de menor
- [ ] Test: registro con cédula duplicada
- [ ] Test: actualización de estado de persona
- [ ] Test: actualización a IDENTIFICADO requiere confirmación admin
- [ ] Verificar que los métodos del repositorio mockeado se llaman correctamente

#### Tarea 5.5: Pruebas de FamiliarService
- [ ] Test: registro exitoso de familiar
- [ ] Test: registro con cédula duplicada
- [ ] Test: actualización de datos de familiar

#### Tarea 5.6: Pruebas de BusquedaService
- [ ] Test: búsqueda por nombre con resultados
- [ ] Test: búsqueda sin resultados
- [ ] Test: búsqueda combinada con múltiples filtros
- [ ] Test: paginación funciona correctamente

### Backend - Pruebas de Mapeadores

#### Tarea 5.7: Pruebas de PersonaMapper
- [ ] Test: toDomain convierte correctamente de Entity a Domain
- [ ] Test: toEntity convierte correctamente de Domain a Entity
- [ ] Test: toResponse convierte correctamente de Domain a DTO
- [ ] Test: mapeo preserva todos los campos obligatorios

#### Tarea 5.8: Pruebas de otros mapeadores
- [ ] Test: FamiliarMapper
- [ ] Test: UbicacionMapper
- [ ] Test: RasgosFisicosMapper

### Backend - Pruebas de Controladores

#### Tarea 5.9: Pruebas de integración de controladores
- [ ] Test: POST /api/v1/personas retorna 201 con datos válidos
- [ ] Test: POST /api/v1/personas retorna 400 con datos inválidos
- [ ] Test: GET /api/v1/busqueda retorna resultados paginados
- [ ] Test: Endpoints protegidos retornan 401 sin token
- [ ] Test: Endpoints con roles retornan 403 sin permisos

---

## Tareas Adicionales (Post-Fase 5)

### Backend - Funcionalidades Adicionales

#### Tarea 6.1: Implementar subida de fotos
- [ ] Crear endpoint POST /api/v1/fotos
- [ ] Validar formato (JPEG, PNG) y tamaño (máx 5MB)
- [ ] Almacenar imagen con resolución optimizada
- [ ] Asociar foto a persona existente

#### Tarea 6.2: Implementar eliminación lógica
- [ ] Crear endpoint DELETE /api/v1/personas/{id} (soft delete)
- [ ] Marcar registro como eliminado_logico = true
- [ ] Registrar fecha y usuario de eliminación
- [ ] Excluir de búsquedas públicas
- [ ] Crear endpoint PUT /api/v1/personas/{id}/restaurar

#### Tarea 6.3: Implementar auditoría
- [ ] Crear aspect para registrar operaciones CRUD
- [ ] Guardar en AuditoriaJpaEntity: tabla, operación, usuario, datos
- [ ] Crear endpoint GET /api/v1/admin/auditoria para consulta

#### Tarea 6.4: Implementar cache offline (Frontend)
- [ ] Crear OfflineCacheService en MFE Captura
- [ ] Almacenar registros localmente cuando no hay conexión
- [ ] Detectar estado de conexión
- [ ] Sincronizar cuando se restaura la conexión
- [ ] Mostrar indicadores visuales de estado

### Backend - Documentación

#### Tarea 6.5: Documentar API con OpenAPI
- [ ] Configurar springdoc-openapi
- [ ] Agregar anotaciones @Operation a cada endpoint
- [ ] Documentar esquemas de DTOs
- [ ] Documentar códigos de respuesta
- [ ] Habilitar UI de Swagger en /swagger-ui.html

### Despliegue

#### Tarea 6.6: Preparar para despliegue
- [ ] Crear Dockerfile para Backend
- [ ] Crear Dockerfile para cada MFE
- [ ] Crear docker-compose.yml para ambiente local
- [ ] Crear script de inicialización de base de datos
- [ ] Documentar instrucciones de despliegue

---

## Resumen de Tareas por Estado

| Fase | Total Tareas | Pendientes | En Progreso | Completadas |
|------|--------------|------------|-------------|-------------|
| Fase 1 | 7 | 4 | 0 | 3 |
| Fase 2 | 10 | 10 | 0 | 0 |
| Fase 3 | 9 | 9 | 0 | 0 |
| Fase 4 | 9 | 9 | 0 | 0 |
| Fase 5 | 9 | 9 | 0 | 0 |
| Adicionales | 6 | 6 | 0 | 0 |
| **Total** | **50** | **47** | **0** | **3** |

---

## Notas de Implementación

- Cada tarea debe ser verificada con pruebas antes de marcar como completada
- Seguir el principio de commits pequeños y frecuentes
- Cada fase debe tener su propio branch: `feature/fase-1`, `feature/fase-2`, etc.
- El código debe pasar los checks de CI antes de hacer merge a develop

# Requirements Document

## Introduction

El Sistema de Registro de Personas en Catástrofes es una plataforma humanitaria diseñada para emergencias mayores (terremotos, inundaciones, desastres naturales) que permite registrar personas desaparecidas y facilitar la búsqueda por parte de familiares afectados. El sistema opera en condiciones de conectividad limitada y debe ser accesible desde dispositivos móviles en campo.

El sistema sigue una arquitectura hexagonal estricta con DTOs inmutables (Java Records), mapeo explícito sin frameworks automáticos, y micro frontends para modularidad.

---

## Glossary

- **Sistema**: El Sistema de Registro de Personas en Catástrofes en su totalidad
- **Rescatista**: Personal de emergencia autorizado para registrar personas en campo
- **Familiar_Afectado**: Persona que busca a sus seres queridos desaparecidos
- **Administrador**: Usuario con permisos de gestión completa del sistema
- **Invitado**: Usuario no autenticado que puede subir fotos de desaparecidos
- **Persona_Desaparecida**: Individuo cuyo paradero es desconocido tras una catástrofe
- **Familiar_Registrado**: Persona que registra sus datos para ser contactada sobre un desaparecido
- **Ubicacion**: Coordenadas geográficas o dirección de un punto de interés
- **GPS_Rescatista**: Ubicación obtenida del dispositivo del rescatista en campo
- **Estado_Persona**: Condición actual de una persona (Desaparecido, Encontrado_Vivo, Encontrado_Fallecido, Identificado)
- **Rasgos_Fisicos**: Características físicas descriptivas de una persona
- **Registro_Vivienda**: Información sobre la vivienda habitual del desaparecido
- **Cache_Offline**: Almacenamiento local para operación sin conectividad
- **Micro_Frontend_Captura**: Módulo frontend para registro de personas en campo
- **Micro_Frontend_Consulta**: Módulo frontend para búsqueda pública de desaparecidos

---

## Requirements

### Requirement 1: Registro de Familiares Buscadores

**User Story:** Como Familiar_Afectado, quiero registrar mis datos de contacto y los rasgos físicos de mi familiar desaparecido, para que los rescatistas puedan contactarme cuando haya novedades.

#### Acceptance Criteria

1. THE Sistema SHALL permitir el registro de un Familiar_Registrado con cédula, nombres, número de contacto y relación con el desaparecido
2. WHEN un Familiar_Registrado es menor de edad, THE Sistema SHALL solicitar el nombre del adulto responsable
3. WHEN un Familiar_Registrado es adulto, THE Sistema SHALL requerir únicamente cédula, nombres y número de contacto
4. THE Sistema SHALL almacenar los rasgos físicos del Familiar_Registrado como referencia para identificación
5. IF la cédula ya existe en el sistema, THEN THE Sistema SHALL mostrar un mensaje indicando que el familiar ya está registrado

---

### Requirement 2: Registro de Personas Desaparecidas Adultas

**User Story:** Como Rescatista, quiero registrar personas desaparecidas adultas con su información completa, para facilitar su localización y posterior identificación.

#### Acceptance Criteria

1. WHEN el Rescatista registra una persona adulta, THE Sistema SHALL requerir cédula, nombres, edad y última ubicación conocida
2. THE Sistema SHALL permitir el registro opcional de rasgos físicos para personas adultas
3. THE Sistema SHALL permitir el registro de la dirección de vivienda habitual del desaparecido
4. THE Sistema SHALL asignar el estado "Desaparecido" a toda persona recién registrada
5. WHEN la ubicación se obtiene del GPS_Rescatista, THE Sistema SHALL almacenar las coordenadas geográficas con precisión
6. WHEN la ubicación se obtiene de la última llamada del desaparecido, THE Sistema SHALL registrar la fuente de la información

---

### Requirement 3: Registro de Personas Desaparecidas Menores de Edad

**User Story:** Como Rescatista, quiero registrar menores de edad desaparecidos con información adaptada a su situación, para garantizar su protección y búsqueda adecuada.

#### Acceptance Criteria

1. WHEN el Rescatista registra un menor de edad, THE Sistema SHALL NO requerir cédula como campo obligatorio
2. THE Sistema SHALL capturar automáticamente la ubicación del GPS_Rescatista al momento del registro del menor
3. THE Sistema SHALL requerir nombres completos del menor y edad aproximada
4. THE Sistema SHALL permitir el registro de una evaluación de trauma inicial para el menor
5. THE Sistema SHALL asignar el estado "Desaparecido" al menor recién registrado
6. THE Sistema SHALL asociar el registro del menor con el Familiar_Registrado que lo busca

---

### Requirement 4: Gestión de Estados de Persona

**User Story:** Como Rescatista, quiero actualizar el estado de las personas desaparecidas según avanza la búsqueda, para mantener información precisa sobre su situación.

#### Acceptance Criteria

1. THE Sistema SHALL mantener los siguientes estados para las personas: Desaparecido, Encontrado_Vivo, Encontrado_Fallecido, Identificado
2. WHEN el Rescatista cambia el estado de una persona a "Encontrado_Vivo" o "Encontrado_Fallecido", THE Sistema SHALL registrar la ubicación del hallazgo
3. WHEN el estado de una persona cambia, THE Sistema SHALL notificar a los Familiares_Registrados asociados
4. THE Sistema SHALL mantener un historial de cambios de estado con fecha, hora y usuario que realizó el cambio
5. IF una persona es marcada como "Identificado", THEN THE Sistema SHALL requerir confirmación del Administrador

---

### Requirement 5: Búsqueda de Personas Desaparecidas

**User Story:** Como Familiar_Afectado, quiero buscar a mis familiares desaparecidos usando diferentes criterios, para obtener información sobre su paradero.

#### Acceptance Criteria

1. THE Sistema SHALL permitir búsqueda por nombres completos o parciales
2. THE Sistema SHALL permitir búsqueda por número de cédula
3. THE Sistema SHALL permitir búsqueda por última ubicación conocida (región, ciudad o área)
4. THE Sistema SHALL permitir búsqueda por rango de edad
5. THE Sistema SHALL permitir búsqueda por rasgos físicos registrados
6. WHEN se realizan múltiples búsquedas, THE Sistema SHALL combinar los criterios con operador AND
7. THE Sistema SHALL mostrar los resultados paginados con un máximo de 20 registros por página
8. WHEN no se encuentran resultados, THE Sistema SHALL mostrar un mensaje indicando que no hay coincidencias

---

### Requirement 6: Búsqueda Avanzada para Rescatistas

**User Story:** Como Rescatista, quiero realizar búsquedas avanzadas con múltiples filtros, para localizar personas de manera eficiente en operaciones de campo.

#### Acceptance Criteria

1. THE Sistema SHALL permitir al Rescatista filtrar por estado de persona (Desaparecido, Encontrado_Vivo, Encontrado_Fallecido, Identificado)
2. THE Sistema SHALL permitir filtrar por fecha de registro
3. THE Sistema SHALL permitir filtrar por radio de distancia desde una ubicación central
4. THE Sistema SHALL permitir combinar múltiples criterios de búsqueda simultáneamente
5. WHEN el Rescatista realiza una búsqueda, THE Sistema SHALL responder en menos de 3 segundos
6. THE Sistema SHALL mostrar en los resultados la distancia desde la ubicación actual del Rescatista

---

### Requirement 7: Operación Offline con Sincronización

**User Story:** Como Rescatista, quiero poder registrar personas sin conexión a internet, para continuar trabajando en zonas sin cobertura durante emergencias.

#### Acceptance Criteria

1. WHEN no hay conexión a internet, THE Sistema SHALL almacenar los registros localmente en el Cache_Offline del dispositivo
2. WHEN se restaura la conexión, THE Sistema SHALL sincronizar automáticamente los registros pendientes con el servidor
3. THE Sistema SHALL mostrar indicadores visuales claros del estado de conexión (online/offline/sincronizando)
4. WHEN hay registros pendientes de sincronización, THE Sistema SHALL mostrar un contador de registros no sincronizados
5. IF la sincronización falla, THEN THE Sistema SHALL reintentar automáticamente hasta 3 veces antes de notificar al usuario
6. THE Sistema SHALL preservar la integridad de los datos durante la sincronización

---

### Requirement 8: Notificaciones a Familiares

**User Story:** Como Familiar_Afectado, quiero recibir notificaciones cuando haya novedades sobre mis familiares desaparecidos, para estar informado oportunamente.

#### Acceptance Criteria

1. WHEN el estado de una persona cambia, THE Sistema SHALL enviar una notificación por email al Familiar_Registrado
2. WHEN el estado de una persona cambia, THE Sistema SHALL enviar una notificación por SMS al número de contacto del Familiar_Registrado
3. THE Sistema SHALL permitir al Familiar_Registrado configurar su preferencia de notificación (email, SMS o ambos)
4. WHEN el envío de notificación falla, THE Sistema SHALL registrar el error y reintentar en los siguientes 5 minutos
5. THE Sistema SHALL incluir en la notificación el nuevo estado y datos de contacto del Rescatista asignado

---

### Requirement 9: Subida de Fotos por Invitados

**User Story:** Como Invitado, quiero subir fotos de mis familiares desaparecidos, para facilitar su identificación por parte de los rescatistas.

#### Acceptance Criteria

1. THE Sistema SHALL permitir a cualquier Invitado subir fotos sin necesidad de autenticación
2. THE Sistema SHALL aceptar formatos de imagen JPEG y PNG
3. THE Sistema SHALL limitar el tamaño máximo de imagen a 5 megabytes
4. WHEN una foto es subida, THE Sistema SHALL asociarla a un registro de persona existente mediante cédula o nombre
5. THE Sistema SHALL mostrar la foto en el perfil de la persona desaparecida para facilitar identificación
6. THE Sistema SHALL almacenar la foto con resolución optimizada para visualización en dispositivos móviles

---

### Requirement 10: Autenticación y Control de Acceso

**User Story:** Como Administrador, quiero que el sistema tenga control de acceso basado en roles, para garantizar la seguridad de los datos sensibles.

#### Acceptance Criteria

1. THE Sistema SHALL requerir autenticación para los roles Rescatista, Familiar_Afectado y Administrador
2. THE Sistema SHALL permitir acceso de solo lectura al Familiar_Afectado para consultar registros
3. THE Sistema SHALL permitir al Rescatista crear, editar y consultar registros de personas
4. THE Sistema SHALL permitir al Administrador realizar eliminación lógica de registros
5. THE Sistema SHALL permitir al Administrador gestionar usuarios y roles
6. WHEN un usuario intenta acceder a un recurso sin permisos, THE Sistema SHALL retornar un error 403 Forbidden

---

### Requirement 11: Eliminación Lógica de Registros

**User Story:** Como Administrador, quiero poder eliminar registros de manera lógica, para mantener la integridad histórica de los datos en caso de auditorías.

#### Acceptance Criteria

1. WHEN el Administrador elimina un registro, THE Sistema SHALL marcarlo como inactivo sin borrarlo físicamente
2. THE Sistema SHALL registrar la fecha, hora y usuario que realizó la eliminación lógica
3. WHEN un registro está marcado como eliminado, THE Sistema SHALL excluirlo de las búsquedas públicas
4. THE Sistema SHALL permitir al Administrador restaurar registros eliminados lógicamente
5. THE Sistema SHALL mantener los registros eliminados por un periodo mínimo de 90 días antes de considerar su purga

---

### Requirement 12: Micro Frontend de Captura en Campo

**User Story:** Como Rescatista, quiero una interfaz optimizada para registro rápido desde mi dispositivo móvil, para agilizar mi trabajo en operaciones de campo.

#### Acceptance Criteria

1. THE Micro_Frontend_Captura SHALL mostrar formularios simplificados con campos mínimos obligatorios
2. THE Micro_Frontend_Captura SHALL estar optimizado para pantallas de dispositivos móviles
3. THE Micro_Frontend_Captura SHALL utilizar una paleta de colores accesible bajo condiciones extremas de luz
4. THE Micro_Frontend_Captura SHALL permitir capturar la ubicación GPS con un solo toque
5. THE Micro_Frontend_Captura SHALL funcionar independientemente de otros módulos del sistema
6. THE Micro_Frontend_Captura SHALL integrarse con el Shell principal mediante Module Federation

---

### Requirement 13: Micro Frontend de Consulta Pública

**User Story:** Como Familiar_Afectado, quiero un portal de consulta simple y rápido, para buscar a mis familiares sin complicaciones técnicas.

#### Acceptance Criteria

1. THE Micro_Frontend_Consulta SHALL mostrar una interfaz de búsqueda simple accesible para cualquier usuario
2. THE Micro_Frontend_Consulta SHALL cargar en menos de 2 segundos en conexiones lentas
3. THE Micro_Frontend_Consulta SHALL mostrar resultados en formato de tarjeta con foto y datos básicos
4. THE Micro_Frontend_Consulta SHALL permitir ver detalles completos al seleccionar un resultado
5. THE Micro_Frontend_Consulta SHALL funcionar independientemente de otros módulos del sistema
6. THE Micro_Frontend_Consulta SHALL integrarse con el Shell principal mediante Module Federation

---

### Requirement 14: API REST con DTOs Inmutables

**User Story:** Como desarrollador, quiero que la API REST utilice DTOs inmutables implementados con Java Records, para garantizar la integridad de los datos en tránsito.

#### Acceptance Criteria

1. THE Sistema SHALL implementar todos los DTOs de petición y respuesta como Java Records
2. THE Sistema SHALL exponer endpoints REST para todas las operaciones de registro y consulta
3. THE Sistema SHALL documentar la API siguiendo el estándar OpenAPI
4. THE Sistema SHALL validar los DTOs de entrada antes de procesar las solicitudes
5. WHEN un DTO de entrada no cumple las validaciones, THE Sistema SHALL retornar errores descriptivos con códigos HTTP 400

---

### Requirement 15: Arquitectura Hexagonal Estricta

**User Story:** Como desarrollador, quiero que el sistema siga una arquitectura hexagonal estricta, para mantener el dominio aislado de la infraestructura.

#### Acceptance Criteria

1. THE Sistema SHALL estructurar el código en tres capas: Domain, Ports y Adapters
2. THE capa Domain SHALL contener únicamente lógica de negocio pura sin dependencias de framework
3. THE capa Ports SHALL definir interfaces para comunicación entre dominio e infraestructura
4. THE capa Adapters SHALL implementar las interfaces de Ports usando tecnologías específicas
5. THE Sistema SHALL utilizar inyección por constructor con @RequiredArgsConstructor de Lombok
6. THE Sistema SHALL NO utilizar frameworks de mapeo automáticos, implementando conversión manual

---

### Requirement 16: Persistencia en Base de Datos H2

**User Story:** Como desarrollador, quiero que el sistema utilice H2 como base de datos en memoria, para garantizar despliegue rápido en emergencias.

#### Acceptance Criteria

1. THE Sistema SHALL configurar H2 como base de datos en memoria por defecto
2. THE Sistema SHALL exponer la consola H2 para administración durante desarrollo
3. THE Sistema SHALL utilizar Spring Data JPA para abstracción del acceso a datos
4. THE Sistema SHALL crear las tablas automáticamente al iniciar la aplicación
5. THE Sistema SHALL permitir configurar la persistencia en disco como alternativa a memoria

---

### Requirement 17: Pruebas Unitarias con Mockito

**User Story:** Como desarrollador, quiero que el sistema tenga pruebas unitarias enfocadas en el dominio, para garantizar la calidad del código.

#### Acceptance Criteria

1. THE Sistema SHALL implementar pruebas unitarias para todos los casos de uso del dominio
2. THE pruebas SHALL utilizar Mockito para simular los puertos de salida
3. THE pruebas SHALL ejecutarse sin levantar el contexto de Spring
4. THE Sistema SHALL mantener un cobertura mínima del 80% en la capa de dominio
5. THE pruebas SHALL verificar el comportamiento de los mapeadores manuales

---

### Requirement 18: Trazabilidad y Auditoría

**User Story:** Como Administrador, quiero un registro de todas las operaciones críticas, para poder auditar el sistema en caso de necesidad.

#### Acceptance Criteria

1. THE Sistema SHALL registrar un log de auditoría para cada operación de creación, modificación y eliminación
2. THE log de auditoría SHALL incluir usuario, fecha, hora, operación realizada y datos afectados
3. THE Sistema SHALL almacenar los logs de auditoría de manera persistente
4. THE Sistema SHALL permitir al Administrador consultar los logs de auditoría filtrados por fecha y usuario
5. THE logs de auditoría SHALL ser inmutables una vez creados

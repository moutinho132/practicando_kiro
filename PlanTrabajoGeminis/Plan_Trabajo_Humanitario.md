# Plan de Proyecto: Sistema de Registro de Personas en Catástrofes (Hexagonal + Micro Frontends)

Este documento sirve como especificación técnica detallada y plan de trabajo para el desarrollo del aplicativo de contingencia humanitaria. Diseñado para ser procesado por herramientas de asistencia de IA especializadas en desarrollo de software.

---

## 1. Ficha Técnica del Proyecto

| Componente | Tecnología / Enfoque | Propósito / Rol Técnico |
| :--- | :--- | :--- |
| **Frontend Arquitectura** | Micro frontends (MFE) | Modularidad absoluta, resiliencia ante caídas parciales. |
| **Frontend Framework** | Angular + TypeScript | Robustez en tipado, estructura empresarial y escalabilidad. |
| **Frontend UI** | Bootstrap | Diseño 100% responsivo, ágil, liviano y optimizado para móviles de rescate. |
| **Backend Tipo** | Monolito | Consistencia de transacciones e integridad de datos en emergencias. |
| **Backend Lenguaje** | Java 17 | Uso de características modernas, estabilidad y rendimiento óptimo. |
| **Backend Core** | Arquitectura Hexagonal | Aislamiento total del dominio (reglas de negocio) de la infraestructura. |
| **Base de Datos** | H2 (En memoria) | Persistencia volátil o de contingencia ultra rápida, sin dependencias externas. |
| **Persistencia ORM** | Spring Data JPA | Abstracción del acceso a datos mediante repositorios y entidades. |
| **Seguridad** | Spring Security | Autenticación y autorización basada en roles (Rescatista, Familiar, Admin). |
| **Transferencia Datos** | DTOs (Data Transfer Objects) | Desacoplamiento del modelo interno de persistencia de la API REST. |
| **Mapeo / Conversión** | `Map` nativo de Java | Transformación manual/estructurada de Entidad <-> DTO sin dependencias pesadas. |
| **Inyección de Deps.** | Anotaciones Spring (`@RequiredArgsConstructor`) | Inyección limpia por constructor a través de Lombok. |
| **Librerías Auxiliares**| Lombok | Reducción de código repetitivo (Boilerplate) mediante anotaciones. |
| **Inmutabilidad** | Java Records | Modelado inmutable y limpio para los DTOs de transporte de datos. |
| **Principios de Diseño**| S.O.L.I.D. | Código mantenible, extensible y altamente desacoplable. |
| **Estrategia de Test** | Pruebas Unitarias con Mockito | Validación aislada del Dominio y los Casos de Uso. |

---

## 2. Arquitectura del Backend: Enfoque Hexagonal Especializado

Para garantizar que el núcleo del sistema no se vea afectado por cambios en la base de datos o en la seguridad, estructuraremos el monolito en tres grandes capas conceptuales según los principios SOLID:

```
[ Infraestructura (Adapters) ] ---> [ Puertos (Ports / Interfaces) ] ---> [ Dominio (Domain Core) ]
```

### 2.1. Capa de Dominio (Domain)
* **Responsabilidad:** Contiene la lógica del negocio pura. No sabe que existen bases de datos, ni Spring, ni HTTP.
* **Entidades:** Clases Java puras anotadas únicamente con Lombok para comportamiento básico (ej. `PersonaPerdida`).
* **SOLID (Single Responsibility):** Cada entidad o servicio de dominio maneja una única lógica de negocio (ej. validar si un estado de búsqueda es correcto).

### 2.2. Capa de Puertos (Ports)
* **Responsabilidad:** Interfaces que definen los contratos de comunicación entre el dominio y el mundo exterior.
* **Puertos de Entrada (Inbound):** Interfaces utilizadas por los controladores (ej. `RegistrarPersonaUseCase`).
* **Puertos de Salida (Outbound):** Interfaces que debe implementar la infraestructura para persistir o buscar datos (ej. `PersonaRepositoryPort`).

### 2.3. Capa de Infraestructura (Adapters)
* **Responsabilidad:** Implementaciones tecnológicas de los puertos.
* **Adaptadores de Entrada:** Controladores REST de Spring Boot que reciben DTOs (implementados como *Java Records* para garantizar inmutabilidad).
* **Adaptadores de Salida:** Repositorios de Spring Data JPA que se conectan con la base de datos en memoria **H2** y transforman las Entidades de Dominio a Entidades JPA mediante transformaciones estructurales utilizando `Map`.
* **Seguridad:** Configuración de **Spring Security** para filtrar peticiones según los roles del personal de emergencia.

---

## 3. Estrategia de Mapeo, Registros e Inyección

* **Java Records como DTOs:** Toda la información que entra o sale de la API REST se manejará mediante `record`. Al ser inmutables por definición, previenen alteraciones accidentales de los datos de las víctimas durante el ciclo de vida de la petición HTTP.
* **Mapeo Estructurado con Map (sin Mappers externos):** Para evitar la sobrecarga de dependencias como MapStruct o modelMapper, la transformación se realizará mediante clases dedicadas de conversión que utilizan estructuras de mapeo nativas o flujos de conversión explícitos, garantizando legibilidad total y facilidad para depurar errores.
* **Inyección de Dependencias por Constructor con Lombok:** Aplicando el principio de inversión de dependencias de SOLID, se utilizará `@RequiredArgsConstructor` de Lombok sobre los servicios y adaptadores, lo que autogenera el constructor con los campos `private final`, eliminando el uso de la anotación `@Autowired` directamente en atributos (Mala práctica).

---

## 4. Plan de Trabajo Completo (10 Semanas)

### Fase 1: Arquitectura Base e Infraestructura Inicial (Semanas 1-2)
* **Hito Técnico:** Esqueleto del Backend y Frontend Shell operativos con H2 activo.
* **Backend:**
    * Inicialización del proyecto Spring Boot con Java 17, Lombok, Spring Data JPA, H2 y Spring Security.
    * Configuración de la consola H2 (`/h2-console`) protegida pero accesible para desarrollo.
    * Estructuración de paquetes por capas (domain, ports, adapters).
* **Frontend:**
    * Configuración del proyecto base de Angular.
    * Implementación de la arquitectura de Micro Frontends (MFE) mediante Module Federation (Módulo contenedor o "Shell").
    * Integración de Bootstrap y definición del sistema de diseño responsivo de alta accesibilidad (paleta de colores accesible bajo condiciones extremas de luz).

### Fase 2: Definición de Modelos Inmutables y Dominio (Semanas 3-4)
* **Hito Técnico:** Casos de uso de registro core codificados sin base de datos real.
* **Backend:**
    * Creación de las entidades de dominio puro (`Persona`, `Ubicacion`, `Reporte`).
    * Definición de los **Java Records** como DTOs (`PersonaRegistroRequest`, `PersonaResponse`).
    * Desarrollo de las interfaces de Puertos de Entrada y Salida.
    * Implementación de las clases de mapeo explícito empleando lógica nativa.
* **Frontend:**
    * Desarrollo del Micro frontend 1: **Módulo de Captura en Campo**. Formulario ultra responsivo optimizado para entrada rápida de datos desde smartphones por rescatistas.

### Fase 3: Capa de Persistencia H2 y Spring Data JPA (Semanas 5-6)
* **Hito Técnico:** Flujo de guardado completo y seguro de punta a punta (End-to-End) en memoria.
* **Backend:**
    * Creación de las entidades JPA mapeadas a las tablas de H2.
    * Implementación del adaptador de salida utilizando repositorios Spring Data JPA.
    * Codificación de la lógica que traduce de entidades de Dominio a entidades JPA y viceversa.
    * Configuración inicial de **Spring Security** (Autenticación básica / JWT stateless para los endpoints de la API).
* **Frontend:**
    * Conexión del formulario del MFE con los endpoints expuestos del Backend.

### Fase 4: Búsqueda, Filtros y Micro frontend Público (Semanas 7-8)
* **Hito Técnico:** Portal de búsqueda para familiares operativo con paginación y filtros eficientes.
* **Backend:**
    * Desarrollo del puerto y servicio para búsquedas avanzadas (por nombre, última ubicación conocida, estado de salud).
    * Optimización de consultas JPA sobre la base de datos en memoria H2.
* **Frontend:**
    * Desarrollo del Micro frontend 2: **Portal de Consulta Pública**. Interfaz simple, liviana y veloz orientada a ciudadanos en búsqueda de familiares.
    * Orquestación en el Shell de Angular para integrar ambos MFEs de forma invisible para el usuario.

---

## 5. Plan de Pruebas Unitarias con Mockito (Semanas 9-10)

Las pruebas unitarias se enfocarán en la **Capa de Dominio y Casos de Uso**, aislando completamente los componentes externos de infraestructura gracias al uso de **Mockito**. Al seguir la arquitectura hexagonal, no se requerirá levantar el contexto de Spring (`@SpringBootTest`), haciendo las pruebas extremadamente rápidas y eficientes.

### 5.1. Estrategia de Pruebas SOLID (Single Responsibility & Interface Segregation)
Cada clase de caso de uso tendrá su correspondiente clase de prueba unitaria. Las dependencias externas (los Puertos de Salida) se inyectarán como mocks mediante anotaciones de Mockito.

### 5.2. Componentes Clave a Probar
1.  **Casos de Uso (Services):** Comprobar que las reglas de negocio se ejecuten correctamente bajo diferentes escenarios.
2.  **Mapeadores:** Asegurar que la conversión manual basada en colecciones nativas (`Map`/estructuras explícitas) no altere ni pierda atributos críticos de la persona desaparecida.

### 5.3. Casos de Prueba Críticos Sugeridos
* **Test de Registro Exitoso:** Validar que el caso de uso reciba un Record DTO válido, invoque correctamente el método `.save()` del puerto de salida y devuelva la respuesta inmutable esperada.
* **Test de Validación de Negocio:** Verificar que si se intenta registrar una persona con datos incongruentes o campos obligatorios nulos, el sistema lance una excepción de dominio controlada y no interactúe con el repositorio simulado.
* **Test de Comportamiento del Repositorio Mockeado:** Utilizar `Mockito.when(...).thenReturn(...)` para simular que la base de datos H2 está vacía al buscar un ID inexistente, asegurando que el sistema responda con un DTO vacío controlado en lugar de un error inesperado de puntero nulo (`NullPointerException`).

---

## 6. Instrucciones para la IA de Codificación (Prompt para Kilo)

> "Utiliza este documento de diseño para generar un proyecto Spring Boot en Java 17 estructurado bajo Arquitectura Hexagonal estricta. Utiliza Lombok para inyección por constructor mediante `@RequiredArgsConstructor`. Diseña los DTOs utilizando Java Records inmutables. Implementa una base de datos H2 en memoria gestionada mediante Spring Data JPA. No uses frameworks de mapeo automáticos; escribe convertidores de objetos explícitos y nativos. Asegúrate de incluir la suite de pruebas para los casos de uso utilizando JUnit 5 y Mockito para simular el comportamiento de los puertos de persistencia."

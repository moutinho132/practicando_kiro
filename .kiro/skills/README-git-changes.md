# Sistema de Gestión de Cambios en Git

## Descripción
Sistema completo para automatizar la gestión de cambios en Git cuando completas tareas o solicitudes en Kiro.

## Componentes creados

### 1. Skill principal
**Archivo**: `.kiro/skills/git-changes-skill.md`
- Documentación completa del skill
- Comandos disponibles y ejemplos de uso

### 2. Configuración del skill
**Archivo**: `.kiro/skills/git-changes-skill.json`
- Definición de comandos ejecutables
- Secuencias para tareas completas
- Triggers automáticos

### 3. Hooks configurados

#### a) Auto Git después de tarea
- **Evento**: `postTaskExecution`
- **Acción**: Ejecuta automáticamente `git add . && git commit`
- **ID**: `auto-git-after-task`

#### b) Revisar cambios antes de commit  
- **Evento**: `postTaskExecution`
- **Acción**: Pregunta al agente antes de proceder
- **ID**: `review-before-commit`

#### c) Commit al finalizar solicitud
- **Evento**: `promptSubmit`
- **Acción**: Detecta palabras clave y ofrece hacer commit
- **ID**: `commit-on-request-end`

#### d) Auto-add en cambios de código
- **Evento**: `fileEdited`
- **Acción**: Agrega automáticamente el archivo modificado
- **ID**: `auto-add-code-changes`

### 4. Steering file
**Archivo**: `.kiro/steering/git-workflow.md`
- Guías de buenas prácticas
- Comandos disponibles
- Configuración recomendada

## Cómo usar el sistema

### Flujo automático:
1. Trabaja en tu tarea normalmente
2. Cuando marques la tarea como completada, el hook `auto-git-after-task` se ejecutará automáticamente
3. Los cambios serán agregados y confirmados con un mensaje automático

### Flujo con revisión:
1. Completa tu tarea
2. El hook `review-before-commit` te preguntará si quieres proceder
3. Puedes revisar los cambios antes de confirmar

### Flujo manual:
1. Cuando termines, usa el comando: `git-complete-task "Mi descripción"`
2. O usa los comandos individuales: `git-status`, `git-add`, `git-commit`

### Detección de finalización:
- Si escribes "tarea completada", "requerimientos cumplidos" o frases similares
- El hook `commit-on-request-end` detectará esto y ofrecerá ayuda

## Personalización

### Cambiar mensajes de commit:
Edita el archivo `.kiro/skills/git-changes-skill.json` y modifica los comandos `git-commit` y `git-complete-task`.

### Desactivar hooks:
- Ve a la vista de Hooks en Kiro
- Desactiva los hooks que no necesites

### Agregar más patrones de archivos:
Modifica el hook `auto-add-code-changes` para incluir más extensiones de archivo.

## Recomendaciones

1. **Para tareas pequeñas**: Usa el flujo automático
2. **Para cambios importantes**: Usa el flujo con revisión  
3. **Para commits específicos**: Usa los comandos manuales
4. **Mensajes descriptivos**: Siempre usa mensajes que expliquen qué cambió y por qué

## Prueba del sistema

Para probar el sistema:
1. Crea o modifica un archivo de código
2. Observa cómo se agrega automáticamente al staging area
3. Completa una tarea y observa el comportamiento de los hooks

## Notas
- Los hooks de `fileEdited` solo funcionan con las extensiones configuradas
- El hook `promptSubmit` busca palabras clave específicas
- Puedes crear hooks adicionales según tus necesidades específicas
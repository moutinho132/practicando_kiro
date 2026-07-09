---
inclusion: always
---

# Flujo de trabajo con Git

## Skill de Git disponible

Hemos creado un skill llamado `git-changes-skill` que te ayuda a gestionar cambios en Git cuando completas tareas.

## Comandos disponibles

- `git-status` - Ver estado del repositorio
- `git-add [patrón]` - Agregar cambios
- `git-commit [mensaje]` - Crear commit
- `git-complete-task [mensaje]` - Agregar y confirmar cambios en un paso

## Buenas prácticas

1. **Antes de completar una tarea:**
   - Usa `git-status` para revisar los cambios
   - Asegúrate de que solo incluyes los archivos relevantes

2. **Al completar una tarea:**
   - Usa `git-complete-task "Descripción clara de la tarea"`
   - O hazlo paso a paso: `git-add .` → `git-commit "mensaje"`

3. **Mensajes de commit:**
   - Usa verbos en presente: "Agrega", "Corrige", "Mejora"
   - Sé específico: "Corrige bug en validación de login" no solo "Fix bug"
   - Limita a 50 caracteres el título

## Configuración automática

El skill incluye un trigger que se ejecuta automáticamente después de completar tareas. Si prefieres control manual, puedes desactivarlo editando el archivo de configuración.

## Integración con Kiro

Este skill se integra con el sistema de hooks de Kiro. Puedes crear hooks personalizados para:
- Verificar cambios antes de commits
- Ejecutar linters después de agregar cambios
- Notificar cuando se completan tareas importantes
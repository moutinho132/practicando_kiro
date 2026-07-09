# Git Changes Skill

## Descripción
Skill para gestionar cambios en Git cuando se completa una tarea o se cumplen los requerimientos. Permite agregar y confirmar cambios de manera controlada.

## Comandos disponibles

### `git-status`
Muestra el estado actual del repositorio Git.
- Verifica qué archivos han sido modificados
- Muestra qué cambios están staged o unstaged

### `git-add [patrón]`
Agrega cambios al staging area.
- Sin parámetro: agrega todos los cambios
- Con patrón: agrega solo archivos que coinciden con el patrón

### `git-commit [mensaje]`
Crea un commit con los cambios staged.
- Sin mensaje: usa mensaje por defecto "Auto-commit: cambios completados"
- Con mensaje: usa el mensaje proporcionado

### `git-complete-task [mensaje]`
Comando completo que:
1. Muestra el estado actual
2. Agrega todos los cambios
3. Crea commit con mensaje

## Ejemplos de uso

1. **Después de completar una tarea:**
   ```
   git-complete-task "Agregada funcionalidad de login"
   ```

2. **Solo ver estado:**
   ```
   git-status
   ```

3. **Agregar cambios específicos:**
   ```
   git-add src/
   ```

## Flujo recomendado

Cuando termines de trabajar en una solicitud o cumplas con los requerimientos:

1. Revisa los cambios con `git-status`
2. Agrega los cambios con `git-add` (o usa patrón específico)
3. Confirma los cambios con `git-commit "descripción clara del cambio"`

O usa el comando combinado `git-complete-task` para hacer todo en un paso.

## Notas importantes
- Siempre verifica qué archivos vas a agregar antes de hacer commit
- Usa mensajes descriptivos en los commits
- Este skill no hace push automáticamente para evitar conflictos
- Puedes configurar hooks de Kiro para que este skill se ejecute automáticamente después de completar tareas
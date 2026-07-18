package com.catastrofes.registro.domain;

/**
 * Enumeración que define los roles de usuario en el sistema
 * de registro de catástrofes.
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public enum Role {
    /**
     * Personal de emergencia en campo.
     * Permisos: Crear, editar, consultar personas; búsquedas avanzadas.
     */
    RESCATISTA,
    
    /**
     * Familiar afectado.
     * Permisos: Consultar registros asociados; actualizar sus datos.
     */
    FAMILIAR,
    
    /**
     * Administrador del sistema.
     * Permisos: Gestión completa; eliminación lógica; gestión de usuarios.
     */
    ADMIN
}

package com.catastrofes.registro.port.out;

/**
 * Puerto de salida (Port) para el envío de notificaciones.
 * Define las operaciones de notificación disponibles,
 * independientemente del mecanismo concreto (email, SMS, etc.).
 * 
 * @author Sistema de Catástrofes
 * @version 1.0.0
 */
public interface NotificacionPort {
    
    /**
     * Envía una notificación por correo electrónico.
     * 
     * @param destinatario El correo del destinatario
     * @param asunto El asunto del correo
     * @param mensaje El contenido del mensaje
     * @return true si el envío fue exitoso, false si falló
     */
    boolean enviarEmail(String destinatario, String asunto, String mensaje);
    
    /**
     * Envía una notificación por SMS.
     * 
     * @param numeroTelefono El número de teléfono del destinatario
     * @param mensaje El contenido del mensaje
     * @return true si el envío fue exitoso, false si falló
     */
    boolean enviarSms(String numeroTelefono, String mensaje);
    
    /**
     * Envía una notificación de cambio de estado de persona a un familiar.
     * 
     * @param familiarId El ID del familiar a notificar
     * @param nombrePersona El nombre de la persona cuyo estado cambió
     * @param nuevoEstado El nuevo estado
     * @return true si el envío fue exitoso, false si falló
     */
    boolean notificarCambioEstado(Long familiarId, String nombrePersona, String nuevoEstado);
    
    /**
     * Envía una notificación masiva a múltiples destinatarios.
     * 
     * @param destinatarios Lista de correos electrónicos
     * @param asunto El asunto del correo
     * @param mensaje El contenido del mensaje
     * @return Número de notificaciones enviadas exitosamente
     */
    int enviarNotificacionMasiva(java.util.List<String> destinatarios, String asunto, String mensaje);
}

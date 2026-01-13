package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.servicios.dto.NotificacionDTO;

import java.util.List;
import java.util.Optional;


public interface NotificacionService {

    /**
     * Crea y guarda una nueva notificación en el sistema.
     * La fecha de envío y el estado 'no leída' se establecen automáticamente.
     * @param notificacion La notificación a crear (debe incluir el usuario destinatario y el mensaje).
     * @return La notificación creada, con su ID asignado por la persistencia.
     */
    Notificacion crearNotificacion(Notificacion notificacion);

    void marcarComoLeida(int notificacionId);
    Optional<NotificacionDTO> obtenerNotificacionPorId(int id);
    List<NotificacionDTO> listarNotificacionesPorUsuario();
    List<NotificacionDTO> listarNotificacionesNoLeidasPorUsuario();

    // Otros métodos que podrían ser útiles:
    // List<Notificacion> listarTodasLasNotificaciones(); // Para administración
    // void eliminarNotificacion(int notificacionId); // Para eliminar notificaciones
}

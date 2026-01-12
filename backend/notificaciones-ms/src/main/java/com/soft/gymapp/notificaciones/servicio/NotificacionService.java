package com.soft.gymapp.notificaciones.servicio;

import com.soft.gymapp.notificaciones.dominio.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    // Crea y guarda una nueva notificación en el sistema.

    Notificacion crearNotificacion(Notificacion notificacion);

    void marcarComoLeida(Long notificacionId); 
    
    Optional<Notificacion> obtenerNotificacionPorId(Long id);
    
    List<Notificacion> listarNotificacionesPorUsuario(Long usuarioId);
    
    List<Notificacion> listarNotificacionesNoLeidasPorUsuario(Long usuarioId);
}
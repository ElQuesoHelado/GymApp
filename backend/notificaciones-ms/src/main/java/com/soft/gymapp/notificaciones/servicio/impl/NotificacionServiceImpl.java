package com.soft.gymapp.notificaciones.servicio.impl;

import com.soft.gymapp.notificaciones.dominio.Notificacion;
import com.soft.gymapp.notificaciones.repositorio.NotificacionRepositorio;
import com.soft.gymapp.notificaciones.servicio.NotificacionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepositorio notificacionRepositorio;

    public NotificacionServiceImpl(NotificacionRepositorio notificacionRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
    }

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        // 1. Validar Mensaje
        if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la notificación no puede estar vacío.");
        }
        
        // 2. Validar Usuario ID
        if (notificacion.getUsuarioId() == null || notificacion.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("La notificación debe tener un ID de usuario válido.");
        }

        // 3. Configurar fecha y estado
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(new Date());
        }
        notificacion.setLeido(false);

        return notificacionRepositorio.save(notificacion);
    }

    @Override
    public void marcarComoLeida(Long notificacionId) {
        Notificacion notificacion = notificacionRepositorio.findById(notificacionId)
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con ID: " + notificacionId));

        notificacion.marcarComoLeida();
        notificacionRepositorio.save(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        return notificacionRepositorio.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarNotificacionesPorUsuario(Long usuarioId) {
        return notificacionRepositorio.findByUsuarioId(usuarioId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarNotificacionesNoLeidasPorUsuario(Long usuarioId) {
        return notificacionRepositorio.findByUsuarioIdAndLeidoFalse(usuarioId);
    }
}
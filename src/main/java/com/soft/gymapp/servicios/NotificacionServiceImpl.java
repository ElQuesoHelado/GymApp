package com.soft.gymapp.servicios;

import com.soft.gymapp.servicios.NotificacionService;
import com.soft.gymapp.servicios.exceptions.RecursoNoEncontradoException;
import com.soft.gymapp.servicios.exceptions.UsuarioNoEncontradoException; // Importar si no estaba
import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.dominio.repository.NotificacionRepositorio;
import com.soft.gymapp.dominio.repository.UsuarioRepositorio; // Necesario para validar que el destinatario existe

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepositorio notificacionRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public NotificacionServiceImpl(NotificacionRepositorio notificacionRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.notificacionRepositorio = notificacionRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        // Validaciones de negocio:
        if (notificacion.getMensaje() == null || notificacion.getMensaje().trim().isEmpty()) {
            throw new IllegalArgumentException("El mensaje de la notificación no puede estar vacío.");
        }
        // notificacion.getUsuario().getId() ahora devuelve int, por lo que no puede ser null directamente
        if (notificacion.getUsuario() == null || notificacion.getUsuario().getId() == 0) { // Asumiendo 0 como valor por defecto si no se establece
            throw new IllegalArgumentException("La notificación debe tener un usuario destinatario válido.");
        }

        // Verificar que el usuario destinatario existe en la base de datos
        usuarioRepositorio.findById(notificacion.getUsuario().getId())
                .orElseThrow(() -> new UsuarioNoEncontradoException("El usuario destinatario de la notificación no existe."));

        // Asegurarse de que la fecha de envío y el estado inicial sean correctos
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(new Date());
        }
        notificacion.setLeido(false);
        notificacion.enviar();

        return notificacionRepositorio.save(notificacion);
    }

    @Override
    public void marcarComoLeida(int notificacionId) {
        Notificacion notificacion = notificacionRepositorio.findById(notificacionId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Notificación no encontrada con ID: " + notificacionId));

        notificacion.marcarComoLeida();
        notificacionRepositorio.save(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Notificacion> obtenerNotificacionPorId(int id) {
        return notificacionRepositorio.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarNotificacionesPorUsuario(int usuarioId) {
        // verificar que el usuario exista
        usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario destinatario no encontrado con ID: " + usuarioId));

        return notificacionRepositorio.findByUsuario_Id(usuarioId); // Método personalizado con int ID
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notificacion> listarNotificacionesNoLeidasPorUsuario(int usuarioId) {
        // verificar que el usuario exista
        usuarioRepositorio.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario destinatario no encontrado con ID: " + usuarioId));

        return notificacionRepositorio.findByUsuario_IdAndLeidoFalse(usuarioId); // Método personalizado con int ID
    }
}
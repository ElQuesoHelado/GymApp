package com.soft.gymapp.servicios;

import com.soft.gymapp.servicios.NotificacionService;
import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.dominio.notificaciones.NotificacionRepositorio;
import com.soft.gymapp.dominio.usuarios.UsuarioRepositorio; // Necesario para validar que el destinatario existe

import com.soft.gymapp.servicios.dto.NotificacionDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.soft.gymapp.servicios.dto.UsuarioDTO;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepositorio notificacionRepositorio;
    private final UsuarioService usuarioService;

    public NotificacionServiceImpl(NotificacionRepositorio notificacionRepositorio,
                                   UsuarioService usuarioService) {
        this.notificacionRepositorio = notificacionRepositorio;
        this.usuarioService = usuarioService;
    }

    private NotificacionDTO toDTO(Notificacion n) {
        return new NotificacionDTO(
                n.getId(),
                n.getMensaje(),
                n.isLeido(),
                n.getFechaEnvio(),
                n.getTipo()
        );
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

        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioLogueado();
        if(usuarioDTO.id() != notificacion.getUsuario().getId()){
            throw new IllegalArgumentException("La notificación debe tener un usuario destinatario válido.");
        }

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
                .orElseThrow(() -> new IllegalArgumentException("Notificación no encontrada con ID: " + notificacionId));

        notificacion.marcarComoLeida();
        notificacionRepositorio.save(notificacion);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificacionDTO> obtenerNotificacionPorId(int id) {
        return notificacionRepositorio.findById(id)
                .map(this::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarNotificacionesPorUsuario() {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioLogueado();

        return notificacionRepositorio.findByUsuarioId(usuarioDTO.id())
                .stream().map(this::toDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> listarNotificacionesNoLeidasPorUsuario() {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioLogueado();

        return notificacionRepositorio.findByUsuarioIdAndLeidoFalse(usuarioDTO.id())
                .stream().map(this::toDTO).toList();
    }
}
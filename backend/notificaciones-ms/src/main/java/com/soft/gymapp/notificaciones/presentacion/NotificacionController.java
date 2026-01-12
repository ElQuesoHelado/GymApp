package com.soft.gymapp.notificaciones.presentacion;

import com.soft.gymapp.notificaciones.dominio.Notificacion;
import com.soft.gymapp.notificaciones.servicio.NotificacionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @PostMapping
    public Notificacion enviarNotificacion(@RequestBody Notificacion notificacion) {
        return notificacionService.crearNotificacion(notificacion);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> listarNotificacionesUsuario(@PathVariable Long usuarioId) {
        return notificacionService.listarNotificacionesPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public List<Notificacion> listarNoLeidas(@PathVariable Long usuarioId) {
        return notificacionService.listarNotificacionesNoLeidasPorUsuario(usuarioId);
    }

    @PutMapping("/{id}/leer")
    public void marcarComoLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
    }
}
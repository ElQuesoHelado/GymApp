package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.servicios.NotificacionService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

  private final NotificacionService notificacionService;

  public NotificacionController(NotificacionService notificacionService) {
    this.notificacionService = notificacionService;
  }

  @PostMapping
  public Notificacion
  enviarNotificacion(@RequestBody Notificacion notificacion) {
    return notificacionService.crearNotificacion(notificacion);
  }

  @GetMapping("/usuario/{usuarioId}")
  public List<Notificacion>
  listarNotificacionesUsuario(@PathVariable int usuarioId) {

    return notificacionService.listarNotificacionesPorUsuario(usuarioId);
  }

  @GetMapping("/usuario/{usuarioId}/no-leidas")
  public List<Notificacion> listarNoLeidas(@PathVariable int usuarioId) {

    return notificacionService.listarNotificacionesNoLeidasPorUsuario(
        usuarioId);
  }

  @PutMapping("/{id}/leer")
  public void marcarComoLeida(@PathVariable int id) {
    notificacionService.marcarComoLeida(id);
  }
}

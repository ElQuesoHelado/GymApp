package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.notificaciones.Notificacion;
import com.soft.gymapp.servicios.NotificacionService;
import java.util.List;
import java.util.Map;

import com.soft.gymapp.servicios.dto.NotificacionDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
@PreAuthorize("isAuthenticated()")
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

  @GetMapping
  public List<NotificacionDTO> listar() {
    return notificacionService.listarNotificacionesPorUsuario();
  }

  @GetMapping("/no-leidas")
  public List<NotificacionDTO> listarNoLeidas() {
    return notificacionService.listarNotificacionesNoLeidasPorUsuario();
  }

  @PutMapping("/{id}/leer")
  public void marcarComoLeida(@PathVariable int id) {
    notificacionService.marcarComoLeida(id);
  }
}

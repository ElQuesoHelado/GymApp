package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.SesionService;
import java.util.List;

import com.soft.gymapp.servicios.dto.SesionDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sesiones")
public class SesionController {

  private final SesionService sesionService;

  public SesionController(SesionService sesionService) {
    this.sesionService = sesionService;
  }

  @PostMapping
  public Sesion programarSesion(@RequestBody Sesion sesion) {
    return sesionService.programarSesion(sesion);
  }

  @PutMapping("/{id}/cancelar")
  public void cancelarSesion(@PathVariable int id) {
    sesionService.cancelarSesion(id);
  }

  @GetMapping()
  public List<SesionDTO> listarSesionesPorUsuario() {
    return sesionService.listarSesionesPorUsuario();
  }
}

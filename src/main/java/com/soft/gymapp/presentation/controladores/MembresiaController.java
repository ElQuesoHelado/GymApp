package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.servicios.MembresiaService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membresias")
public class MembresiaController {

  private final MembresiaService membresiaService;

  public MembresiaController(MembresiaService membresiaService) {
    this.membresiaService = membresiaService;
  }

  @GetMapping
  public List<Membresia>
  listarMembresias(@RequestParam(required = false) EstadoMembresia estado) {

    if (estado != null) {
      return membresiaService.buscarPorEstado(estado);
    }

    return membresiaService.buscarPorEstado(EstadoMembresia.ACTIVADA);
  }

  @PutMapping("/{id}/cancelar")
  public Membresia cancelarMembresia(@PathVariable Integer id) {
    return membresiaService.cancelarMembresia(id);
  }

  @PutMapping("/{id}/adeudar")
  public Membresia marcarComoAdeudada(@PathVariable Integer id,
                                      @RequestBody Map<String, Double> body) {

    Double monto = body.get("monto");
    return membresiaService.marcarComoAdeudada(id, monto);
  }
}

package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/planes-entrenamiento")
public class PlanEntrenamientoController {

  private final PlanEntrenamientoService planEntrenamientoService;

  public PlanEntrenamientoController(
      PlanEntrenamientoService planEntrenamientoService) {
    this.planEntrenamientoService = planEntrenamientoService;
  }

  private static final String STATUS = "status";
  private static final String MESSAGE = "message";
  private static final String SUCCESS = "success";
  private static final String ERROR = "error";

  /**
   * Obtener plan de entrenamiento por cliente
   */
  @GetMapping("/cliente/{clienteId}")
  @PreAuthorize("hasRole('CLIENTE') or hasRole('ENTRENADOR')")
  public ResponseEntity<?>
  obtenerPlanPorCliente(@PathVariable Integer clienteId) {

    PlanEntrenamientoDTO plan =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(clienteId);

    if (plan == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(Map.of(STATUS, ERROR, MESSAGE,
                       "No se encontró plan de entrenamiento"));
    }

    return ResponseEntity.ok(plan);
  }

  /**
   * Crear rutina (simulado)
   */
  @PostMapping("/rutinas")
  @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>>
  crearRutina(@RequestBody Map<String, Object> rutinaRequest) {

    if (!rutinaRequest.containsKey("nombre") ||
        !rutinaRequest.containsKey("objetivo")) {

      return ResponseEntity.badRequest().body(
          Map.of(STATUS, ERROR, MESSAGE, "Nombre y objetivo son requeridos"));
    }

    Map<String, Object> response = new HashMap<>();
    response.put(STATUS, SUCCESS);
    response.put(MESSAGE, "Rutina creada correctamente");
    response.put("rutina",
                 Map.of("id", 1, "nombre", rutinaRequest.get("nombre"),
                        "objetivo", rutinaRequest.get("objetivo"),
                        "fechaCreacion", LocalDate.now().toString()));

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  /**
   * Asignar rutina a cliente (simulado)
   */
  @PostMapping("/rutinas/asignar")
  @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>>
  asignarRutina(@RequestBody Map<String, Object> request) {

    if (!request.containsKey("rutinaId") || !request.containsKey("clienteId")) {

      return ResponseEntity.badRequest().body(Map.of(
          STATUS, ERROR, MESSAGE, "rutinaId y clienteId son requeridos"));
    }

    return ResponseEntity.ok(
        Map.of(STATUS, SUCCESS, MESSAGE, "Rutina asignada correctamente"));
  }

  /**
   * Modificar rutina (simulado)
   */
  @PutMapping("/rutinas/{id}")
  @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
  public ResponseEntity<Map<String, Object>>
  modificarRutina(@PathVariable Integer id,
                  @RequestBody Map<String, Object> updates) {

    if (id <= 0) {
      return ResponseEntity.badRequest().body(
          Map.of(STATUS, ERROR, MESSAGE, "ID inválido"));
    }

    return ResponseEntity.ok(Map.of(STATUS, SUCCESS, MESSAGE,
                                    "Rutina modificada", "id", id,
                                    "actualizaciones", updates));
  }
}

@Controller
@RequestMapping("/cliente")
public class PlanEntrenamientoViewController {

  @PreAuthorize("hasRole('CLIENTE')")
  @GetMapping("/planes-entrenamiento")
  public String vistaPlanes() {
    return "planes_entrenamiento";
  }
}

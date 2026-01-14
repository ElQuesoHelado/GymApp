package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planes-entrenamiento")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class PlanEntrenamientoController {

    private final PlanEntrenamientoService planEntrenamientoService;

    public PlanEntrenamientoController(PlanEntrenamientoService planEntrenamientoService) {
        this.planEntrenamientoService = planEntrenamientoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
    public ResponseEntity<List<PlanEntrenamientoDTO>> listarTodosLosPlanes() {
        return ResponseEntity.ok(planEntrenamientoService.listarTodosLosPlanes()); 
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('CLIENTE') or hasRole('ENTRENADOR')")
    public ResponseEntity<?> obtenerPlanPorCliente(@PathVariable Integer clienteId) {
        PlanEntrenamientoDTO plan = planEntrenamientoService.getPlanEntrenamientoPorClienteId(); 
        if (plan == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Este cliente aún no tiene un plan asignado"));
        }
        return ResponseEntity.ok(plan);
    }

    @PostMapping
    @PreAuthorize("hasRole('ENTRENADOR')")
    public @ResponseBody ResponseEntity<PlanEntrenamientoDTO> crearPlan(@RequestBody PlanEntrenamientoDTO dto) {
        System.out.println("Intentando crear plan: " + dto);
        try {
            PlanEntrenamientoDTO nuevoPlan = planEntrenamientoService.crearNuevoPlan(dto);
            return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<Void> eliminarPlan(@PathVariable int id) {
        planEntrenamientoService.eliminarPlan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{planId}/rutinas/{rutinaId}")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<PlanEntrenamientoDTO> asignarRutina(
            @PathVariable int planId, 
            @PathVariable int rutinaId) {
        return ResponseEntity.ok(planEntrenamientoService.asignarRutinaAPlan(planId, rutinaId));
    }

    @GetMapping("/rutinas")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<List<RutinaDTO>> listarRutinasParaAsignar() {
        return ResponseEntity.ok(planEntrenamientoService.listarTodasLasRutinas()); 
    }

    @DeleteMapping("/{planId}/rutinas/{rutinaId}")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<Void> removerRutina(@PathVariable int planId, @PathVariable int rutinaId) {
        planEntrenamientoService.removerRutinaDePlan(planId, rutinaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clientes")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public ResponseEntity<List<UsuarioDTO>> listarClientes() {
        return ResponseEntity.ok(planEntrenamientoService.listarClientesParaAsignar());
    }
}
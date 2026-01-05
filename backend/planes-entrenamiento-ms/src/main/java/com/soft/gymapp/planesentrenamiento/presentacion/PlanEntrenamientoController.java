package com.soft.gymapp.planesentrenamiento.presentacion;

import com.soft.gymapp.planesentrenamiento.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.planesentrenamiento.servicio.PlanEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/planes-entrenamiento")
public class PlanEntrenamientoController {
    @Autowired
    private PlanEntrenamientoService planService;

    @GetMapping
    public List<PlanEntrenamientoDTO> listarPlanes() {
        return planService.listarPlanes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanEntrenamientoDTO> getPlan(@PathVariable int id) {
        PlanEntrenamientoDTO dto = planService.getPlanEntrenamientoPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PlanEntrenamientoDTO crearPlan(@RequestBody PlanEntrenamientoDTO dto) {
        return planService.crearPlan(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarPlan(@PathVariable int id) {
        planService.eliminarPlan(id);
    }
}

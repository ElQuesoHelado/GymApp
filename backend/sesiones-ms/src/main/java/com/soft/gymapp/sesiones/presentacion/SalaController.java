package com.soft.gymapp.sesiones.presentacion;

import com.soft.gymapp.sesiones.dominio.Sala;
import com.soft.gymapp.sesiones.dto.ActualizarSalaDTO;
import com.soft.gymapp.sesiones.servicio.SalaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de salas.
 */
@RestController
@RequestMapping("/api/v1/salas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SalaController {

    private final SalaServicio salaServicio;

    public SalaController(SalaServicio salaServicio) {
        this.salaServicio = salaServicio;
    }

    @PostMapping
    public ResponseEntity<Sala> crearSala(
            @RequestParam String nombre,
            @RequestParam Integer capacidad,
            @RequestParam(required = false) String descripcion) {
        Sala sala = salaServicio.crearSala(nombre, capacidad, descripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(sala);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> obtenerPorId(@PathVariable Long id) {
        return salaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Sala>> obtenerTodas() {
        List<Sala> salas = salaServicio.obtenerTodas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Sala>> obtenerActivas() {
        List<Sala> salas = salaServicio.obtenerActivas();
        return ResponseEntity.ok(salas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> actualizarSala(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarSalaDTO actualizar) {
        return salaServicio.actualizarSala(id, actualizar)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Sala> desactivarSala(@PathVariable Long id) {
        return salaServicio.desactivarSala(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activar")
    public ResponseEntity<Sala> activarSala(@PathVariable Long id) {
        return salaServicio.activarSala(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarSala(@PathVariable Long id) {
        boolean eliminada = salaServicio.eliminarSala(id);
        if (eliminada) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Sala eliminada correctamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}

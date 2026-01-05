package com.soft.gymapp.sesiones.presentacion;

import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import com.soft.gymapp.sesiones.dominio.Horario;
import com.soft.gymapp.sesiones.dto.CrearSesionDTO;
import com.soft.gymapp.sesiones.dto.SesionDTO;
import com.soft.gymapp.sesiones.servicio.SesionServicio;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de sesiones.
 */
@RestController
@RequestMapping("/api/v1/sesiones")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SesionController {

    private final SesionServicio sesionServicio;

    public SesionController(SesionServicio sesionServicio) {
        this.sesionServicio = sesionServicio;
    }

    // CRUD
    @PostMapping
    public ResponseEntity<SesionDTO> crearSesion(@Valid @RequestBody CrearSesionDTO crearDTO) {
        SesionDTO sesion = sesionServicio.crearSesion(crearDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sesion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SesionDTO> obtenerPorId(@PathVariable Long id) {
        return sesionServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<SesionDTO>> obtenerPorEntrenador(@PathVariable Long idEntrenador) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorEntrenador(idEntrenador);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/plan/{idPlanEntrenamiento}")
    public ResponseEntity<List<SesionDTO>> obtenerPorPlan(@PathVariable Long idPlanEntrenamiento) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorPlan(idPlanEntrenamiento);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SesionDTO>> obtenerPorEstado(@PathVariable EstadoSesion estado) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorEstado(estado);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/sala/{idSala}")
    public ResponseEntity<List<SesionDTO>> obtenerPorSala(@PathVariable Long idSala) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorSala(idSala);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<SesionDTO>> obtenerPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorFecha(fecha);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/sala/{idSala}/fecha")
    public ResponseEntity<List<SesionDTO>> obtenerPorSalaYFecha(
            @PathVariable Long idSala,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<SesionDTO> sesiones = sesionServicio.obtenerPorSalaYFecha(idSala, fecha);
        return ResponseEntity.ok(sesiones);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<SesionDTO>> obtenerDisponibles() {
        List<SesionDTO> sesiones = sesionServicio.obtenerDisponibles();
        return ResponseEntity.ok(sesiones);
    }

    // OPERACIONES DE ESTADO
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<SesionDTO> confirmarSesion(@PathVariable Long id) {
        return sesionServicio.confirmarSesion(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<SesionDTO> cancelarSesion(@PathVariable Long id) {
        return sesionServicio.cancelarSesion(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/terminar")
    public ResponseEntity<SesionDTO> terminarSesion(@PathVariable Long id) {
        return sesionServicio.terminarSesion(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<SesionDTO> reprogramarSesion(
            @PathVariable Long id,
            @RequestBody Horario nuevoHorario) {
        return sesionServicio.reprogramarSesion(id, nuevoHorario)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/agregar-participante")
    public ResponseEntity<SesionDTO> agregarParticipante(@PathVariable Long id) {
        return sesionServicio.agregarParticipante(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/remover-participante")
    public ResponseEntity<SesionDTO> removerParticipante(@PathVariable Long id) {
        return sesionServicio.removerParticipante(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarSesion(@PathVariable Long id) {
        boolean eliminada = sesionServicio.eliminarSesion(id);
        if (eliminada) {
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Sesión eliminada correctamente");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // ESTADÍSTICAS
    @GetMapping("/estadisticas/estado/{estado}")
    public ResponseEntity<Map<String, Object>> contarPorEstado(@PathVariable EstadoSesion estado) {
        long cantidad = sesionServicio.contarPorEstado(estado);
        Map<String, Object> response = new HashMap<>();
        response.put("estado", estado);
        response.put("cantidad", cantidad);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/estadisticas/entrenador/{idEntrenador}")
    public ResponseEntity<Map<String, Object>> contarPorEntrenador(@PathVariable Long idEntrenador) {
        long cantidad = sesionServicio.contarPorEntrenador(idEntrenador);
        Map<String, Object> response = new HashMap<>();
        response.put("idEntrenador", idEntrenador);
        response.put("cantidad", cantidad);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("servicio", "Sesiones Microservicio");
        return ResponseEntity.ok(response);
    }
}

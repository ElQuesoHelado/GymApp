package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rutinas")
@CrossOrigin(origins = "http://localhost:5173")
public class RutinaRestController {

    @Autowired
    private RutinaService rutinaService;

    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@RequestBody RutinaDTO dto) {
        try {
            Rutina rutinaGuardada = rutinaService.crearRutina(dto);
            return new ResponseEntity<>(convertirADTO(rutinaGuardada), HttpStatus.CREATED);
        } catch (Exception e) { return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); }
    }

    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listarTodas() {
        List<Rutina> rutinas = rutinaService.listarTodasLasRutinas();
        List<RutinaDTO> respuesta = rutinas.stream().map(this::convertirADTO).collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRutina(@PathVariable Integer id) {
        try {
            rutinaService.eliminarRutina(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/ejercicios")
    public ResponseEntity<RutinaDTO> agregarEjercicio(@PathVariable Integer id, @RequestBody EjercicioDTO ejercicioDTO) {
        try {
            Rutina rutinaActualizada = rutinaService.agregarEjercicio(id, ejercicioDTO);
            return ResponseEntity.ok(convertirADTO(rutinaActualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private RutinaDTO convertirADTO(Rutina r) {
        List<EjercicioDTO> ejerciciosDTO = r.getEjercicios().stream()
            .map(e -> new EjercicioDTO(e.getId(), e.getNombre(), e.getDescripcion(), e.getRepeticiones(), e.getSeries()))
            .collect(Collectors.toList());
        return new RutinaDTO(r.getId(), r.getNombre(), r.getObjetivo(), ejerciciosDTO);
    }
}
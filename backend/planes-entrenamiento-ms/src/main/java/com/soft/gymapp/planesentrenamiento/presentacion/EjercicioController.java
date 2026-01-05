package com.soft.gymapp.planesentrenamiento.presentacion;

import com.soft.gymapp.planesentrenamiento.dto.EjercicioDTO;
import com.soft.gymapp.planesentrenamiento.servicio.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {
    @Autowired
    private EjercicioService ejercicioService;

    @GetMapping
    public List<EjercicioDTO> listarEjercicios() {
        return ejercicioService.listarEjercicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjercicioDTO> getEjercicio(@PathVariable int id) {
        EjercicioDTO dto = ejercicioService.getEjercicioPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public EjercicioDTO crearEjercicio(@RequestBody EjercicioDTO dto) {
        return ejercicioService.crearEjercicio(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarEjercicio(@PathVariable int id) {
        ejercicioService.eliminarEjercicio(id);
    }
}

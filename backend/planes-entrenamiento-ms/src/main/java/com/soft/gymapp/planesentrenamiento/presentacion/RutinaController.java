package com.soft.gymapp.planesentrenamiento.presentacion;

import com.soft.gymapp.planesentrenamiento.dto.RutinaDTO;
import com.soft.gymapp.planesentrenamiento.servicio.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {
    @Autowired
    private RutinaService rutinaService;

    @GetMapping
    public List<RutinaDTO> listarRutinas() {
        return rutinaService.listarRutinas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutinaDTO> getRutina(@PathVariable int id) {
        RutinaDTO dto = rutinaService.getRutinaPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public RutinaDTO crearRutina(@RequestBody RutinaDTO dto) {
        return rutinaService.crearRutina(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminarRutina(@PathVariable int id) {
        rutinaService.eliminarRutina(id);
    }
}

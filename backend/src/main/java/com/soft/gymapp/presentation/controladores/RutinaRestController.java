package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/routines")
public class RutinaRestController {

    private List<Rutina> memoriaTemporal = new ArrayList<>();

    @PostMapping
    public ResponseEntity<RutinaDTO> crearRutina(@RequestBody RutinaDTO dto) {
        // Lógica de negocio
        Rutina nuevaRutina = new Rutina(dto.getNombre(), dto.getObjetivo());
        memoriaTemporal.add(nuevaRutina);
        
        dto.setId(memoriaTemporal.size());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RutinaDTO>> listarTodas() {
        // Aquí simulamos la respuesta con datos de ejemplo usando tu EjercicioDTO
        List<EjercicioDTO> ejerciciosEjemplo = List.of(
            new EjercicioDTO(1, "Sentadillas", "Bajar con espalda recta", 12, 4),
            new EjercicioDTO(2, "Prensa", "Empuje de piernas", 15, 3)
        );

        List<RutinaDTO> respuesta = memoriaTemporal.stream()
                .map(r -> new RutinaDTO(r.getId(), r.getNombre(), r.getObjetivo(), ejerciciosEjemplo))
                .toList();
                
        return ResponseEntity.ok(respuesta);
    }
}
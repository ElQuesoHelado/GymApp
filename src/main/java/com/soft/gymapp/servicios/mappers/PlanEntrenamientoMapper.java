package com.soft.gymapp.servicios.mappers;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlanEntrenamientoMapper {

    public PlanEntrenamientoDTO toDTO(PlanEntrenamiento plan) {
        if (plan == null) {
            return null;
        }
        
        return new PlanEntrenamientoDTO(
                plan.getId(),
                plan.getFechaInicio(),
                plan.getDuracionSemanas(),
                plan.getRutinas().stream()
                        .map(this::toRutinaDTO)
                        .collect(Collectors.toList())
        );
    }

    public PlanEntrenamiento toEntity(PlanEntrenamientoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PlanEntrenamiento plan = new PlanEntrenamiento();
        plan.setId(dto.getId());
        plan.setFechaInicio(dto.getFechaInicio());
        plan.setDuracionSemanas(dto.getDuracionSemanas());
        
        // NOTA: Para convertir rutinas DTO a entidades, necesitarías
        // otro mapper o lógica adicional. Por ahora lo dejamos simple.
        
        return plan;
    }

    private RutinaDTO toRutinaDTO(Rutina rutina) {
        if (rutina == null) {
            return null;
        }
        
        // Asegúrate que RutinaDTO existe y tiene este constructor
        return new RutinaDTO(
                rutina.getId(),
                rutina.getNombre(),
                rutina.getObjetivo(),
                rutina.getEjercicios().stream()
                        .map(this::toEjercicioDTO)
                        .collect(Collectors.toList())
        );
    }

    private EjercicioDTO toEjercicioDTO(Ejercicio ejercicio) {
        if (ejercicio == null) {
            return null;
        }
        
        // Asegúrate que EjercicioDTO existe y tiene este constructor
        return new EjercicioDTO(
                ejercicio.getId(),
                ejercicio.getNombre(),
                ejercicio.getDescripcion(),
                ejercicio.getRepeticiones(),
                ejercicio.getSeries()
        );
    }
}
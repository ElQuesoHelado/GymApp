package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    final PlanEntrenamientoRepositorio planEntrenamientoRepositorio;

    @Autowired
    public PlanEntrenamientoServiceImpl(PlanEntrenamientoRepositorio planEntrenamientoRepositorio) {
        this.planEntrenamientoRepositorio = planEntrenamientoRepositorio;
    }

    @Override
    public PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId(Integer cliente_id) {
        Optional<PlanEntrenamiento> res = planEntrenamientoRepositorio.findByClienteId(cliente_id);

        return res.map(this::convertirPlanADTO).orElse(null);
    }

    private PlanEntrenamientoDTO convertirPlanADTO(PlanEntrenamiento planEntrenamiento) {
        return new PlanEntrenamientoDTO(planEntrenamiento.getId(), planEntrenamiento.getFechaInicio(),
                planEntrenamiento.getDuracionSemanas(),
                planEntrenamiento.getRutinas().stream()
                        .map(this::convertirRutinaADTO).collect(Collectors.toList()));
    }

    private RutinaDTO convertirRutinaADTO(Rutina rutina) {
        return new RutinaDTO(rutina.getId(), rutina.getNombre(), rutina.getObjetivo(),
                rutina.getEjercicios().stream()
                        .map(this::convertirEjercicioADTO)
                        .collect(Collectors.toList()));
    }

    private EjercicioDTO convertirEjercicioADTO(Ejercicio ejercicio) {
        return new EjercicioDTO(ejercicio.getId(), ejercicio.getNombre(),
                ejercicio.getDescripcion(), ejercicio.getRepeticiones(), ejercicio.getSeries());
    }
}
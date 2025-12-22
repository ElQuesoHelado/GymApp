package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.mappers.PlanEntrenamientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    private final PlanEntrenamientoRepositorio planEntrenamientoRepositorio;
    private final PlanEntrenamientoMapper planEntrenamientoMapper;

    @Autowired
    public PlanEntrenamientoServiceImpl(
            PlanEntrenamientoRepositorio planEntrenamientoRepositorio,
            PlanEntrenamientoMapper planEntrenamientoMapper) {
        this.planEntrenamientoRepositorio = planEntrenamientoRepositorio;
        this.planEntrenamientoMapper = planEntrenamientoMapper;
    }

    @Override
    public PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId(Integer clienteId) {
        return planEntrenamientoRepositorio.findByClienteId(clienteId)
                .map(planEntrenamientoMapper::toDTO)
                .orElse(null);
    }

    @Override
    public PlanEntrenamientoDTO getPlanEntrenamientoPorId(Integer planId) {
        return planEntrenamientoRepositorio.findById(planId)
                .map(planEntrenamientoMapper::toDTO)
                .orElse(null);
    }

    @Override
    public PlanEntrenamientoDTO crearPlanEntrenamiento(PlanEntrenamientoDTO planDTO) {
        PlanEntrenamiento plan = planEntrenamientoMapper.toEntity(planDTO);
        PlanEntrenamiento savedPlan = planEntrenamientoRepositorio.save(plan);
        return planEntrenamientoMapper.toDTO(savedPlan);
    }

    @Override
    public PlanEntrenamientoDTO actualizarPlanEntrenamiento(Integer planId, PlanEntrenamientoDTO planDTO) {
        return planEntrenamientoRepositorio.findById(planId)
                .map(existingPlan -> {
                    existingPlan.setFechaInicio(planDTO.getFechaInicio());
                    existingPlan.setDuracionSemanas(planDTO.getDuracionSemanas());
                    PlanEntrenamiento updatedPlan = planEntrenamientoRepositorio.save(existingPlan);
                    return planEntrenamientoMapper.toDTO(updatedPlan);
                })
                .orElseThrow(() -> new IllegalArgumentException("Plan de entrenamiento no encontrado con ID: " + planId));
    }

    @Override
    public boolean eliminarPlanEntrenamiento(Integer planId) {
        if (planEntrenamientoRepositorio.existsById(planId)) {
            planEntrenamientoRepositorio.deleteById(planId);
            return true;
        }
        return false;
    }

    @Override
    public List<PlanEntrenamientoDTO> listarTodosLosPlanes() {
        return planEntrenamientoRepositorio.findAll().stream()
                .map(planEntrenamientoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlanEntrenamientoDTO asignarRutinaAPlan(Integer planId, Integer rutinaId) {
        return planEntrenamientoRepositorio.findById(planId)
                .map(plan -> {
                    Rutina rutina = new Rutina(); // Placeholder
                    plan.asignarRutina(rutina);
                    PlanEntrenamiento updatedPlan = planEntrenamientoRepositorio.save(plan);
                    return planEntrenamientoMapper.toDTO(updatedPlan);
                })
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado con ID: " + planId));
    }

    @Override
    public List<PlanEntrenamientoDTO> getPlanesPorEntrenadorId(Integer entrenadorId) {
        return planEntrenamientoRepositorio.findAll().stream()
                .filter(plan -> plan.getEntrenador() != null && 
                            plan.getEntrenador().getId() == entrenadorId)
                .map(planEntrenamientoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
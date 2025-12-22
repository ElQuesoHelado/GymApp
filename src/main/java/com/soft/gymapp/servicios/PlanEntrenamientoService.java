package com.soft.gymapp.servicios;

import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;

import java.util.List;

public interface PlanEntrenamientoService {
    
    // SOLO CAMBIA ESTA LÍNEA:
    PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId(Integer clienteId); // cliente_id → clienteId
    
    // Añade los otros métodos si no los tienes:
    PlanEntrenamientoDTO getPlanEntrenamientoPorId(Integer planId);
    PlanEntrenamientoDTO crearPlanEntrenamiento(PlanEntrenamientoDTO planDTO);
    PlanEntrenamientoDTO actualizarPlanEntrenamiento(Integer planId, PlanEntrenamientoDTO planDTO);
    boolean eliminarPlanEntrenamiento(Integer planId);
    List<PlanEntrenamientoDTO> listarTodosLosPlanes();
    PlanEntrenamientoDTO asignarRutinaAPlan(Integer planId, Integer rutinaId);
    List<PlanEntrenamientoDTO> getPlanesPorEntrenadorId(Integer entrenadorId);
}
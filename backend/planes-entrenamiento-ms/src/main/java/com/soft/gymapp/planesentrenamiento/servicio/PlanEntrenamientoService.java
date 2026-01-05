package com.soft.gymapp.planesentrenamiento.servicio;

import com.soft.gymapp.planesentrenamiento.dto.PlanEntrenamientoDTO;
import java.util.List;

public interface PlanEntrenamientoService {
    PlanEntrenamientoDTO getPlanEntrenamientoPorId(int id);
    List<PlanEntrenamientoDTO> listarPlanes();
    PlanEntrenamientoDTO crearPlan(PlanEntrenamientoDTO dto);
    void eliminarPlan(int id);
}

package com.soft.gymapp.servicios;

import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;

import java.util.List;

public interface PlanEntrenamientoService {
    PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId();
    List<PlanEntrenamientoDTO> getPlanEntrenamientoPorEntrenadorId();
    
    List<PlanEntrenamientoDTO> listarTodosLosPlanes();
    PlanEntrenamientoDTO crearNuevoPlan(PlanEntrenamientoDTO dto);
    void eliminarPlan(int id);
    PlanEntrenamientoDTO asignarRutinaAPlan(int planId, int rutinaId);
    List<RutinaDTO> listarTodasLasRutinas();
    void removerRutinaDePlan(int planId, int rutinaId);
    List<UsuarioDTO> listarClientesParaAsignar();}
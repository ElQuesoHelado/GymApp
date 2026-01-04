package com.soft.gymapp.servicios;

import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;

import java.util.List;

public interface PlanEntrenamientoService {
    public PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId(Integer cliente_id);


}
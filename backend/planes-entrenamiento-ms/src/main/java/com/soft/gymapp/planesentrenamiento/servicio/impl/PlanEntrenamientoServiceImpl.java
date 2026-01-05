package com.soft.gymapp.planesentrenamiento.servicio.impl;

import com.soft.gymapp.planesentrenamiento.dominio.PlanEntrenamiento;
import com.soft.gymapp.planesentrenamiento.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.planesentrenamiento.repositorio.PlanEntrenamientoRepositorio;
import com.soft.gymapp.planesentrenamiento.servicio.PlanEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {
    @Autowired
    private PlanEntrenamientoRepositorio planRepo;

    @Override
    public PlanEntrenamientoDTO getPlanEntrenamientoPorId(int id) {
        return planRepo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<PlanEntrenamientoDTO> listarPlanes() {
        return planRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PlanEntrenamientoDTO crearPlan(PlanEntrenamientoDTO dto) {
        PlanEntrenamiento plan = new PlanEntrenamiento();
        // ...mapear campos desde dto...
        plan = planRepo.save(plan);
        return toDTO(plan);
    }

    @Override
    public void eliminarPlan(int id) {
        planRepo.deleteById(id);
    }

    private PlanEntrenamientoDTO toDTO(PlanEntrenamiento plan) {
        // ...mapear entidad a DTO...
        return new PlanEntrenamientoDTO();
    }
}

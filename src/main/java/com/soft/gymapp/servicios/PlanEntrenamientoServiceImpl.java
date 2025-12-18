package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
<<<<<<< HEAD
import com.soft.gymapp.servicios.mappers.PlanEntrenamientoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
=======
import com.soft.gymapp.servicios.dto.RutinaDTO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
>>>>>>> bb1cd2e (refactor: Mejorar PlanEntrenamientoServiceImpl - limpiar imports y nombres variables. Fix #31)
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

<<<<<<< HEAD
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
=======
  private final PlanEntrenamientoRepositorio planEntrenamientoRepositorio;

  @Autowired
  public PlanEntrenamientoServiceImpl(
      PlanEntrenamientoRepositorio planEntrenamientoRepositorio) {
    this.planEntrenamientoRepositorio = planEntrenamientoRepositorio;
  }

  @Override
  public PlanEntrenamientoDTO
  getPlanEntrenamientoPorClienteId(Integer clienteId) {
    validarIdCliente(clienteId);

    Optional<PlanEntrenamiento> planOptional =
        planEntrenamientoRepositorio.findByClienteId(clienteId);

    return planOptional.map(this::convertirPlanADTO).orElse(null);
  }

  private void validarIdCliente(Integer clienteId) {
    if (clienteId == null) {
      throw new IllegalArgumentException("El ID del cliente no puede ser nulo");
    }
    if (clienteId <= 0) {
      throw new IllegalArgumentException("El ID del cliente debe ser positivo");
    }
  }

  private PlanEntrenamientoDTO
  convertirPlanADTO(PlanEntrenamiento planEntrenamiento) {
    Objects.requireNonNull(planEntrenamiento,
                           "El plan de entrenamiento no puede ser nulo");

    List<RutinaDTO> rutinasDTO =
        convertirRutinasADTO(planEntrenamiento.getRutinas());

    return new PlanEntrenamientoDTO(
        planEntrenamiento.getId(), planEntrenamiento.getFechaInicio(),
        planEntrenamiento.getDuracionSemanas(), rutinasDTO);
  }

  private List<RutinaDTO> convertirRutinasADTO(List<Rutina> rutinas) {
    if (rutinas == null || rutinas.isEmpty()) {
      return List.of();
    }

    return rutinas.stream()
        .filter(Objects::nonNull)
        .map(this::convertirRutinaADTO)
        .collect(Collectors.toList());
  }

  private RutinaDTO convertirRutinaADTO(Rutina rutina) {
    Objects.requireNonNull(rutina, "La rutina no puede ser nula");

    List<EjercicioDTO> ejerciciosDTO =
        convertirEjerciciosADTO(rutina.getEjercicios());

    return new RutinaDTO(rutina.getId(), rutina.getNombre(),
                         rutina.getObjetivo(), ejerciciosDTO);
  }

  private List<EjercicioDTO>
  convertirEjerciciosADTO(List<Ejercicio> ejercicios) {
    if (ejercicios == null || ejercicios.isEmpty()) {
      return List.of();
    }

    return ejercicios.stream()
        .filter(Objects::nonNull)
        .map(this::convertirEjercicioADTO)
        .collect(Collectors.toList());
  }

  private EjercicioDTO convertirEjercicioADTO(Ejercicio ejercicio) {
    Objects.requireNonNull(ejercicio, "El ejercicio no puede ser nulo");

    return new EjercicioDTO(ejercicio.getId(), ejercicio.getNombre(),
                            ejercicio.getDescripcion(),
                            ejercicio.getRepeticiones(), ejercicio.getSeries());
  }
>>>>>>> bb1cd2e (refactor: Mejorar PlanEntrenamientoServiceImpl - limpiar imports y nombres variables. Fix #31)
}
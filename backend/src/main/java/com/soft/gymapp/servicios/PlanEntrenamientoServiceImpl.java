package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

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
}
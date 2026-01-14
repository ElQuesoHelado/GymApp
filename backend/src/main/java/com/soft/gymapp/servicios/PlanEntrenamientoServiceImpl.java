package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    private final UsuarioService usuarioService;
    private final PlanEntrenamientoRepositorio planEntrenamientoRepositorio;

    @Autowired
    public PlanEntrenamientoServiceImpl(
            UsuarioService usuarioService, PlanEntrenamientoRepositorio planEntrenamientoRepositorio) {
        this.usuarioService = usuarioService;
        this.planEntrenamientoRepositorio = planEntrenamientoRepositorio;
    }

    private EjercicioDTO toDTO(Ejercicio e) {
        return new EjercicioDTO(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getSeries(),
                e.getRepeticiones()
        );
    }

    private RutinaDTO toDTO(Rutina r) {
        return new RutinaDTO(
                r.getId(),
                r.getNombre(),
                r.getObjetivo(),
                r.getEjercicios().stream()
                        .map(this::toDTO)
                        .toList()
        );
    }

    private PlanEntrenamientoDTO toDTO(PlanEntrenamiento p) {
        return new PlanEntrenamientoDTO(
                p.getId(),
                p.getFechaInicio(),
                p.getDuracionSemanas(),
                p.getCliente() != null ? p.getCliente().getId() : null,
                p.getEntrenador() != null ? p.getEntrenador().getId() : null,
                p.getRutinas().stream()
                        .map(this::toDTO)
                        .toList()
        );
    }

    @Override
    public PlanEntrenamientoDTO
    getPlanEntrenamientoPorClienteId() {
        ClienteDTO clienteDTO = usuarioService.obtenerClienteLogueado();

        return planEntrenamientoRepositorio.findByClienteId(clienteDTO.id())
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<PlanEntrenamientoDTO>
    getPlanEntrenamientoPorEntrenadorId() {
        UsuarioDTO usuarioDTO = usuarioService.obtenerEntrenadorLogueado();

        return planEntrenamientoRepositorio.findByEntrenadorId(usuarioDTO.id())
                .stream()
                .map(this::toDTO)
                .toList();
    }
}
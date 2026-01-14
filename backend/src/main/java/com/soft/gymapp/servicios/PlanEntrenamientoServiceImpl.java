package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.dominio.planesentrenamiento.RutinaRepositorio;
import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.dominio.usuarios.UsuarioRepositorio;
import com.soft.gymapp.servicios.dto.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    private final UsuarioService usuarioService;
    private final PlanEntrenamientoRepositorio planEntrenamientoRepositorio;
    private final RutinaRepositorio rutinaRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public PlanEntrenamientoServiceImpl(
            UsuarioService usuarioService, 
            PlanEntrenamientoRepositorio planEntrenamientoRepositorio,
            RutinaRepositorio rutinaRepositorio,
            UsuarioRepositorio usuarioRepositorio) {  
        this.usuarioService = usuarioService;
        this.planEntrenamientoRepositorio = planEntrenamientoRepositorio;
        this.rutinaRepositorio = rutinaRepositorio;
        this.usuarioRepositorio = usuarioRepositorio; 
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
        List<RutinaDTO> rutinasDTO = new java.util.ArrayList<>();
        
        if (p.getRutinas() != null) {
            rutinasDTO = p.getRutinas().stream()
                .map(r -> new RutinaDTO(
                    r.getId(), 
                    r.getNombre(), 
                    r.getObjetivo(),
                    null
                ))
                .toList();
        }

        return new PlanEntrenamientoDTO(
            p.getId(),
            p.getFechaInicio(),
            p.getDuracionSemanas(),
            p.getCliente() != null ? p.getCliente().getId() : null,
            p.getEntrenador() != null ? p.getEntrenador().getId() : null,
            rutinasDTO
        );
    }

    @Override
    public PlanEntrenamientoDTO getPlanEntrenamientoPorClienteId() {
        ClienteDTO clienteDTO = usuarioService.obtenerClienteLogueado();
        return planEntrenamientoRepositorio.findByClienteId(clienteDTO.id()).map(this::toDTO).orElse(null);
    }

    @Override
    public List<PlanEntrenamientoDTO> getPlanEntrenamientoPorEntrenadorId() {
        UsuarioDTO usuarioDTO = usuarioService.obtenerEntrenadorLogueado();
        return planEntrenamientoRepositorio.findByEntrenadorId(usuarioDTO.id())
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public List<PlanEntrenamientoDTO> listarTodosLosPlanes() {
        return planEntrenamientoRepositorio.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlanEntrenamientoDTO crearNuevoPlan(PlanEntrenamientoDTO dto) {
        try {
            PlanEntrenamiento nuevoPlan = new PlanEntrenamiento();
            nuevoPlan.setFechaInicio(dto.fechaInicio() != null ? dto.fechaInicio() : LocalDate.now());
            nuevoPlan.setDuracionSemanas(dto.duracionSemanas() > 0 ? dto.duracionSemanas() : 4);

            UsuarioDTO entrenadorDTO = usuarioService.obtenerEntrenadorLogueado();
            usuarioRepositorio.findById(entrenadorDTO.id()).ifPresent(u -> {
                if (u instanceof Entrenador entrenador) {
                    nuevoPlan.setEntrenador(entrenador);
                }
            });

            if (dto.clienteId() != null && dto.clienteId() > 0) {
                usuarioRepositorio.findById(dto.clienteId()).ifPresent(u -> {
                    if (u instanceof Cliente cliente) {
                        nuevoPlan.setCliente(cliente);
                    }
                });
            }

            PlanEntrenamiento guardado = planEntrenamientoRepositorio.save(nuevoPlan);
            
            return toDTO(guardado);
            
        } catch (Exception e) {
            System.err.println("FALLO CRÍTICO AL CREAR PLAN: " + e.getMessage());
            throw new RuntimeException("Error interno del servidor al procesar el plan");
        }
    }

    @Override
    public void eliminarPlan(int id) {
        if (planEntrenamientoRepositorio.existsById(id)) {
            planEntrenamientoRepositorio.deleteById(id);
        } else {
            throw new RuntimeException("El plan no existe");
        }
    }

    @Override
    @Transactional
    public PlanEntrenamientoDTO asignarRutinaAPlan(int planId, int rutinaId) {
        PlanEntrenamiento plan = planEntrenamientoRepositorio.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        Rutina rutina = rutinaRepositorio.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        plan.getRutinas().add(rutina);
        PlanEntrenamiento guardado = planEntrenamientoRepositorio.save(plan);
        
        return toDTO(guardado); 
    }

    @Override
    public List<RutinaDTO> listarTodasLasRutinas() {
        return rutinaRepositorio.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public void removerRutinaDePlan(int planId, int rutinaId) {
        PlanEntrenamiento plan = planEntrenamientoRepositorio.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        
        plan.getRutinas().removeIf(rutina -> rutina.getId() == rutinaId);
        
        planEntrenamientoRepositorio.save(plan);
    }

    @Override
    public List<UsuarioDTO> listarClientesParaAsignar() {
        return usuarioService.listarTodosLosClientes();
    }
}
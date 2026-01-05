package com.soft.gymapp.sesiones.servicio;

import com.soft.gymapp.sesiones.dominio.EstadoSesion;
import com.soft.gymapp.sesiones.dominio.Horario;
import com.soft.gymapp.sesiones.dominio.Sesion;
import com.soft.gymapp.sesiones.dto.CrearSesionDTO;
import com.soft.gymapp.sesiones.dto.SesionDTO;
import com.soft.gymapp.sesiones.repositorio.SesionRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para la gestión de sesiones.
 */
@Service
@Transactional
public class SesionServicioImpl implements SesionServicio {

    private final SesionRepositorio sesionRepositorio;

    public SesionServicioImpl(SesionRepositorio sesionRepositorio) {
        this.sesionRepositorio = sesionRepositorio;
    }

    @Override
    public SesionDTO crearSesion(CrearSesionDTO crearDTO) {
        Sesion sesion = new Sesion(
                crearDTO.getIdPlanEntrenamiento(),
                crearDTO.getIdEntrenador(),
                crearDTO.getHorario()
        );
        sesion.setIdSala(crearDTO.getIdSala());
        sesion.setDescripcion(crearDTO.getDescripcion());
        
        Sesion guardada = sesionRepositorio.save(sesion);
        return convertirADTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionDTO> obtenerPorId(Long id) {
        return sesionRepositorio.findById(id)
                .map(this::convertirADTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorEntrenador(Long idEntrenador) {
        return sesionRepositorio.findByIdEntrenadorOrderByHorarioFechaDesc(idEntrenador)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorPlan(Long idPlanEntrenamiento) {
        return sesionRepositorio.findByIdPlanEntrenamientoOrderByHorarioFechaDesc(idPlanEntrenamiento)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorEstado(EstadoSesion estado) {
        return sesionRepositorio.findByEstadoOrderByHorarioFechaDesc(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorSala(Long idSala) {
        return sesionRepositorio.findByIdSalaOrderByHorarioFechaDesc(idSala)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorFecha(LocalDate fecha) {
        return sesionRepositorio.findSesionesPorFecha(fecha)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerPorSalaYFecha(Long idSala, LocalDate fecha) {
        return sesionRepositorio.findSesionesPorSalaYFecha(idSala, fecha)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SesionDTO> confirmarSesion(Long id) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.confirmar();
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public Optional<SesionDTO> cancelarSesion(Long id) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.cancelar();
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public Optional<SesionDTO> terminarSesion(Long id) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.terminar();
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public Optional<SesionDTO> reprogramarSesion(Long id, Horario nuevoHorario) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.reprogramar(nuevoHorario);
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public Optional<SesionDTO> agregarParticipante(Long id) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.agregarParticipante();
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public Optional<SesionDTO> removerParticipante(Long id) {
        return sesionRepositorio.findById(id)
                .map(sesion -> {
                    sesion.removerParticipante();
                    Sesion actualizada = sesionRepositorio.save(sesion);
                    return convertirADTO(actualizada);
                });
    }

    @Override
    public boolean eliminarSesion(Long id) {
        if (sesionRepositorio.existsById(id)) {
            sesionRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorEstado(EstadoSesion estado) {
        return sesionRepositorio.countByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarPorEntrenador(Long idEntrenador) {
        return sesionRepositorio.countByIdEntrenador(idEntrenador);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionDTO> obtenerDisponibles() {
        return sesionRepositorio.findByEstadoOrderByHorarioFechaDesc(EstadoSesion.SIN_EMPEZAR)
                .stream()
                .filter(Sesion::estaDisponible)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private SesionDTO convertirADTO(Sesion sesion) {
        return new SesionDTO(
                sesion.getId(),
                sesion.getIdPlanEntrenamiento(),
                sesion.getIdEntrenador(),
                sesion.getIdSala(),
                sesion.getEstado(),
                sesion.getHorario(),
                sesion.getDescripcion(),
                sesion.getFechaCreacion(),
                sesion.getNumeroParticipantes()
        );
    }
}

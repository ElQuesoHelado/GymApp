package com.soft.gymapp.dominio.planesentrenamiento;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder y manipular los planes de entrenamiento.
 */
public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {

    Logger log = LoggerFactory.getLogger(PlanEntrenamientoRepositorio.class);

    /**
     * Busca un plan de entrenamiento por el ID del cliente.
     *
     * @param clienteId el ID del cliente
     * @return un Optional con el plan si existe
     */
    Optional<PlanEntrenamiento> findByClienteId(Integer clienteId);

    /**
     * Guarda una nueva rutina asociada a los planes indicados.
     *
     * @param rutina la rutina a guardar
     */
    default void guardar(Rutina rutina) {
        if (rutina == null) {
            log.error("No se puede guardar una rutina nula.");
            return;
        }

        List<PlanEntrenamiento> planes = rutina.getPlanEntrenamiento();
        if (planes == null || planes.isEmpty()) {
            log.error("La rutina no tiene planes de entrenamiento asociados.");
            return;
        }

        for (PlanEntrenamiento plan : planes) {
            if (plan == null || plan.getId() == 0) {
                log.warn("Plan de entrenamiento inválido o sin ID.");
            } else {
                Optional<PlanEntrenamiento> planExistente = findById(plan.getId());
                if (planExistente.isPresent()) {
                    planExistente.get().asignarRutina(rutina);
                    save(planExistente.get());
                    log.info("Rutina asociada al plan con ID: {}", plan.getId());
                } else {
                    log.error("No se encontró el plan de entrenamiento con ID: {}", plan.getId());
                }
            }
        }

    }

    /**
     * Actualiza una rutina existente.
     *
     * @param rutina la rutina actualizada
     */
    default void actualizar(Rutina rutina) {
        if (rutina == null || rutina.getId() == 0) {
            log.error("La rutina o su ID no pueden ser nulos al actualizar.");
            return;
        }
        guardar(rutina);
    }

    /**
     * Elimina una rutina de todos los planes por su ID.
     *
     * @param rutinaId el ID de la rutina a eliminar
     */
    default void eliminarPorId(String rutinaId) {
        if (rutinaId == null || rutinaId.isBlank()) {
            log.error("ID de rutina inválido para eliminación.");
            return;
        }

        findAll().forEach(plan -> {
            boolean fueEliminada = plan.getRutinas().stream()
                .anyMatch(r -> rutinaId.equals(String.valueOf(r.getId())));

            if (fueEliminada) {
                List<Rutina> nuevasRutinas = new ArrayList<>(plan.getRutinas());
                nuevasRutinas.removeIf(r -> rutinaId.equals(String.valueOf(r.getId())));
                plan.setRutinas(nuevasRutinas);
                save(plan);
                log.info("Rutina con ID {} eliminada del plan con ID {}", rutinaId, plan.getId());
            }
        });
    }

    /**
     * Busca una rutina por su ID.
     *
     * @param rutinaId el ID de la rutina
     * @return un Optional con la rutina encontrada
     */
    default Optional<Rutina> buscarPorId(String rutinaId) {
        if (rutinaId == null || rutinaId.isBlank()) {
            log.error("ID de rutina inválido para búsqueda.");
            return Optional.empty();
        }

        return findAll().stream()
                .flatMap(plan -> plan.getRutinas().stream())
                .filter(r -> rutinaId.equals(String.valueOf(r.getId())))
                .findFirst();
    }

    /**
     * Lista todas las rutinas de todos los planes.
     *
     * @return lista de rutinas
     */
    default List<Rutina> listarTodas() {
        return findAll().stream()
                .flatMap(plan -> plan.getRutinas().stream())
                .distinct() // por si la misma rutina está en varios planes
                .toList();
    }
}

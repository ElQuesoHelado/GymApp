package com.soft.gymapp.dominio.planesentrenamiento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder y manipular los planes de entrenamiento.
 */
public interface PlanEntrenamientoRepositorio extends JpaRepository<PlanEntrenamiento, Integer> {

    /**
     * Busca un plan de entrenamiento por el ID del cliente.
     *
     * @param clienteId el ID del cliente
     * @return un Optional con el plan si existe
     */
    Optional<PlanEntrenamiento> findByClienteId(Integer clienteId);

    /**
     * Guarda una nueva rutina asociada al plan.
     *
     * @param rutina la rutina a guardar
     */
    default void guardar(Rutina rutina) {
        if (rutina == null) {
            throw new IllegalArgumentException("La rutina no puede ser nula");
        }
        rutina.getPlanEntrenamiento().getRutinas().add(rutina);
        save(rutina.getPlanEntrenamiento());
    }

    /**
     * Actualiza una rutina existente.
     *
     * @param rutina la rutina actualizada
     */
    default void actualizar(Rutina rutina) {
        if (rutina == null || rutina.getId() == null) {
            throw new IllegalArgumentException("La rutina o su ID no pueden ser nulos");
        }
        guardar(rutina);
    }

    /**
     * Elimina una rutina del plan por su ID.
     *
     * @param rutinaId el ID de la rutina a eliminar
     */
    default void eliminarPorId(String rutinaId) {
        findAll().forEach(plan -> {
            plan.getRutinas().removeIf(r -> r.getId().equals(rutinaId));
            save(plan);
        });
    }

    /**
     * Busca una rutina por su ID.
     *
     * @param rutinaId el ID de la rutina
     */
    default Optional<Rutina> buscarPorId(String rutinaId) {
        return findAll().stream()
                .flatMap(plan -> plan.getRutinas().stream())
                .filter(r -> r.getId().equals(rutinaId))
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
                .toList();
    }
}

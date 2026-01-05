package com.soft.gymapp.sesiones.servicio;

import com.soft.gymapp.sesiones.dominio.Sala;
import com.soft.gymapp.sesiones.dto.ActualizarSalaDTO;
import com.soft.gymapp.sesiones.repositorio.SalaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar salas.
 */
@Service
@Transactional
public class SalaServicio {

    private final SalaRepositorio salaRepositorio;

    public SalaServicio(SalaRepositorio salaRepositorio) {
        this.salaRepositorio = salaRepositorio;
    }

    public Sala crearSala(String nombre, Integer capacidad, String descripcion) {
        if (salaRepositorio.existsByNombre(nombre)) {
            throw new IllegalArgumentException("Ya existe una sala con ese nombre");
        }
        Sala sala = new Sala(nombre, capacidad, descripcion);
        return salaRepositorio.save(sala);
    }

    public Optional<Sala> obtenerPorId(Long id) {
        return salaRepositorio.findById(id);
    }

    public List<Sala> obtenerActivas() {
        return salaRepositorio.findByActivaTrueOrderByNombre();
    }

    public List<Sala> obtenerTodas() {
        return salaRepositorio.findAll();
    }

    public Optional<Sala> actualizarSala(Long id, ActualizarSalaDTO actualizar) {
        return salaRepositorio.findById(id)
                .map(sala -> {
                    sala.setNombre(actualizar.getNombre());
                    sala.actualizarCapacidad(actualizar.getCapacidad());
                    if (actualizar.getDescripcion() != null) {
                        sala.setDescripcion(actualizar.getDescripcion());
                    }
                    return salaRepositorio.save(sala);
                });
    }

    public Optional<Sala> desactivarSala(Long id) {
        return salaRepositorio.findById(id)
                .map(sala -> {
                    sala.desactivar();
                    return salaRepositorio.save(sala);
                });
    }

    public Optional<Sala> activarSala(Long id) {
        return salaRepositorio.findById(id)
                .map(sala -> {
                    sala.activar();
                    return salaRepositorio.save(sala);
                });
    }

    public boolean eliminarSala(Long id) {
        if (salaRepositorio.existsById(id)) {
            salaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }
}

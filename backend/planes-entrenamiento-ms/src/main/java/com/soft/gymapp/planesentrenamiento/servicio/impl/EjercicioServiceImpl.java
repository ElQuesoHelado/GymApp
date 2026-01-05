package com.soft.gymapp.planesentrenamiento.servicio.impl;

import com.soft.gymapp.planesentrenamiento.dominio.Ejercicio;
import com.soft.gymapp.planesentrenamiento.dto.EjercicioDTO;
import com.soft.gymapp.planesentrenamiento.repositorio.EjercicioRepositorio;
import com.soft.gymapp.planesentrenamiento.servicio.EjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjercicioServiceImpl implements EjercicioService {
    @Autowired
    private EjercicioRepositorio ejercicioRepo;

    @Override
    public EjercicioDTO getEjercicioPorId(int id) {
        return ejercicioRepo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<EjercicioDTO> listarEjercicios() {
        return ejercicioRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EjercicioDTO crearEjercicio(EjercicioDTO dto) {
        Ejercicio ejercicio = new Ejercicio();
        // ...mapear campos desde dto...
        ejercicio = ejercicioRepo.save(ejercicio);
        return toDTO(ejercicio);
    }

    @Override
    public void eliminarEjercicio(int id) {
        ejercicioRepo.deleteById(id);
    }

    private EjercicioDTO toDTO(Ejercicio ejercicio) {
        // ...mapear entidad a DTO...
        return new EjercicioDTO();
    }
}

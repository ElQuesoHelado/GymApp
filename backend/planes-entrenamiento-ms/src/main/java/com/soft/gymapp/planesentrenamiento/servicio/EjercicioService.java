package com.soft.gymapp.planesentrenamiento.servicio;

import com.soft.gymapp.planesentrenamiento.dto.EjercicioDTO;
import java.util.List;

public interface EjercicioService {
    EjercicioDTO getEjercicioPorId(int id);
    List<EjercicioDTO> listarEjercicios();
    EjercicioDTO crearEjercicio(EjercicioDTO dto);
    void eliminarEjercicio(int id);
}

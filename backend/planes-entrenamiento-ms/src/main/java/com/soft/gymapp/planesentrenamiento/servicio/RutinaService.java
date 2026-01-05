package com.soft.gymapp.planesentrenamiento.servicio;

import com.soft.gymapp.planesentrenamiento.dto.RutinaDTO;
import java.util.List;

public interface RutinaService {
    RutinaDTO getRutinaPorId(int id);
    List<RutinaDTO> listarRutinas();
    RutinaDTO crearRutina(RutinaDTO dto);
    void eliminarRutina(int id);
}

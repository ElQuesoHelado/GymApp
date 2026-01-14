package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import java.util.List;

public interface RutinaService {
    Rutina crearRutina(RutinaDTO dto);
    List<Rutina> listarTodasLasRutinas();
    
    void eliminarRutina(Integer id);
    Rutina agregarEjercicio(Integer rutinaId, EjercicioDTO ejercicioDTO);
}
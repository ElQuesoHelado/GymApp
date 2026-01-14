package com.soft.gymapp.servicios.impl;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio; 
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.dominio.planesentrenamiento.RutinaRepositorio;
import com.soft.gymapp.servicios.RutinaService;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RutinaServiceImpl implements RutinaService {

    @Autowired
    private RutinaRepositorio rutinaRepositorio;

    @Override
    public Rutina crearRutina(RutinaDTO dto) {
        Rutina nuevaRutina = new Rutina(dto.nombre(), dto.objetivo());
        return rutinaRepositorio.save(nuevaRutina);
    }

    @Override
    public List<Rutina> listarTodasLasRutinas() {
        return rutinaRepositorio.findAll();
    }

    @Override
    public void eliminarRutina(Integer id) {
        rutinaRepositorio.deleteById(id);
    }

    @Override
    public Rutina agregarEjercicio(Integer rutinaId, EjercicioDTO dto) {
        Rutina rutina = rutinaRepositorio.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(dto.nombre());
        ejercicio.setDescripcion(dto.descripcion());
        ejercicio.setSeries(dto.series());
        ejercicio.setRepeticiones(dto.repeticiones());

        rutina.agregarEjercicio(ejercicio);

        return rutinaRepositorio.save(rutina);
    }
}
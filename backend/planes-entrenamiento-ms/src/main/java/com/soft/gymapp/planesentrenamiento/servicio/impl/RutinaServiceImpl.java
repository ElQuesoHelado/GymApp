package com.soft.gymapp.planesentrenamiento.servicio.impl;

import com.soft.gymapp.planesentrenamiento.dominio.Rutina;
import com.soft.gymapp.planesentrenamiento.dto.RutinaDTO;
import com.soft.gymapp.planesentrenamiento.repositorio.RutinaRepositorio;
import com.soft.gymapp.planesentrenamiento.servicio.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutinaServiceImpl implements RutinaService {
    @Autowired
    private RutinaRepositorio rutinaRepo;

    @Override
    public RutinaDTO getRutinaPorId(int id) {
        return rutinaRepo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public List<RutinaDTO> listarRutinas() {
        return rutinaRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RutinaDTO crearRutina(RutinaDTO dto) {
        Rutina rutina = new Rutina();
        // ...mapear campos desde dto...
        rutina = rutinaRepo.save(rutina);
        return toDTO(rutina);
    }

    @Override
    public void eliminarRutina(int id) {
        rutinaRepo.deleteById(id);
    }

    private RutinaDTO toDTO(Rutina rutina) {
        // ...mapear entidad a DTO...
        return new RutinaDTO();
    }
}

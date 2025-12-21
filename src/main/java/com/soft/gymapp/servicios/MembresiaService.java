package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
//import com.soft.gymapp.repositorio.;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembresiaService {

    private final MembresiaRepositorio membresiaRepositorio;

    public MembresiaService(MembresiaRepositorio membresiaRepository) {
        this.membresiaRepositorio= membresiaRepository;
    }

    @Transactional
    public Membresia marcarComoAdeudada(Integer membresiaId, double montoDeuda) {
        Membresia membresia = membresiaRepositorio.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        return membresiaRepositorio.save(membresia);
    }

    @Transactional
    public Membresia cancelarMembresia(Integer membresiaId) {
        Membresia membresia = membresiaRepositorio.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        membresia.setEstado(EstadoMembresia.CANCELADA);

        return membresiaRepositorio.save(membresia);
    }

    public List<Membresia> buscarPorEstado(EstadoMembresia estado) {
        return membresiaRepositorio.findByEstado(estado);
    }
}
package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
import com.soft.gymapp.dominio.usuarios.Usuario;
//import com.soft.gymapp.repositorio.;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembresiaService {

    @Autowired
    private final MembresiaRepositorio membresiaRepositorio;

    public MembresiaService(MembresiaRepositorio membresiaRepository) {
        this.membresiaRepositorio= membresiaRepository;
    }

    @Transactional
    public Membresia marcarComoAdeudada(Integer membresiaId, double montoDeuda) {
        Membresia membresia = membresiaRepositorio.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

//        membresia.setEstado(Estado);
//        membresia.setDeuda(montoDeuda);
//        membresia.setMetodoPago(null);

        return membresiaRepositorio.save(membresia);
    }

    @Transactional
    public Membresia cancelarMembresia(Integer membresiaId) {
        Membresia membresia = membresiaRepositorio.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        membresia.setEstado(EstadoMembresia.CANCELADA);
//        membresia.setDeuda(0.0);

        return membresiaRepositorio.save(membresia);
    }

    public List<Membresia> buscarPorEstado(EstadoMembresia estado) {
        return membresiaRepositorio.findByEstado(estado);
    }
}
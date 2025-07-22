package com.soft.gymapp.servicios;

public interface MembresiaService {

}

//G
package com.gymapp.service;

import com.gymapp.entity.Membresia;
import com.gymapp.entity.Usuario;
import com.gymapp.repository.MembresiaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;

    public MembresiaService(MembresiaRepository membresiaRepository) {
        this.membresiaRepository = membresiaRepository;
    }

    @Transactional
    public Membresia marcarComoAdeudada(Long membresiaId, double montoDeuda) {
        Membresia membresia = membresiaRepository.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        membresia.setEstado(Membresia.EstadoMembresia.ADEUDADA);
        membresia.setDeuda(montoDeuda);
        membresia.setMetodoPago(null); 

        return membresiaRepository.save(membresia);
    }

    @Transactional
    public Membresia cancelarMembresia(Long membresiaId) {
        Membresia membresia = membresiaRepository.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        membresia.setEstado(Membresia.EstadoMembresia.CANCELADA);
        membresia.setDeuda(0.0); 

        return membresiaRepository.save(membresia);
    }

    public List<Membresia> buscarPorEstado(Membresia.EstadoMembresia estado) {
        return membresiaRepository.findByEstado(estado);
    }
}
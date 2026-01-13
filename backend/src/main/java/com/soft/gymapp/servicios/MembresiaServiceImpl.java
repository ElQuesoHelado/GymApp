package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
import com.soft.gymapp.servicios.dto.MembresiaDTO;
import com.soft.gymapp.servicios.dto.PagoMembresiaDTO;
import com.soft.gymapp.servicios.dto.TipoMembresiaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembresiaServiceImpl implements MembresiaService {

    private final MembresiaRepositorio membresiaRepositorio;

    public MembresiaServiceImpl(MembresiaRepositorio membresiaRepositorio) {
        this.membresiaRepositorio = membresiaRepositorio;
    }

    private MembresiaDTO toDTO(Membresia m) {
        return new MembresiaDTO(
                m.getId(),
                m.getFechaInicio(),
                m.getFechaFin(),
                m.getEstado(),
                m.getDeuda(),
                m.estaActiva(),
                m.estaVencida(),
                new TipoMembresiaDTO(
                        m.getTipo().getNombre(),
                        m.getTipo().getDuracionDias(),
                        m.getTipo().getPrecio()
                ),
                m.getPagosMembresia().stream()
                        .map(p -> new PagoMembresiaDTO(
                                p.getId(),
                                p.getMonto(),
                                p.getFechaPago()
                        ))
                        .toList(),
                m.getCliente().getId()
        );
    }

    @Transactional
    public Membresia marcarComoAdeudada(Integer membresiaId, double montoDeuda) {
        Membresia membresia = membresiaRepositorio.findById(membresiaId)
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));

        membresia.setEstado(EstadoMembresia.ADEUDADA);
        membresia.setDeuda(montoDeuda);

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
package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.servicios.dto.MembresiaDTO;

import java.util.List;
import java.util.Optional;


public interface MembresiaService {
    Optional<MembresiaDTO> obtenerPorId(Integer idMembresia);

    Optional<MembresiaDTO> obtenerPorCliente();

    Membresia marcarComoAdeudada(Integer membresiaId, double montoDeuda);

    Membresia cancelarMembresia(Integer membresiaId);

    List<MembresiaDTO> buscarPorEstado(EstadoMembresia estado);
}
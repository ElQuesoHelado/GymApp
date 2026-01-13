package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.membresias.MembresiaRepositorio;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MembresiaService {
    Membresia marcarComoAdeudada(Integer membresiaId, double montoDeuda);
    Membresia cancelarMembresia(Integer membresiaId);
    List<Membresia> buscarPorEstado(EstadoMembresia estado);
}
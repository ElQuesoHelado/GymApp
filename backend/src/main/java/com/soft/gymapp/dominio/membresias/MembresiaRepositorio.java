package com.soft.gymapp.dominio.membresias;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

    List<Membresia> findByClienteId(Integer clienteId);

    List<Membresia> findByTipoNombreContainingIgnoreCase(String nombre);

    List<Membresia> findByTipoPrecioLessThan(float precioMaximo);

    List<Membresia> findByTipoDuracionDiasGreaterThanEqual(int diasMinimos);

    List<Membresia> findByEstado(EstadoMembresia estado);
}
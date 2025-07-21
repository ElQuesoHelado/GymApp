package com.soft.gymapp.dominio.membresias;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

    List<Membresia> findByNombreContainingIgnoreCase(String nombre);

    List<Membresia> findByPrecioLessThan(float precioMaximo);

    List<Membresia> findByDuracionDiasGreaterThanEqual(int diasMinimos);
}
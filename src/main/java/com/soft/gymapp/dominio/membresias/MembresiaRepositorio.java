package com.soft.gymapp.dominio.membresias;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {

    List<Membresia> findByTipoNombreContainingIgnoreCase(String nombre);

    List<Membresia> findByTipoPrecioLessThan(float precioMaximo);

    List<Membresia> findByTipoDuracionDiasGreaterThanEqual(int diasMinimos);
}

//G
package com.gymapp.repository;

import com.gymapp.entity.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {
    List<Membresia> findByEstado(Membresia.EstadoMembresia estado);
}
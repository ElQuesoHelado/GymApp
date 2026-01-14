package com.soft.gymapp.dominio.sesiones;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.servicios.dto.SalaDTO;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;

class SalaTest {

  @Test
  void toDTODeberiaConvertirCorrectamente() {
    Sala sala = new Sala();
    sala.setId(1);
    sala.setNombre("Sala Crossfit");
    sala.setCapacidad(20);

    SalaDTO dto = sala.toDTO();

    assertNotNull(dto);
    assertEquals(1, dto.id());
    assertEquals("Sala Crossfit", dto.nombre());
    assertEquals(20, dto.capacidad());
  }

  @Test
  void verDisponibilidadActualmenteSiempreFalse() {
    Sala sala = new Sala();

    boolean disponible = sala.verDisponibilidad(new Date());

    assertFalse(disponible);
  }

  @Test
  void gettersYSettersDeberianFuncionarCorrectamente() {
    Sala sala = new Sala();

    sala.setId(5);
    sala.setNombre("Sala Yoga");
    sala.setCapacidad(15);
    sala.setSesiones(List.of());

    assertEquals(5, sala.getId());
    assertEquals("Sala Yoga", sala.getNombre());
    assertEquals(15, sala.getCapacidad());
    assertNotNull(sala.getSesiones());
    assertTrue(sala.getSesiones().isEmpty());
  }
}

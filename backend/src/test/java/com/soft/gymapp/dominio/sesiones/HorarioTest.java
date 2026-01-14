package com.soft.gymapp.dominio.sesiones;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.servicios.dto.HorarioDTO;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class HorarioTest {

  @Test
  void horarioFuturoDeberiaEstarDisponible() {
    Horario horario = new Horario();
    horario.setFecha(LocalDate.now().plusDays(1));
    horario.setHoraInicio(LocalTime.of(10, 0));
    horario.setHoraFin(LocalTime.of(11, 0));

    assertTrue(horario.esDisponible());
  }

  @Test
  void horarioHoyPeroNoTerminaDeberiaEstarDisponible() {
    Horario horario = new Horario();
    horario.setFecha(LocalDate.now());
    horario.setHoraInicio(LocalTime.now().minusMinutes(10));
    horario.setHoraFin(LocalTime.now().plusMinutes(30));

    assertTrue(horario.esDisponible());
  }

  @Test
  void horarioPasadoDeberiaNoEstarDisponible() {
    Horario horario = new Horario();
    horario.setFecha(LocalDate.now());
    horario.setHoraInicio(LocalTime.now().minusHours(2));
    horario.setHoraFin(LocalTime.now().minusHours(1));

    assertFalse(horario.esDisponible());
  }

  @Test
  void horaInicioMayorQueHoraFinDeberiaNoEstarDisponible() {
    Horario horario = new Horario();
    horario.setFecha(LocalDate.now().plusDays(1));
    horario.setHoraInicio(LocalTime.of(15, 0));
    horario.setHoraFin(LocalTime.of(10, 0));

    assertFalse(horario.esDisponible());
  }

  @Test
  void horarioInvalidoConCamposNulosDeberiaLanzarExcepcion() {
    Horario horario = new Horario();

    IllegalStateException exception =
        assertThrows(IllegalStateException.class, horario::esDisponible);

    assertEquals("Horario invalido", exception.getMessage());
  }

  @Test
  void toDTODeberiaConvertirCorrectamente() {
    LocalDate fecha = LocalDate.of(2026, 1, 20);
    LocalTime inicio = LocalTime.of(8, 0);
    LocalTime fin = LocalTime.of(9, 30);

    Horario horario = new Horario();
    horario.setFecha(fecha);
    horario.setHoraInicio(inicio);
    horario.setHoraFin(fin);

    HorarioDTO dto = horario.toDTO();

    assertNotNull(dto);
    assertEquals(fecha, dto.fecha());
    assertEquals("08:00", dto.horaInicio());
    assertEquals("09:30", dto.horaFin());
  }
}

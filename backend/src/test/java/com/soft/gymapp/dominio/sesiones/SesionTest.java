package com.soft.gymapp.dominio.sesiones;

import static org.junit.jupiter.api.Assertions.*;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import com.soft.gymapp.servicios.dto.SesionDTO;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class SesionTest {

  // 🔧 helper para setear campos privados (porque no hay setters)
  private void setField(Object target, String fieldName, Object value) {
    try {
      Field field = target.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(target, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void confirmarDeberiaCambiarEstadoAEnProgreso() {
    Sesion sesion = new Sesion();
    setField(sesion, "estado", EstadoSesion.SIN_EMPEZAR);

    sesion.confirmar();

    assertEquals(EstadoSesion.EN_PROGRESO, sesion.getEstado());
  }

  @Test
  void cancelarDeberiaCambiarEstadoATerminada() {
    Sesion sesion = new Sesion();
    setField(sesion, "estado", EstadoSesion.EN_PROGRESO);

    sesion.cancelar();

    assertEquals(EstadoSesion.TERMINADA, sesion.getEstado());
  }

  @Test
  void reprogramarDeberiaActualizarHorarioYEstado() {
    Sesion sesion = new Sesion();
    setField(sesion, "estado", EstadoSesion.EN_PROGRESO);

    Horario nuevoHorario = new Horario();
    nuevoHorario.setFecha(LocalDate.now().plusDays(1));
    nuevoHorario.setHoraInicio(LocalTime.of(10, 0));
    nuevoHorario.setHoraFin(LocalTime.of(11, 0));

    sesion.reprogramar(nuevoHorario);

    assertEquals(EstadoSesion.SIN_EMPEZAR, sesion.getEstado());
    assertEquals(nuevoHorario, sesion.getHorario());
  }

  @Test
  void agregarClienteDeberiaAgregarClienteSinDuplicados() {
    Sesion sesion = new Sesion();
    Cliente cliente = new Cliente();

    sesion.agregarCliente(cliente);
    sesion.agregarCliente(cliente); // duplicado

    assertEquals(1, sesion.getClientes().size());
    assertTrue(sesion.getClientes().contains(cliente));
  }

  @Test
  void toDTODeberiaConvertirSesionCompleta() {
    Sesion sesion = new Sesion();

    // estado
    setField(sesion, "estado", EstadoSesion.SIN_EMPEZAR);
    setField(sesion, "id", 10);

    // horario
    Horario horario = new Horario();
    horario.setFecha(LocalDate.now().plusDays(1));
    horario.setHoraInicio(LocalTime.of(8, 0));
    horario.setHoraFin(LocalTime.of(9, 0));
    setField(sesion, "horario", horario);

    // sala
    Sala sala = new Sala();
    sala.setId(1);
    sala.setNombre("Sala Funcional");
    sala.setCapacidad(20);
    setField(sesion, "sala", sala);

    // entrenador
    Entrenador entrenador = new Entrenador();
    entrenador.setNombre("Carlos");
    setField(sesion, "entrenador", entrenador);

    // cliente
    Cliente cliente = new Cliente();
    cliente.setNombre("Juan");
    sesion.agregarCliente(cliente);

    SesionDTO dto = sesion.toDTO();

    assertNotNull(dto);
    assertEquals(10, dto.getId());
    assertEquals("SIN_EMPEZAR", dto.getEstado());
    assertEquals(1, dto.getClientes().size());
    assertNotNull(dto.getHorario());
    assertNotNull(dto.getSala());
    assertNotNull(dto.getEntrenador());
  }

  @Test
  void toDTOSinRelacionesNoDeberiaFallar() {
    Sesion sesion = new Sesion();
    setField(sesion, "estado", EstadoSesion.SIN_EMPEZAR);
    setField(sesion, "id", 1);

    SesionDTO dto = sesion.toDTO();

    assertNotNull(dto);
    assertEquals("SIN_EMPEZAR", dto.getEstado());
    assertTrue(dto.getClientes().isEmpty());
    assertNull(dto.getEntrenador());
    assertNull(dto.getHorario());
    assertNull(dto.getSala());
  }
}

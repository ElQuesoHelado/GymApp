package com.soft.gymapp.dominio.usuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.dto.ClienteDTO;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteTest {

  private Cliente cliente;

  @BeforeEach
  void setUp() {
    cliente = new Cliente();
    cliente.setId(1);
    cliente.setNombre("Misael");
    cliente.setEmail("misael@test.com");
    cliente.setDni("12345678");
    cliente.setTelefono("999999999");
    cliente.setFechaNacimiento(LocalDate.of(2000, 1, 1));
    cliente.setObjetivo("Bajar peso");
    cliente.setNivel("Intermedio");
  }

  // ======================
  // toDTO
  // ======================
  @Test
  void toDTOdeberiaConvertirCorrectamente() {
    ClienteDTO dto = cliente.toDTO();

    assertEquals(1, dto.id());
    assertEquals("Misael", dto.nombre());
    assertEquals("12345678", dto.dni());
    assertEquals("misael@test.com", dto.email());
    assertEquals("999999999", dto.telefono());
    assertEquals(LocalDate.of(2000, 1, 1), dto.fechaNacimiento());
    assertEquals("Bajar peso", dto.objetivo());
    assertEquals("Intermedio", dto.nivel());
  }

  // ======================
  // Getters / Setters
  // ======================
  @Test
  void settersYGettersdeberianFuncionar() {
    Membresia membresia = mock(Membresia.class);
    PlanEntrenamiento plan = mock(PlanEntrenamiento.class);

    cliente.setMembresia(membresia);
    cliente.setPlanEntrenamiento(plan);

    assertEquals(membresia, cliente.getMembresia());
    assertEquals(plan, cliente.getPlanEntrenamiento());
  }

  // ======================
  // agregarSesion
  // ======================
  @Test
  void agregarSesiondeberiaAgregarUnaVez() {
    Sesion sesion = mock(Sesion.class);

    cliente.agregarSesion(sesion);
    cliente.agregarSesion(sesion); // duplicado

    assertEquals(1, cliente.getSesiones().size());
    assertTrue(cliente.getSesiones().contains(sesion));
  }

  @Test
  void agregarSesionconNullNoHaceNada() {
    cliente.agregarSesion(null);
    assertTrue(cliente.getSesiones().isEmpty());
  }

  // ======================
  // eliminarSesion
  // ======================
  @Test
  void eliminarSesiondeberiaEliminarCorrectamente() {
    Sesion sesion = mock(Sesion.class);
    cliente.agregarSesion(sesion);

    cliente.eliminarSesion(sesion);

    assertTrue(cliente.getSesiones().isEmpty());
  }

  // ======================
  // reservarCita
  // ======================
  @Test
  void reservarCitadeberiaAgregarSesionYRelacionBidireccional() {
    Sesion sesion = mock(Sesion.class);

    cliente.reservarCita(sesion);

    assertEquals(1, cliente.getSesiones().size());
    verify(sesion).agregarCliente(cliente);
  }

  @Test
  void reservarCitaconNullNoHaceNada() {
    cliente.reservarCita(null);
    assertTrue(cliente.getSesiones().isEmpty());
  }

  // ======================
  // verRutina
  // ======================
  @Test
  void verRutinaconPlanEjecutaMetodo() {
    PlanEntrenamiento plan = mock(PlanEntrenamiento.class);
    cliente.setPlanEntrenamiento(plan);

    cliente.verRutina();

    verify(plan).verRutinas();
  }

  @Test
  void verRutinasinPlanNoLanzaExcepcion() {
    cliente.setPlanEntrenamiento(null);

    assertDoesNotThrow(() -> cliente.verRutina());
  }

  // ======================
  // equals / hashCode
  // ======================
  @Test
  void equalsdeberiaCompararPorId() {
    Cliente otro = new Cliente();
    otro.setId(1);

    assertEquals(cliente, otro);
    assertEquals(cliente.hashCode(), otro.hashCode());
  }

  @Test
  void equalsconIdDiferenteDebeSerFalse() {
    Cliente otro = new Cliente();
    otro.setId(2);

    assertNotEquals(cliente, otro);
  }

  @Test
  void equalsconObjetoDiferenteDebeSerFalse() {
    assertNotEquals(cliente, new Object());
  }
}

package com.soft.gymapp.dominio.usuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.dominio.sesiones.Sesion;
import com.soft.gymapp.servicios.dto.EntrenadorDTO;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrenadorTest {

  private Entrenador entrenador;

  @BeforeEach
  void setUp() {
    entrenador = new Entrenador();
    entrenador.setId(1);
    entrenador.setNombre("Misael");
    entrenador.setDni("12345678");
    entrenador.setEmail("misael@test.com");
    entrenador.setTelefono("999999999");
    entrenador.setFechaNacimiento(LocalDate.of(2000, 1, 1));
    entrenador.setEspecialidad("Fuerza");
    entrenador.setCertificaciones("Certificado A");
  }

  // =========================
  // toDTO
  // =========================
  @Test
  void toDTOdeberiaMapearTodosLosCampos() {
    EntrenadorDTO dto = entrenador.toDTO();

    assertEquals(1, dto.getId());
    assertEquals("Misael", dto.getNombre());
    assertEquals("12345678", dto.getDni());
    assertEquals("misael@test.com", dto.getEmail());
    assertEquals("999999999", dto.getTelefono());
    assertEquals(LocalDate.of(2000, 1, 1), dto.getFechaNacimiento());
    assertEquals("Fuerza", dto.getEspecialidad());
    assertEquals("Certificado A", dto.getCertificaciones());
  }

  // =========================
  // crearRutina
  // =========================
  @Test
  void crearRutinadeberiaAsignarRutinaAlPlan() {
    PlanEntrenamiento plan = mock(PlanEntrenamiento.class);

    entrenador.crearRutina("Rutina 1", "Ganar fuerza", plan);

    verify(plan, times(1)).asignarRutina(any(Rutina.class));
  }

  @Test
  void crearRutinaconNombreVacioNoDebeAsignarRutina() {
    PlanEntrenamiento plan = mock(PlanEntrenamiento.class);

    entrenador.crearRutina("", "Objetivo", plan);

    verify(plan, never()).asignarRutina(any());
  }

  @Test
  void crearRutinaconObjetivoVacioNoDebeAsignarRutina() {
    PlanEntrenamiento plan = mock(PlanEntrenamiento.class);

    entrenador.crearRutina("Rutina", "", plan);

    verify(plan, never()).asignarRutina(any());
  }

  @Test
  void crearRutinaconPlanNuloNoDebeLanzarExcepcion() {
    assertDoesNotThrow(
        () -> entrenador.crearRutina("Rutina", "Objetivo", null));
  }

  // =========================
  // verClientes
  // =========================
  @Test
  void verClientesSinSesionesNoDebeLanzarExcepcion() {
    entrenador.setSesiones(List.of());

    assertDoesNotThrow(() -> entrenador.verClientes());
  }

  @Test
  void verClientesConClientesNoDebeLanzarExcepcion() {
    Cliente cliente = new Cliente();
    cliente.setNombre("Cliente 1");
    cliente.setDni("87654321");

    Sesion sesion = mock(Sesion.class);
    when(sesion.getClientes()).thenReturn(List.of(cliente));

    entrenador.setSesiones(List.of(sesion));

    assertDoesNotThrow(() -> entrenador.verClientes());
  }

  // =========================
  // getters / setters
  // =========================
  @Test
  void gettersYSettersFuncionanCorrectamente() {
    entrenador.setEspecialidad("Cardio");
    entrenador.setCertificaciones("Cert B");

    assertEquals("Cardio", entrenador.getEspecialidad());
    assertEquals("Cert B", entrenador.getCertificaciones());
  }
}

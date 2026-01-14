package com.soft.gymapp.dominio.planesentrenamiento;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.soft.gymapp.dominio.usuarios.Cliente;
import com.soft.gymapp.dominio.usuarios.Entrenador;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanEntrenamientoTest {

  private PlanEntrenamiento plan;
  private Rutina rutina;

  @BeforeEach
  void setUp() {
    plan = new PlanEntrenamiento(1, LocalDate.now(), 8, new Cliente(),
                                 new Entrenador());

    rutina = mock(Rutina.class);
    when(rutina.getId()).thenReturn(10);
    when(rutina.getNombre()).thenReturn("Rutina Fuerza");
  }

  @Test
  void asignarRutinavalidadeberiaAgregarRutina() {
    plan.asignarRutina(rutina);

    assertEquals(1, plan.getRutinas().size());
    assertEquals("Rutina Fuerza", plan.getRutinas().get(0).getNombre());
  }

  @Test
  void asignarRutinanulanoDeberiaAgregar() {
    plan.asignarRutina(null);

    assertTrue(plan.getRutinas().isEmpty());
  }

  @Test
  void modificarRutinaexistentedeberiaActualizarRutina() {
    plan.asignarRutina(rutina);

    Rutina rutinaModificada = mock(Rutina.class);
    when(rutinaModificada.getId()).thenReturn(10);
    when(rutinaModificada.getNombre()).thenReturn("Rutina Cardio");

    plan.modificarRutina(rutinaModificada);

    assertEquals(1, plan.getRutinas().size());
    assertEquals("Rutina Cardio", plan.getRutinas().get(0).getNombre());
  }

  @Test
  void modificarRutinanoExistentenoDeberiaCambiarNada() {
    Rutina rutinaNueva = mock(Rutina.class);
    when(rutinaNueva.getId()).thenReturn(99);

    plan.modificarRutina(rutinaNueva);

    assertTrue(plan.getRutinas().isEmpty());
  }

  @Test
  void modificarRutinainvalidanoDeberiaCambiarNada() {
    Rutina rutinaInvalida = mock(Rutina.class);
    when(rutinaInvalida.getId()).thenReturn(0);

    plan.modificarRutina(rutinaInvalida);

    assertTrue(plan.getRutinas().isEmpty());
  }

  @Test
  void setRutinasnuladeberiaInicializarListaVacia() {
    plan.setRutinas(null);

    assertNotNull(plan.getRutinas());
    assertTrue(plan.getRutinas().isEmpty());
  }
}

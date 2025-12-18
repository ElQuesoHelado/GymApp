package com.soft.gymapp.servicios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.soft.gymapp.dominio.planesentrenamiento.Ejercicio;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamiento;
import com.soft.gymapp.dominio.planesentrenamiento.PlanEntrenamientoRepositorio;
import com.soft.gymapp.dominio.planesentrenamiento.Rutina;
import com.soft.gymapp.servicios.dto.EjercicioDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import com.soft.gymapp.servicios.dto.RutinaDTO;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlanEntrenamientoServiceImplTest {

  @Mock private PlanEntrenamientoRepositorio planEntrenamientoRepositorio;

  @InjectMocks private PlanEntrenamientoServiceImpl planEntrenamientoService;

  private PlanEntrenamiento planEntrenamiento;
  private static final String NOMBRE_RUTINA = "Rutina Pecho";
  private static final String NOMBRE_EJERCICIO = "Press de banca";
  private static final int DURACION_SEMANAS = 8;
  private static final int REPETICIONES = 10;
  private static final int SERIES = 3;

  @BeforeEach
  void setUp() {
    // Crear ejercicio con datos reales
    Ejercicio ejercicio = new Ejercicio();
    ejercicio.setNombre(NOMBRE_EJERCICIO);
    ejercicio.setDescripcion("Ejercicio para pectoral");
    ejercicio.setRepeticiones(REPETICIONES);
    ejercicio.setSeries(SERIES);

    // Crear rutina
    Rutina rutina = new Rutina();
    rutina.setNombre(NOMBRE_RUTINA);
    rutina.setObjetivo("Desarrollo pectoral");
    rutina.setEjercicios(Arrays.asList(ejercicio));

    // Crear plan de entrenamiento
    planEntrenamiento = new PlanEntrenamiento();
    planEntrenamiento.setFechaInicio(LocalDate.now());
    planEntrenamiento.setDuracionSemanas(DURACION_SEMANAS);
    planEntrenamiento.setRutinas(Arrays.asList(rutina));

    // NOTA: No establecemos IDs porque probablemente se auto-generan
    // o no tienen setters. Los IDs en JPA suelen ser Long, no Integer.
  }

  @Test
  @DisplayName("Debería retornar plan cuando el ID del cliente existe")
  void getPlanEntrenamientoPorClienteIdPlanExistente() {
    Integer clienteId = 1;
    when(planEntrenamientoRepositorio.findByClienteId(clienteId))
        .thenReturn(Optional.of(planEntrenamiento));

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(clienteId);

    assertNotNull(resultado, "El resultado no debería ser nul");
    assertEquals(DURACION_SEMANAS, resultado.getDuracionSemanas(),
                 "La duración en semanas debería coincidir");
    assertNotNull(resultado.getRutinas(),
                  "La lista de rutinas no debería ser nula");
    assertEquals(1, resultado.getRutinas().size(),
                 "Debería tener exactamente 1 rutina");
    assertEquals(NOMBRE_RUTINA, resultado.getRutinas().get(0).getNombre(),
                 "El nombre de la rutina debería coincidir");

    // Verificar ejercicios dentro de la rutina
    List<EjercicioDTO> ejercicios =
        resultado.getRutinas().get(0).getEjercicios();
    assertNotNull(ejercicios, "La lista de ejercicios no debería ser nula");
    assertEquals(1, ejercicios.size(), "Debería tener exactamente 1 ejercicio");
    assertEquals(NOMBRE_EJERCICIO, ejercicios.get(0).getNombre(),
                 "El nombre del ejercicio debería coincidir");
    assertEquals(REPETICIONES, ejercicios.get(0).getRepeticiones(),
                 "Las repeticiones deberían coincidir");
    assertEquals(SERIES, ejercicios.get(0).getSeries(),
                 "Las series deberían coincidir");
  }

  @Test
  @DisplayName("Debería retornar null cuando el plan no existe")
  void getPlanEntrenamientoPorClienteIdPlanNoExistente() {
    Integer clienteId = 999;
    when(planEntrenamientoRepositorio.findByClienteId(clienteId))
        .thenReturn(Optional.empty());

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(clienteId);

    assertNull(resultado, "Debería retornar null cuando no existe el plan");
  }

  @Test
  @DisplayName("Debería lanzar excepción cuando el ID es nulo")
  void getPlanEntrenamientoPorClienteIdIdNulo() {
    Integer clienteId = null;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        ()
            -> planEntrenamientoService.getPlanEntrenamientoPorClienteId(
                clienteId),
        "Debería lanzar IllegalArgumentException cuando el ID es nulo");

    assertEquals("El ID del cliente no puede ser nulo", exception.getMessage(),
                 "El mensaje de error debería coincidi");
  }

  @Test
  @DisplayName("Debería lanzar excepción cuando el ID es negativo")
  void getPlanEntrenamientoPorClienteIdIdNegativo() {
    Integer clienteId = -1;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        ()
            -> planEntrenamientoService.getPlanEntrenamientoPorClienteId(
                clienteId),
        "Debería lanzar IllegalArgumentException cuando el ID es negativo");

    assertEquals("El ID del cliente debe ser positivo", exception.getMessage(),
                 "El mensaje de error debería coincidir");
  }

  @Test
  @DisplayName("Debería lanzar excepción cuando el ID es cero")
  void getPlanEntrenamientoPorClienteIdIdCero() {
    Integer clienteId = 0;

    IllegalArgumentException exception = assertThrows(
        IllegalArgumentException.class,
        ()
            -> planEntrenamientoService.getPlanEntrenamientoPorClienteId(
                clienteId),
        "Debería lanzar IllegalArgumentException cuando el ID es cero");

    assertEquals("El ID del cliente debe ser positivo", exception.getMessage(),
                 "El mensaje de error debería coincidir");
  }

  @Test
  @DisplayName("Debería convertir correctamente un plan con todas sus rutinas")
  void convertirPlanADTOPlanCompleto() {
    when(planEntrenamientoRepositorio.findByClienteId(1))
        .thenReturn(Optional.of(planEntrenamiento));

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(1);

    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertNotNull(resultado.getRutinas(), "Las rutinas no deberían ser nulas");
    assertFalse(resultado.getRutinas().isEmpty(),
                "La lista de rutinas no debería estar vacía");
    assertEquals(DURACION_SEMANAS, resultado.getDuracionSemanas(),
                 "La duración en semanas debería coincidir");
  }

  @Test
  @DisplayName("Debería manejar correctamente un plan sin rutinas (null)")
  void convertirPlanADTOPlanSinRutinas() {
    PlanEntrenamiento planSinRutinas = new PlanEntrenamiento();
    planSinRutinas.setFechaInicio(LocalDate.now());
    planSinRutinas.setDuracionSemanas(4);
    // No establecer rutinas, dejar null por defecto

    when(planEntrenamientoRepositorio.findByClienteId(2))
        .thenReturn(Optional.of(planSinRutinas));

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(2);

    assertNotNull(resultado,
                  "El resultado no debería ser nulo incluso sin rutinas");
    assertNotNull(resultado.getRutinas(),
                  "La lista de rutinas debería ser una lista vacía, no nula");
    assertTrue(resultado.getRutinas().isEmpty(),
               "La lista de rutinas debería estar vacía cuando no hay rutinas");
  }

  @Test
  @DisplayName(
      "Debería manejar correctamente un plan con lista de rutinas vacía")
  void
  convertirPlanADTOPlanConRutinasVacias() {
    PlanEntrenamiento planConRutinasVacias = new PlanEntrenamiento();
    planConRutinasVacias.setFechaInicio(LocalDate.now());
    planConRutinasVacias.setDuracionSemanas(6);
    planConRutinasVacias.setRutinas(List.of()); // Lista vacía explícita

    when(planEntrenamientoRepositorio.findByClienteId(3))
        .thenReturn(Optional.of(planConRutinasVacias));

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(3);

    assertNotNull(resultado, "El resultado no debería ser nulo");
    assertNotNull(resultado.getRutinas(), "Las rutinas no deberían ser nulas");
    assertTrue(resultado.getRutinas().isEmpty(),
               "La lista de rutinas debería estar vacía");
  }

  @Test
  @DisplayName("Debería convertir ejercicio con todos sus campos")
  void convertirEjercicioADTOCompleto() {
    // Crear ejercicio con todos los campos
    Ejercicio ejercicioCompleto = new Ejercicio();
    ejercicioCompleto.setNombre("Sentadilla");
    ejercicioCompleto.setDescripcion("Ejercicio para piernas");
    ejercicioCompleto.setRepeticiones(12);
    ejercicioCompleto.setSeries(4);

    Rutina rutinaSimple = new Rutina();
    rutinaSimple.setNombre("Rutina Piernas");
    rutinaSimple.setObjetivo("Desarrollo de piernas");
    rutinaSimple.setEjercicios(Arrays.asList(ejercicioCompleto));

    PlanEntrenamiento planSimple = new PlanEntrenamiento();
    planSimple.setFechaInicio(LocalDate.now());
    planSimple.setDuracionSemanas(6);
    planSimple.setRutinas(Arrays.asList(rutinaSimple));

    when(planEntrenamientoRepositorio.findByClienteId(4))
        .thenReturn(Optional.of(planSimple));

    PlanEntrenamientoDTO resultado =
        planEntrenamientoService.getPlanEntrenamientoPorClienteId(4);

    // Verificar que todos los campos del ejercicio se convirtieron
    // correctamente
    EjercicioDTO ejercicioDTO =
        resultado.getRutinas().get(0).getEjercicios().get(0);
    assertEquals("Sentadilla", ejercicioDTO.getNombre());
    assertEquals("Ejercicio para piernas", ejercicioDTO.getDescripcion());
    assertEquals(12, ejercicioDTO.getRepeticiones());
    assertEquals(4, ejercicioDTO.getSeries());
  }
}
package com.soft.gymapp.presentation.controladores;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.ClienteDTO;
import com.soft.gymapp.servicios.dto.MembresiaDTO;
import com.soft.gymapp.servicios.dto.PlanEntrenamientoDTO;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UsuarioService usuarioService;

  @MockBean private PlanEntrenamientoService planEntrenamientoService;

  // =========================
  // /api/cliente/home
  // =========================
  @Test
  @WithMockUser(roles = {"CLIENTE"})
  void homedeberiaRetornarClienteDTO() throws Exception {

    ClienteDTO clienteDTO =
        new ClienteDTO(1, "Misael", "12345678", "misael@test.com", "999999999",
                       LocalDate.of(2000, 1, 1), "Ganar músculo", "Intermedio");

    when(usuarioService.obtenerClienteLogueado()).thenReturn(clienteDTO);

    mockMvc.perform(get("/api/cliente/home"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Misael"))
        .andExpect(jsonPath("$.dni").value("12345678"))
        .andExpect(jsonPath("$.email").value("misael@test.com"))
        .andExpect(jsonPath("$.telefono").value("999999999"))
        .andExpect(jsonPath("$.objetivo").value("Ganar músculo"))
        .andExpect(jsonPath("$.nivel").value("Intermedio"));
  }

  // =========================
  // /api/cliente/plan
  // =========================
  @Test
  @WithMockUser(roles = {"CLIENTE"})
  void plandeberiaRetornarPlanEntrenamientoDTO() throws Exception {

    PlanEntrenamientoDTO planDTO = new PlanEntrenamientoDTO(
        1, LocalDate.of(2025, 1, 1), 12, 1, 2, List.of());

    when(planEntrenamientoService.getPlanEntrenamientoPorClienteId())
        .thenReturn(planDTO);

    mockMvc.perform(get("/api/cliente/plan"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.duracionSemanas").value(12))
        .andExpect(jsonPath("$.clienteId").value(1))
        .andExpect(jsonPath("$.rutinas").isArray());
  }

  // =========================
  // /api/cliente/membresia
  // =========================
  @Test
  @WithMockUser(roles = {"CLIENTE"})
  void membresiadeberiaRetornarMembresiaDTO() throws Exception {

    MembresiaDTO membresiaDTO =
        new MembresiaDTO(1, new Date(), new Date(), EstadoMembresia.ACTIVADA,
                         0.0, true, false, null, List.of(), 1);

    when(usuarioService.obtenerMembresia()).thenReturn(membresiaDTO);

    mockMvc.perform(get("/api/cliente/membresia"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.activa").value(true))
        .andExpect(jsonPath("$.vencida").value(false))
        .andExpect(jsonPath("$.deuda").value(0.0));
  }

  // =========================
  // /api/cliente/sesiones
  // =========================
  @Test
  @WithMockUser(roles = {"CLIENTE"})
  void sesionesdeberiaRetornarString() throws Exception {

    mockMvc.perform(get("/api/cliente/sesiones"))
        .andExpect(status().isOk())
        .andExpect(content().string("cliente/sesiones"));
  }
}

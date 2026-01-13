package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;
import com.soft.gymapp.servicios.PlanEntrenamientoService;
import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private PlanEntrenamientoService planEntrenamientoService;

    // =========================
    // /api/cliente/home
    // =========================
    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void homedeberiaRetornarUsuarioDTO() throws Exception {

        UsuarioDTO usuarioDTO = new UsuarioDTO(
                1,
                "Misael",
                "misael@test.com",
                "12345678",
                "CLIENTE"
        );

        when(usuarioService.obtenerUsuarioLogueado())
                .thenReturn(usuarioDTO);

        mockMvc.perform(get("/api/cliente/home"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Misael"))
                .andExpect(jsonPath("$.email").value("misael@test.com"))
                .andExpect(jsonPath("$.tipo").value("CLIENTE"));
    }


    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void plandeberiaRetornarPlanEntrenamientoDTO() throws Exception {

        PlanEntrenamientoDTO planDTO = new PlanEntrenamientoDTO(
                1,
                LocalDate.of(2025, 1, 1),
                12,
                1,
                2,
                List.of()
        );

        when(planEntrenamientoService.getPlanEntrenamientoPorClienteId())
                .thenReturn(planDTO);

        mockMvc.perform(get("/api/cliente/plan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.duracionSemanas").value(12))
                .andExpect(jsonPath("$.clienteId").value(1))
                .andExpect(jsonPath("$.rutinas").isArray());
    }

    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void membresiadeberiaRetornarMembresiaDTO() throws Exception {

        MembresiaDTO membresiaDTO = new MembresiaDTO(
                1,
                new Date(),
                new Date(),
                EstadoMembresia.ACTIVADA,
                0.0,
                true,
                false,
                null,
                List.of(),
                1
        );

        when(usuarioService.obtenerMembresia())
                .thenReturn(membresiaDTO);

        mockMvc.perform(get("/api/cliente/membresia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activa").value(true))
                .andExpect(jsonPath("$.vencida").value(false))
                .andExpect(jsonPath("$.deuda").value(0.0));
    }


    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void sesionesdeberiaRetornarString() throws Exception {

        mockMvc.perform(get("/api/cliente/sesiones"))
                .andExpect(status().isOk())
                .andExpect(content().string("cliente/sesiones"));
    }
}

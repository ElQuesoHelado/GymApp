package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    // 🔐 Endpoint protegido por ROLE_CLIENTE
    @Test
    @WithMockUser(username = "cliente@test.com", roles = {"CLIENTE"})
    void home_DeberiaRetornarUsuarioDTO() throws Exception {

        // Crear UsuarioDTO usando el constructor del record
        UsuarioDTO dto = new UsuarioDTO(1, "Misael", "misael@test.com", "password123", "CLIENTE");

        when(usuarioService.obtenerUsuarioLogueado(any()))
                .thenReturn(dto);

        mockMvc.perform(get("/api/cliente/home"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Misael"))
                .andExpect(jsonPath("$.email").value("misael@test.com"));
    }

    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void plan_DeberiaRetornarVistaPlan() throws Exception {
        mockMvc.perform(get("/api/cliente/plan"))
                .andExpect(status().isOk())
                .andExpect(content().string("cliente/plan"));
    }

    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void membresia_DeberiaRetornarVistaMembresia() throws Exception {
        mockMvc.perform(get("/api/cliente/membresia"))
                .andExpect(status().isOk())
                .andExpect(content().string("cliente/membresia"));
    }

    @Test
    @WithMockUser(roles = {"CLIENTE"})
    void sesiones_DeberiaRetornarVistaSesiones() throws Exception {
        mockMvc.perform(get("/api/cliente/sesiones"))
                .andExpect(status().isOk())
                .andExpect(content().string("cliente/sesiones"));
    }
}

package com.soft.gymapp.presentation.controladores;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UsuarioService usuarioService;

  // =========================
  // GET /api/auth/me
  // =========================
  @Test
  @WithMockUser(username = "misael@test.com")
  void medebriaRetornarUsuarioDTO() throws Exception {

    UsuarioDTO usuarioDTO =
        new UsuarioDTO(1, "Misael", "misael@test.com", "12345678", "CLIENTE");

    when(usuarioService.obtenerUsuarioLogueado()).thenReturn(usuarioDTO);

    mockMvc.perform(get("/api/auth/me"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Misael"))
        .andExpect(jsonPath("$.email").value("misael@test.com"))
        .andExpect(jsonPath("$.dni").value("12345678"))
        .andExpect(jsonPath("$.tipo").value("CLIENTE"));
  }

  // =========================
  // POST /api/auth/logout
  // =========================
  @Test
  @WithMockUser
  void logoutdeberiaRetornarOk() throws Exception {

    mockMvc.perform(post("/api/auth/logout").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
  }
}

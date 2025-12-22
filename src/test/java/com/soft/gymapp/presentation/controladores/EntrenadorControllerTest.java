package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;  // Now we'll use this

@ExtendWith(MockitoExtension.class)
class EntrenadorControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void constructor_InjectionWorks_ControllerCreatedSuccessfully() {
        // Arrange & Act
        EntrenadorController controller = new EntrenadorController(usuarioService);

        // Assert
        assertNotNull(controller);
        // The controller was created with the mock = constructor injection works
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void dashboard_ReturnsCorrectViewName() {
        // Arrange
        EntrenadorController controller = new EntrenadorController(usuarioService);

        // Act
        String viewName = controller.dashboard();

        // Assert
        assertEquals("entrenador/dashboard.html", viewName);
        // Verify no interaction with usuarioService was expected (if that's correct)
        verifyNoInteractions(usuarioService);  // Now using Mockito!
    }

    @Test
    @WithMockUser(roles = "ENTRENADOR")
    void rutinas_ReturnsCorrectViewName() {
        // Arrange
        EntrenadorController controller = new EntrenadorController(usuarioService);

        // Act
        String viewName = controller.rutinas();

        // Assert
        assertEquals("entrenador/rutinas.html", viewName);
        verifyNoInteractions(usuarioService);  // Using Mockito
    }
}
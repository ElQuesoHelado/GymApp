package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.usuarios.CuentaUsuario;
import com.soft.gymapp.dominio.usuarios.EstadoCuentaUsuario;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.dominio.usuarios.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //  Caso 1: Registro exitoso
    @Test
    void registrarUsuario_DeberiaRegistrarUsuarioExitosamente() {
        // Datos simulados
        String nombre = "Misael";
        String dni = "12345678";
        String email = "misael@test.com";
        String telefono = "999888777";
        String fechaNacimiento = "2002-03-15";
        String password = "123456";

        when(usuarioRepositorio.findByEmail(email)).thenReturn(Optional.empty());
        when(usuarioRepositorio.findByDni(dni)).thenReturn(Optional.empty());

        Map<String, Object> result = usuarioService.registrarUsuario(nombre, dni, email, telefono, fechaNacimiento, password);

        assertEquals("success", result.get("status"));
        assertEquals("Usuario registrado exitosamente siguiendo la receta.", result.get("message"));

        verify(usuarioRepositorio, times(1)).save(any(Usuario.class));
    }

    //  Caso 2: Error por email ya registrado
    @Test
    void registrarUsuario_DeberiaFallarSiEmailYaExiste() {
        when(usuarioRepositorio.findByEmail("misael@test.com"))
                .thenReturn(Optional.of(new Usuario()));

        Map<String, Object> result = usuarioService.registrarUsuario(
                "Misael", "12345678", "misael@test.com", "999888777", "2002-03-15", "123456"
        );

        assertEquals("error", result.get("status"));
        assertTrue(((Map<?, ?>) result.get("errors")).containsKey("email"));
    }

    //  Caso 3: Inicio de sesión exitoso
    @Test
    void iniciarSesion_DeberiaPermitirInicioSesionConCredencialesValidas() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Misael");
        usuario.setEmail("misael@test.com");

        CuentaUsuario cuenta = new CuentaUsuario("misael@test.com", "hashed_123456_super_secure", EstadoCuentaUsuario.ACTIVA);
        usuario.setCuentaUsuario(cuenta);

        when(usuarioRepositorio.findByEmail("misael@test.com"))
                .thenReturn(Optional.of(usuario));

        Map<String, Object> result = usuarioService.iniciarSesion("misael@test.com", "123456");

        assertEquals("success", result.get("status"));
        assertEquals("Inicio de sesión exitoso desde el servicio.", result.get("message"));
    }

    //  Caso 4: Inicio de sesión con credenciales incorrectas
    @Test
    void iniciarSesion_DeberiaFallarSiContrasenaIncorrecta() {
        Usuario usuario = new Usuario();
        usuario.setEmail("misael@test.com");
        usuario.setCuentaUsuario(new CuentaUsuario("misael@test.com", "hashed_123456_super_secure", EstadoCuentaUsuario.ACTIVA));

        when(usuarioRepositorio.findByEmail("misael@test.com"))
                .thenReturn(Optional.of(usuario));

        Map<String, Object> result = usuarioService.iniciarSesion("misael@test.com", "badpass");

        assertEquals("error", result.get("status"));
        assertEquals("Credenciales inválidas desde el servicio.", result.get("message"));
    }

    //  Caso 5: Listar usuarios
    @Test
    void listarTodosUsuarios_DeberiaRetornarListaUsuarios() {
        when(usuarioRepositorio.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));
        List<Usuario> result = usuarioService.listarTodosUsuarios();

        assertEquals(2, result.size());
        verify(usuarioRepositorio, times(1)).findAll();
    }
}

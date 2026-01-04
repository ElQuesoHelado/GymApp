package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.servicios.UsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  private static final Logger logger =
      LoggerFactory.getLogger(UsuarioController.class);

  private final UsuarioService usuarioService;

  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  // =======================
  // DTOs
  // =======================

  public record RegistroUsuarioRequest(String nombre, String DNI, String email,
                                       String telefono, String fechaNacimiento,
                                       String password) {}

  // =======================
  // Métodos auxiliares
  // =======================

  private Map<String, Object> respuesta(String status, String message,
                                        Map<String, Object> data,
                                        Map<String, String> errors) {
    Map<String, Object> response = new HashMap<>();
    response.put("status", status);
    response.put("message", message);

    if (data != null) {
      response.put("data", data);
    }
    if (errors != null && !errors.isEmpty()) {
      response.put("errors", errors);
    }
    return response;
  }

  // =======================
  // Endpoints REST
  // =======================

  /**
   * Registrar usuario
   */
  @PostMapping("/registrar")
  public Map<String, Object>
  registrarUsuario(@RequestBody RegistroUsuarioRequest request) {
    logger.info("Registro de usuario: {}", request.email());

    Map<String, Object> serviceResult = usuarioService.registrarUsuario(
        request.nombre(), request.DNI(), request.email(), request.telefono(),
        request.fechaNacimiento(), request.password());

    return respuesta((String)serviceResult.get("status"),
                     (String)serviceResult.get("message"),
                     (Map<String, Object>)serviceResult.get("usuario"),
                     (Map<String, String>)serviceResult.get("errors"));
  }

  /**
   * Listar usuarios
   */
  @GetMapping
  public Map<String, Object> listarUsuarios() {
    logger.info("Listando usuarios");

    List<Usuario> usuarios = usuarioService.listarTodosUsuarios();

    return respuesta("success", "Usuarios encontrados",
                     Map.of("usuarios", usuarios), null);
  }

  /**
   * Iniciar sesión
   */
  @PostMapping("/iniciarSesion")
  public Map<String, Object>
  iniciarSesion(@RequestBody Map<String, String> loginRequest) {
    logger.info("Inicio de sesión para: {}", loginRequest.get("username"));

    return usuarioService.iniciarSesion(loginRequest.get("username"),
                                        loginRequest.get("password"));
  }

  /**
   * Editar perfil
   */
  @PutMapping("/editarPerfil")
  public Map<String, Object>
  editarPerfil(@RequestBody Map<String, Object> updateRequest) {
    int userId = (Integer)updateRequest.get("id");

    logger.info("Editar perfil del usuario ID: {}", userId);

    Map<String, Object> serviceResult =
        usuarioService.editarPerfil(userId, updateRequest);

    return respuesta((String)serviceResult.get("status"),
                     (String)serviceResult.get("message"),
                     (Map<String, Object>)serviceResult.get("data"),
                     (Map<String, String>)serviceResult.get("errors"));
  }

  /**
   * Cerrar sesión (simulado)
   */
  @PostMapping("/cerrarSesion")
  public Map<String, Object> cerrarSesion() {
    logger.info("Cerrar sesión");

    return respuesta("success", "Sesión cerrada correctamente", null, null);
  }
}

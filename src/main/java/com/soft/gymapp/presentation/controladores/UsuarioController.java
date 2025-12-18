package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.servicios.UsuarioService;
import java.util.*;
import org.slf4j.Logger;        // CAMBIO: Importar Logger
import org.slf4j.LoggerFactory; // CAMBIO: Importar LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

  // CAMBIO: Añadir Logger
  private static final Logger logger =
      LoggerFactory.getLogger(UsuarioController.class);

  private final UsuarioService usuarioService;

  // --- CONSTANTES (Solo las que SE USAN) ---
  private static final String KEY_STATUS = "status";
  private static final String KEY_MESSAGE = "message";
  private static final String KEY_ERRORS = "errors";
  private static final String KEY_DATA = "data";
  private static final String KEY_USER = "usuario";

  private static final String KEY_ID = "id";
  private static final String KEY_NOMBRE = "nombre";
  private static final String KEY_EMAIL = "email";
  private static final String KEY_DNI = "DNI";
  private static final String KEY_TELEFONO = "telefono";
  private static final String KEY_FECHA_NACIMIENTO = "fechaNacimiento";
  private static final String KEY_USERNAME_CUENTA = "usernameCuenta";
  private static final String KEY_ESTADO_CUENTA = "estadoCuenta";
  private static final String KEY_USUARIOS_LIST = "usuarios";

  private static final String STATUS_SUCCESS = "success";

  @Autowired
  public UsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  // Clase DTO para el registro de usuario
  record RegistroUsuarioRequest(String nombre,
                                String DNI, // Se queda así porque es parte del
                                            // record (no es variable local)
                                String email, String telefono,
                                String fechaNacimiento, String password) {}

  // --- Método para formatear respuesta (REFACTORIZADO) ---
  private Map<String, Object> formatearRespuesta(String status, String message,
                                                 Map<String, Object> data,
                                                 Map<String, String> errors) {
    // CAMBIO: Cambiar nombre a camelCase
    Map<String, Object> response = new HashMap<>();
    response.put(KEY_STATUS, status);
    response.put(KEY_MESSAGE, message);
    if (data != null) {
      response.put(KEY_DATA, data);
    }
    if (errors != null && !errors.isEmpty()) {
      response.put(KEY_ERRORS, errors);
    }
    return response;
  }

  // --- Métodos del Controlador ---

  @PostMapping("/usuarios/registrar")
  @ResponseBody
  public Map<String, Object>
  registrarUsuario(@RequestBody RegistroUsuarioRequest request) {
    // CAMBIO: Reemplazar System.out por Logger
    logger.info("Recibida petición de registro para email: {}",
                request.email());

    Map<String, Object> serviceResult = usuarioService.registrarUsuario(
        request.nombre(),
        request.DNI(), // Aquí usa el campo DNI del record (con mayúscula)
        request.email(), request.telefono(), request.fechaNacimiento(),
        request.password());

    String status = (String)serviceResult.get(KEY_STATUS);
    String message = (String)serviceResult.get(KEY_MESSAGE);
    Map<String, Object> usuarioData =
        (Map<String, Object>)serviceResult.get(KEY_USER);
    Map<String, String> errors =
        (Map<String, String>)serviceResult.get(KEY_ERRORS);

    return formatearRespuesta(status, message, usuarioData, errors);
  }

  @GetMapping("/usuarios")
  @ResponseBody
  public Map<String, Object> listarUsuarios() {
    // CAMBIO: Reemplazar System.out por Logger
    logger.info("Recibida petición para listar usuarios");

    List<Usuario> usuarios = usuarioService.listarTodosUsuarios();

    List<Map<String, Object>> usuariosDto =
        usuarios.stream()
            .map(u -> {
              Map<String, Object> userMap = new HashMap<>();
              userMap.put(KEY_ID, u.getId());
              userMap.put(KEY_NOMBRE, u.getNombre());
              userMap.put(KEY_EMAIL, u.getEmail());
              userMap.put(KEY_DNI, u.getDni());
              userMap.put(KEY_TELEFONO, u.getTelefono());
              if (u.getFechaNacimiento() != null) {
                userMap.put(KEY_FECHA_NACIMIENTO, u.getFechaNacimiento());
              }
              if (u.getCuentaUsuario() != null) {
                userMap.put(KEY_USERNAME_CUENTA,
                            u.getCuentaUsuario().getUsername());
                userMap.put(KEY_ESTADO_CUENTA,
                            u.getCuentaUsuario().getEstado());
              }
              return userMap;
            })
            .toList();

    return formatearRespuesta(STATUS_SUCCESS, "Usuarios encontrados.",
                              Map.of(KEY_USUARIOS_LIST, usuariosDto), null);
  }

  @PostMapping("/usuarios/iniciarSesion")
  @ResponseBody
  public Map<String, Object>
  iniciarSesion(@RequestBody Map<String, String> loginRequest) {
    // CAMBIO: Reemplazar System.out por Logger
    String emailOrUsername = loginRequest.get("username");
    logger.info("Recibida petición de inicio de sesión para: {}",
                emailOrUsername);

    String password = loginRequest.get("password");

    Map<String, Object> serviceResult =
        usuarioService.iniciarSesion(emailOrUsername, password);

    String status = (String)serviceResult.get(KEY_STATUS);
    String message = (String)serviceResult.get(KEY_MESSAGE);
    Map<String, Object> usuarioData =
        (Map<String, Object>)serviceResult.get(KEY_USER);
    Map<String, String> errors =
        (Map<String, String>)serviceResult.get(KEY_ERRORS);

    return formatearRespuesta(status, message, usuarioData, errors);
  }

  @PostMapping("/usuarios/editarPerfil")
  @ResponseBody
  public Map<String, Object>
  editarPerfil(@RequestBody Map<String, Object> updateRequest) {
    // CAMBIO: Reemplazar System.out por Logger
    int userId = (Integer)updateRequest.get(KEY_ID);
    logger.info("Recibida petición para editar perfil del usuario ID: {}",
                userId);

    Map<String, Object> serviceResult =
        usuarioService.editarPerfil(userId, updateRequest);

    String status = (String)serviceResult.get(KEY_STATUS);
    String message = (String)serviceResult.get(KEY_MESSAGE);
    Map<String, Object> data = (Map<String, Object>)serviceResult.get(KEY_DATA);
    Map<String, String> errors =
        (Map<String, String>)serviceResult.get(KEY_ERRORS);

    return formatearRespuesta(status, message, data, errors);
  }

  @PostMapping("/usuarios/cerrarSesion")
  @ResponseBody
  public Map<String, Object> cerrarSesion() {
    // CAMBIO: Reemplazar System.out por Logger
    logger.info("Recibida petición para cerrar sesión");
    return formatearRespuesta(STATUS_SUCCESS,
                              "Sesión cerrada (simulado por controlador).",
                              null, null);
  }
}
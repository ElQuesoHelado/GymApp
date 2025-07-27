package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    // --- CONSTANTES PARA EVITAR CADENAS MÁGICAS EN ESTE CONTROLADOR ---
    // Estas constantes centralizan las cadenas literales repetidas aquí.
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERRORS = "errors";
    private static final String KEY_DATA = "data";
    private static final String KEY_USER = "usuario"; // Clave para el objeto usuario en respuestas

    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DNI = "DNI";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_FECHA_NACIMIENTO = "fechaNacimiento";
    private static final String KEY_USERNAME_CUENTA = "usernameCuenta";
    private static final String KEY_ESTADO_CUENTA = "estadoCuenta";
    private static final String KEY_USUARIOS_LIST = "usuarios"; // Para la lista de usuarios

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private static final String STATUS_INFO = "info";

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd"; // Para el formato de fecha

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    // Clase DTO (Data Transfer Object) para el registro de usuario
    record RegistroUsuarioRequest(
            String nombre,
            String DNI,
            String email,
            String telefono,
            String fechaNacimiento,
            String password
    ) {}

    // --- RECETA DENTRO DEL CONTROLADOR (para formato de respuesta) ---
    private Map<String, Object> receta_FormatearRespuesta(String status, String message, Map<String, Object> data, Map<String, String> errors) {
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

    // --- Métodos del Controlador (Delegando al Servicio) ---

    @PostMapping("/usuarios/registrar")
    @ResponseBody
    public Map<String, Object> registrarUsuario(@RequestBody RegistroUsuarioRequest request) {
        System.out.println("\n[Controlador] Recibida petición de registro.");

        Map<String, Object> serviceResult = usuarioService.registrarUsuario(
                request.nombre(),
                request.DNI(),
                request.email(),
                request.telefono(),
                request.fechaNacimiento(),
                request.password()
        );

        // Extraer valores usando las mismas claves (ahora constantes internas)
        String status = (String) serviceResult.get(KEY_STATUS);
        String message = (String) serviceResult.get(KEY_MESSAGE);
        Map<String, Object> usuarioData = (Map<String, Object>) serviceResult.get(KEY_USER);
        Map<String, String> errors = (Map<String, String>) serviceResult.get(KEY_ERRORS);

        return receta_FormatearRespuesta(status, message, usuarioData, errors);
    }

    @GetMapping("/usuarios")
    @ResponseBody
    public Map<String, Object> listarUsuarios() {
        System.out.println("\n[Controlador] Recibida petición de listar usuarios.");

        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();

        List<Map<String, Object>> usuariosDto = usuarios.stream()
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
                        userMap.put(KEY_USERNAME_CUENTA, u.getCuentaUsuario().getUsername());
                        userMap.put(KEY_ESTADO_CUENTA, u.getCuentaUsuario().getEstado());
                    }
                    return userMap;
                })
                .toList();

        return receta_FormatearRespuesta(STATUS_SUCCESS, "Usuarios encontrados.", Map.of(KEY_USUARIOS_LIST, usuariosDto), null);
    }

    @PostMapping("/usuarios/iniciarSesion")
    @ResponseBody
    public Map<String, Object> iniciarSesion(@RequestBody Map<String, String> loginRequest) {
        System.out.println("[Controlador] Recibida petición de inicio de sesión.");
        String emailOrUsername = loginRequest.get("username"); // Clave del request (no nuestra propia clave de respuesta)
        String password = loginRequest.get("password"); // Clave del request

        Map<String, Object> serviceResult = usuarioService.iniciarSesion(emailOrUsername, password);

        String status = (String) serviceResult.get(KEY_STATUS);
        String message = (String) serviceResult.get(KEY_MESSAGE);
        Map<String, Object> usuarioData = (Map<String, Object>) serviceResult.get(KEY_USER);
        Map<String, String> errors = (Map<String, String>) serviceResult.get(KEY_ERRORS);

        return receta_FormatearRespuesta(status, message, usuarioData, errors);
    }

    @PostMapping("/usuarios/editarPerfil")
    @ResponseBody
    public Map<String, Object> editarPerfil(@RequestBody Map<String, Object> updateRequest) {
        System.out.println("[Controlador] Recibida petición de editar perfil.");
        // Asumiendo que el ID viene en el request y es una clave común
        int userId = (Integer) updateRequest.get(KEY_ID);
        Map<String, Object> serviceResult = usuarioService.editarPerfil(userId, updateRequest);

        String status = (String) serviceResult.get(KEY_STATUS);
        String message = (String) serviceResult.get(KEY_MESSAGE);
        Map<String, Object> data = (Map<String, Object>) serviceResult.get(KEY_DATA);
        Map<String, String> errors = (Map<String, String>) serviceResult.get(KEY_ERRORS);

        return receta_FormatearRespuesta(status, message, data, errors);
    }

    @PostMapping("/usuarios/cerrarSesion")
    @ResponseBody
    public Map<String, Object> cerrarSesion() {
        System.out.println("[Controlador] Recibida petición de cerrar sesión.");
        return receta_FormatearRespuesta(STATUS_SUCCESS, "Sesión cerrada (simulado por controlador).", null, null);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/usuarios/cliente/dashboard")
    public String cliente_dashboard() {

//        usuarioService.

        return "cliente_dashboard";
    }

    @PreAuthorize("hasRole('ENTRENADOR')")
    @GetMapping("/usuarios/entrenador/dashboard")
    public String entrenador_dashboard() {
        return "entrenador_dashboard";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios/admin/dashboard")
    public String admin_dashboard() {
        return "administrador_dashboard";
    }


}
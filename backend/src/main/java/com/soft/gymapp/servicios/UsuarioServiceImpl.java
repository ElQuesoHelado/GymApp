package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.usuarios.CuentaUsuario;
import com.soft.gymapp.dominio.usuarios.EstadoCuentaUsuario;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.dominio.usuarios.UsuarioRepositorio;
import org.slf4j.Logger; // CAMBIO: Importar Logger
import org.slf4j.LoggerFactory; // CAMBIO: Importar LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import com.soft.gymapp.dominio.usuarios.*;
import org.springframework.security.core.Authentication;



@Service
public class UsuarioServiceImpl implements UsuarioService {

    // CAMBIO: Añadir Logger en lugar de System.out
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepositorio usuarioRepositorio;

    // --- CONSTANTES (ELIMINAR LAS NO USADAS) ---
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERRORS = "errors";
    private static final String KEY_USER = "usuario";
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DNI = "DNI";
    private static final String KEY_ESTADO_CUENTA = "estadoCuenta";


    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";

    private static final String ACCOUNT_STATUS_BLOCKED = "BLOQUEADA";

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // --- Métodos Auxiliares Privados (REFACTORIZADOS) ---

    /**
     * Hashear la Contraseña
     */
    private String hashearContrasena(String password) { // CAMBIO: camelCase
        logger.debug("Hasheando contraseña..."); // CAMBIO: Logger
        return "hashed_" + password + "_super_secure";
    }

    /**
     * Validar Datos de Registro del Usuario
     */
    private Map<String, String> validarDatosRegistro(String nombre, String dni, String email, 
                                                     String fechaNacimiento, String password) { 
                                                     // CAMBIO: 'dni' en minúscula, quitar 'telefono'
        logger.debug("Validando datos de registro..."); // CAMBIO: Logger
        Map<String, String> errors = new HashMap<>();

        if (nombre == null || nombre.isBlank()) errors.put(KEY_NOMBRE, "El nombre es requerido.");
        if (dni == null || dni.isBlank()) errors.put(KEY_DNI, "El DNI es requerido."); // CAMBIO: 'dni'
        if (email == null || email.isBlank() || !email.contains("@")) errors.put(KEY_EMAIL, "El email es inválido.");
        if (password == null || password.length() < 6) errors.put("password", "La contraseña debe tener al menos 6 caracteres.");
        if (fechaNacimiento == null || fechaNacimiento.isBlank()) {
            errors.put("fechaNacimiento", "La fecha de nacimiento es requerida.");
        } else {
            try { new SimpleDateFormat(DATE_FORMAT).parse(fechaNacimiento); }
            catch (ParseException e) { errors.put("fechaNacimiento", "Formato de fecha de nacimiento inválido (esperado YYYY-MM-DD)."); }
        }

        // Usando el repositorio para verificar unicidad
        if (usuarioRepositorio.findByEmail(email).isPresent()) { errors.put(KEY_EMAIL, "El email ya está registrado."); }
        if (usuarioRepositorio.findByDni(dni).isPresent()) { errors.put(KEY_DNI, "El DNI ya está registrado."); } // CAMBIO: 'dni'

        return errors;
    }

    /**
     * Crear Entidad Usuario
     */
    private Usuario crearEntidadUsuario(String nombre, String dni, String email, // CAMBIO: 'dni'
                                        String fechaNacimientoStr, String hashedPassword) throws ParseException {
        logger.debug("Creando entidad Usuario y CuentaUsuario..."); // CAMBIO: Logger
        LocalDate fechaNacimientoParsed = LocalDate.parse(fechaNacimientoStr);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setDni(dni); // CAMBIO: 'dni'
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setFechaNacimiento(fechaNacimientoParsed);

        CuentaUsuario cuentaUsuario = new CuentaUsuario(email, hashedPassword, EstadoCuentaUsuario.ACTIVA);
        nuevoUsuario.setCuentaUsuario(cuentaUsuario);

        return nuevoUsuario;
    }

    /**
     * Guardar Usuario
     */
    private void guardarUsuario(Usuario usuario) { // CAMBIO: camelCase
        logger.debug("Guardando usuario en el repositorio..."); // CAMBIO: Logger
        usuarioRepositorio.save(usuario);
    }

    // --- MÉTODO PRINCIPAL DE REGISTRO ---

    @Override
    public Map<String, Object> registrarUsuario(String nombre, String dni, String email, // CAMBIO: 'dni'
                                                String telefono, String fechaNacimiento, String password) {
        logger.info("Ejecutando registro de usuario para: {}", email); // CAMBIO: Logger

        // Paso 1: Validar los datos (llamada actualizada)
        Map<String, String> validationErrors = validarDatosRegistro(nombre, dni, email, fechaNacimiento, password);
        if (!validationErrors.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Errores de validación.");
            response.put(KEY_ERRORS, validationErrors);
            return response;
        }

        try {
            // Paso 2: Hashear la contraseña
            String hashedPassword = hashearContrasena(password);

            // Paso 3: Crear la entidad Usuario (llamada actualizada)
            Usuario nuevoUsuario = crearEntidadUsuario(nombre, dni, email, fechaNacimiento, hashedPassword);
            nuevoUsuario.setTelefono(telefono); // Asignar teléfono aquí

            // Paso 4: Guardar el usuario
            guardarUsuario(nuevoUsuario);

            logger.info("Usuario registrado exitosamente. ID: {}", nuevoUsuario.getId()); // CAMBIO: Logger

            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_SUCCESS);
            response.put(KEY_MESSAGE, "Usuario registrado exitosamente.");
            response.put(KEY_USER, Map.of(
                    KEY_ID, nuevoUsuario.getId(),
                    KEY_NOMBRE, nuevoUsuario.getNombre(),
                    KEY_EMAIL, nuevoUsuario.getEmail(),
                    KEY_DNI, nuevoUsuario.getDni(),
                    KEY_ESTADO_CUENTA, nuevoUsuario.getCuentaUsuario().getEstado()
            ));
            return response;

        } catch (ParseException ex) { // CAMBIO: 'ex' en lugar de 'e'
            logger.error("Error al procesar fecha durante registro: {}", ex.getMessage(), ex); // CAMBIO: Logger
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Error interno de servicio al procesar fecha: " + ex.getMessage());
            return response;
        }
    }

    @Override
    public UsuarioDTO obtenerUsuarioLogueado(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }

        String nombre = authentication.getName(); // username/email

        Usuario usuario = usuarioRepositorio.findByCuentaUsuarioUsername(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String tipo = switch (usuario) {
            case Administrador administrador -> "ADMIN";
            case Entrenador entrenador -> "ENTRENADOR";
            case Cliente cliente -> "CLIENTE";
            case null, default -> "DESCONOCIDO";
        };

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getDni(),
                tipo
        );
    }
    // --- Otros métodos del Servicio ---

    @Override
    public List<Usuario> listarTodosUsuarios() {
        logger.debug("Listando todos los usuarios..."); // CAMBIO: Logger
        return usuarioRepositorio.findAll();
    }

    @Override
    public Map<String, Object> iniciarSesion(String emailOrUsername, String password) {
        logger.info("Iniciando sesión para: {}", emailOrUsername); // CAMBIO: Logger

        if (emailOrUsername == null || emailOrUsername.isBlank() || password == null || password.isBlank()) {
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Usuario y contraseña son requeridos.");
            return response;
        }

        Optional<Usuario> foundUser = usuarioRepositorio.findByEmail(emailOrUsername);

        if (foundUser.isPresent()) {
            Usuario user = foundUser.get();
            CuentaUsuario cuenta = user.getCuentaUsuario();

            if (cuenta.getEstado().equals(ACCOUNT_STATUS_BLOCKED)) {
                Map<String, Object> response = new HashMap<>();
                response.put(KEY_STATUS, STATUS_ERROR);
                response.put(KEY_MESSAGE, "Su cuenta está bloqueada.");
                return response;
            }

            if (cuenta.getPassword().equals(hashearContrasena(password))) {
                Map<String, Object> response = new HashMap<>();
                response.put(KEY_STATUS, STATUS_SUCCESS);
                response.put(KEY_MESSAGE, "Inicio de sesión exitoso.");
                response.put(KEY_USER, Map.of(KEY_ID, user.getId(), KEY_NOMBRE, user.getNombre(), 
                        KEY_EMAIL, user.getEmail(), KEY_ESTADO_CUENTA, cuenta.getEstado()));
                return response;
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put(KEY_STATUS, STATUS_ERROR);
        response.put(KEY_MESSAGE, "Credenciales inválidas.");
        return response;
    }

    @Override
    public Map<String, Object> editarPerfil(int userId, Map<String, Object> updates) {
        logger.info("Editando perfil para ID: {}", userId); // CAMBIO: Logger
        // Lógica de negocio para editar perfil, validaciones adicionales, etc.
        Map<String, Object> response = new HashMap<>();
        response.put(KEY_STATUS, STATUS_ERROR); // CAMBIO: Usar STATUS_ERROR ya que STATUS_INFO se eliminó
        response.put(KEY_MESSAGE, "Funcionalidad de editar perfil en desarrollo.");
        return response;
    }
}
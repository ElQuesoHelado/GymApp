package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.usuarios.CuentaUsuario;
import com.soft.gymapp.dominio.usuarios.EstadoCuentaUsuario;
import com.soft.gymapp.dominio.usuarios.Usuario;
import com.soft.gymapp.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    // --- CONSTANTES PARA EVITAR CADENAS MÁGICAS (Solución de SonarLint) ---
    // Estas constantes mejoran la legibilidad y mantenibilidad del código.
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ERRORS = "errors";
    private static final String KEY_USER = "usuario";
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_DNI = "DNI";
    private static final String KEY_ESTADO_CUENTA = "estadoCuenta";
    private static final String KEY_DATA = "data"; // Para usos más genéricos

    private static final String STATUS_SUCCESS = "success";
    private static final String STATUS_ERROR = "error";
    private static final String STATUS_INFO = "info"; // Usado en algunos mensajes

    private static final String ACCOUNT_STATUS_ACTIVE = "ACTIVO";
    private static final String ACCOUNT_STATUS_BLOCKED = "BLOQUEADA";

    private static final String DATE_FORMAT = "yyyy-MM-dd";


    // --- Métodos Auxiliares/Privados para el Estilo Cookbook ---

    /**
     * Receta 1: Hashear la Contraseña
     * Una receta para transformar una contraseña en texto plano en un hash seguro.
     */
    private String receta_HashearContrasena(String password) {
        System.out.println("  [Servicio - Receta] Hasheando contraseña...");
        // En producción: usaría BCryptPasswordEncoder.encode(password)
        return "hashed_" + password + "_super_secure";
    }

    /**
     * Receta 2: Validar Datos de Registro del Usuario
     * Una receta para aplicar todas las reglas de validación de negocio.
     */
    private Map<String, String> receta_ValidarDatosRegistro(String nombre, String DNI, String email, String telefono, String fechaNacimiento, String password) {
        System.out.println("  [Servicio - Receta] Validando datos de registro...");
        Map<String, String> errors = new HashMap<>();

        if (nombre == null || nombre.isBlank()) errors.put(KEY_NOMBRE, "El nombre es requerido.");
        if (DNI == null || DNI.isBlank()) errors.put(KEY_DNI, "El DNI es requerido.");
        if (email == null || email.isBlank() || !email.contains("@")) errors.put(KEY_EMAIL, "El email es inválido.");
        if (password == null || password.length() < 6) errors.put("password", "La contraseña debe tener al menos 6 caracteres.");
        if (fechaNacimiento == null || fechaNacimiento.isBlank()) {
            errors.put("fechaNacimiento", "La fecha de nacimiento es requerida.");
        } else {
            try { new SimpleDateFormat(DATE_FORMAT).parse(fechaNacimiento); }
            catch (ParseException e) { errors.put("fechaNacimiento", "Formato de fecha de nacimiento inválido (esperado YYYY-MM-DD)."); }
        }

        // Usando el repositorio para verificar unicidad
        if (usuarioRepositorio.buscarPorEmail(email).isPresent()) { errors.put(KEY_EMAIL, "El email ya está registrado."); }
        if (usuarioRepositorio.buscarPorDNI(DNI).isPresent()) { errors.put(KEY_DNI, "El DNI ya está registrado."); }

        return errors;
    }

    /**
     * Receta 3: Crear Entidad Usuario
     * Una receta para construir el objeto Usuario con sus sub-objetos.
     */
    private Usuario receta_CrearEntidadUsuario(String nombre, String DNI, String email, String telefono, String fechaNacimientoStr, String hashedPassword) throws ParseException {
        System.out.println("  [Servicio - Receta] Creando entidad Usuario y CuentaUsuario...");
        Date fechaNacimientoParsed = new SimpleDateFormat(DATE_FORMAT).parse(fechaNacimientoStr);

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setDni(DNI);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setFechaNacimiento(fechaNacimientoParsed);

        CuentaUsuario cuentaUsuario = new CuentaUsuario(email, hashedPassword, EstadoCuentaUsuario.ACTIVA);
        nuevoUsuario.setCuentaUsuario(cuentaUsuario);

        return nuevoUsuario;
    }

    /**
     * Receta 4: Guardar Usuario
     * Una receta para persistir el objeto Usuario usando el repositorio.
     */
    private void receta_GuardarUsuario(Usuario usuario) {
        System.out.println("  [Servicio - Receta] Guardando usuario en el repositorio...");
        usuarioRepositorio.guardar(usuario);
    }

    // --- MÉTODO PRINCIPAL DE REGISTRO (LA "RECETA MAESTRA") ---

    @Override
    public Map<String, Object> registrarUsuario(String nombre, String DNI, String email, String telefono, String fechaNacimiento, String password) {
        System.out.println("\n[Servicio] Ejecutando Receta Maestra: Registrar Usuario.");

        // Paso 1 de la Receta Maestra: Validar los datos
        Map<String, String> validationErrors = receta_ValidarDatosRegistro(nombre, DNI, email, telefono, fechaNacimiento, password);
        if (!validationErrors.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Errores de validación de la receta.");
            response.put(KEY_ERRORS, validationErrors);
            return response;
        }

        try {
            // Paso 2 de la Receta Maestra: Hashear la contraseña
            String hashedPassword = receta_HashearContrasena(password);

            // Paso 3 de la Receta Maestra: Crear la entidad Usuario
            Usuario nuevoUsuario = receta_CrearEntidadUsuario(nombre, DNI, email, telefono, fechaNacimiento, hashedPassword);

            // Paso 4 de la Receta Maestra: Guardar el usuario
            receta_GuardarUsuario(nuevoUsuario);

            System.out.println("[Servicio] Receta Maestra 'Registrar Usuario' completada exitosamente. ID: " + nuevoUsuario.getId());

            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_SUCCESS);
            response.put(KEY_MESSAGE, "Usuario registrado exitosamente siguiendo la receta.");
            response.put(KEY_USER, Map.of(
                    KEY_ID, nuevoUsuario.getId(),
                    KEY_NOMBRE, nuevoUsuario.getNombre(),
                    KEY_EMAIL, nuevoUsuario.getEmail(),
                    KEY_DNI, nuevoUsuario.getDni(),
                    KEY_ESTADO_CUENTA, nuevoUsuario.getCuentaUsuario().getEstado()
            ));
            return response;

        } catch (ParseException e) {
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Error interno de servicio al procesar fecha: " + e.getMessage());
            return response;
        }
    }

    // --- Otros métodos del Servicio (sin estilo Cookbook tan explícito por ahora) ---

    @Override
    public List<Usuario> listarTodosUsuarios() {
        System.out.println("[Servicio] Listando todos los usuarios...");
        return usuarioRepositorio.listarTodos();
    }

    @Override
    public Map<String, Object> iniciarSesion(String emailOrUsername, String password) {
        System.out.println("[Servicio] Iniciando sesión para: " + emailOrUsername);

        if (emailOrUsername == null || emailOrUsername.isBlank() || password == null || password.isBlank()) {
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, STATUS_ERROR);
            response.put(KEY_MESSAGE, "Usuario y contraseña son requeridos.");
            return response;
        }

        Optional<Usuario> foundUser = usuarioRepositorio.buscarPorEmail(emailOrUsername);

        if (foundUser.isPresent()) {
            Usuario user = foundUser.get();
            CuentaUsuario cuenta = user.getCuentaUsuario();

            if (cuenta.getEstado().equals(ACCOUNT_STATUS_BLOCKED)) {
                Map<String, Object> response = new HashMap<>();
                response.put(KEY_STATUS, STATUS_ERROR);
                response.put(KEY_MESSAGE, "Su cuenta está bloqueada.");
                return response;
            }

            if (cuenta.getPassword().equals(receta_HashearContrasena(password))) { // Usamos la misma receta de hashing
                Map<String, Object> response = new HashMap<>();
                response.put(KEY_STATUS, STATUS_SUCCESS);
                response.put(KEY_MESSAGE, "Inicio de sesión exitoso desde el servicio.");
                response.put(KEY_USER, Map.of(KEY_ID, user.getId(), KEY_NOMBRE, user.getNombre(), KEY_EMAIL, user.getEmail(), KEY_ESTADO_CUENTA, cuenta.getEstado()));
                return response;
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put(KEY_STATUS, STATUS_ERROR);
        response.put(KEY_MESSAGE, "Credenciales inválidas desde el servicio.");
        return response;
    }

    @Override
    public Map<String, Object> editarPerfil(int userId, Map<String, Object> updates) {
        System.out.println("[Servicio] Editando perfil para ID: " + userId);
        // Lógica de negocio para editar perfil, validaciones adicionales, etc.
        // Después, usaría usuarioRepositorio.actualizar(usuario);
        Map<String, Object> response = new HashMap<>();
        response.put(KEY_STATUS, STATUS_INFO);
        response.put(KEY_MESSAGE, "Funcionalidad de editar perfil en servicio en desarrollo.");
        return response;
    }
}
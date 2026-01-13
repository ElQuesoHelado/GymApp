package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.membresias.Membresia;
import com.soft.gymapp.dominio.usuarios.Usuario;
import java.util.List;
import java.util.Map;

import com.soft.gymapp.servicios.dto.NotificacionDTO;
import com.soft.gymapp.servicios.dto.SesionDTO;
import com.soft.gymapp.servicios.dto.UsuarioDTO;
import org.springframework.security.core.Authentication;

public interface UsuarioService {
  UsuarioDTO obtenerUsuarioLogueado();

  /**
   * Registra un nuevo usuario en el sistema.
   * @param nombre Nombre completo del usuario
   * @param dni Documento nacional de identidad
   * @param email Correo electrónico
   * @param telefono Número telefónico
   * @param fechaNacimiento Fecha de nacimiento (formato yyyy-MM-dd)
   * @param password Contraseña
   * @return Map con el resultado de la operación y mensajes informativos
   */
  Map<String, Object> registrarUsuario(String nombre, String dni, String email,
                                       String telefono, String fechaNacimiento,
                                       String password);

  /**
   * Lista todos los usuarios registrados.
   * @return Lista de usuarios
   */
  List<Usuario> listarTodosUsuarios();

  /**
   * Inicia sesión con un email o nombre de usuario y contraseña.
   * @param emailOrUsername Email o username
   * @param password Contraseña
   * @return Map con el estado de inicio de sesión y datos del usuario si es
   *     exitoso
   */
  Map<String, Object> iniciarSesion(String emailOrUsername, String password);

  /**
   * Edita el perfil de un usuario existente.
   * @param userId ID del usuario a editar
   * @param updates Mapa con los campos a actualizar
   * @return Map con el resultado de la operación
   */
  Map<String, Object> editarPerfil(int userId, Map<String, Object> updates);

  //TODO: Esta funcion es usada para todo tipo de usuario
  List<NotificacionDTO> obtenerNotificaciones();

  /*
   * Tando Cliente como Entrenador tiene lista de sesiones 
   */
  List<SesionDTO> listarSesiones();

  Membresia obtenerMembresia();
}

package com.soft.gymapp.servicios;

import com.soft.gymapp.dominio.usuarios.Usuario;
import java.util.List;
import java.util.Map;

public interface UsuarioService {
    // Los parámetros son desagregados para que el servicio reciba solo los datos puros
    Map<String, Object> registrarUsuario(String nombre, String DNI, String email, String telefono, String fechaNacimiento, String password);
    List<Usuario> listarTodosUsuarios();
    Map<String, Object> iniciarSesion(String emailOrUsername, String password);
    Map<String, Object> editarPerfil(int userId, Map<String, Object> updates);

}
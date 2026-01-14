package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.UsuarioService;
import com.soft.gymapp.servicios.dto.EntrenadorDTO; // Usamos la clase existente
import com.soft.gymapp.servicios.dto.ClienteAsignadoDTO; // El record nuevo para la lista

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/entrenador") 
@PreAuthorize("hasRole('ENTRENADOR')")
@CrossOrigin(origins = "http://localhost:5173")
public class EntrenadorController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/home")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> obtenerDatosHome() {
        Map<String, Object> respuesta = new HashMap<>();
        
        try {
            EntrenadorDTO entrenador = usuarioService.obtenerPerfilEntrenador();

            respuesta.put("id", "#ENT-" + entrenador.getId());
            respuesta.put("nombre", entrenador.getNombre());
            respuesta.put("email", entrenador.getEmail());
            respuesta.put("dni", entrenador.getDni());
            respuesta.put("telefono", entrenador.getTelefono());
            respuesta.put("especialidad", entrenador.getEspecialidad());

            respuesta.put("rol", "Entrenador Staff");
            respuesta.put("clientesActivos", 5); 
            respuesta.put("apellido", "");

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            respuesta.put("error", "Error cargando perfil: " + e.getMessage());
            return ResponseEntity.status(500).body(respuesta);
        }
    }

    @GetMapping("/clientes-asignados")
    @ResponseBody
    public ResponseEntity<List<ClienteAsignadoDTO>> obtenerClientesAsignados() {
        try {
            return ResponseEntity.ok(usuarioService.listarClientesAsignados());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
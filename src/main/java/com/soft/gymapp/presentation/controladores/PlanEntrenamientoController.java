package com.soft.gymapp.presentation.controladores;

import com.soft.gymapp.servicios.PlanEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/planes-entrenamiento")
public class PlanEntrenamientoController {

    // CAMBIO: Eliminar campo no usado o usarlo
    // Si realmente necesitas el servicio en el futuro, déjalo comentado
    
    // --- CONSTANTES PARA STRING LITERALS ---
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_RUTINA = "rutina";
    private static final String KEY_ASIGNACION = "asignacion";
    private static final String KEY_ERROR = "error";
    private static final String KEY_SUCCESS = "success";
    
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_OBJETIVO = "objetivo";
    private static final String KEY_RUTINA_ID = "rutinaId";
    private static final String KEY_CLIENTE_ID = "clienteId";
    
    private static final String MSG_NOMBRE_OBJETIVO_REQUERIDO = "Nombre y objetivo son campos requeridos";
    private static final String MSG_RUTINA_CLIENTE_REQUERIDO = "rutinaId y clienteId son campos requeridos";
    private static final String MSG_ID_RUTINA_INVALIDO = "ID de rutina inválido";
    
    private static final String MSG_RUTINA_CREADA = "Rutina creada exitosamente";
    private static final String MSG_RUTINA_ASIGNADA = "Rutina asignada al cliente exitosamente";
    private static final String MSG_RUTINA_MODIFICADA = "Rutina modificada exitosamente";

    @Autowired
    public PlanEntrenamientoController(PlanEntrenamientoService planEntrenamientoService) {
        // CAMBIO: Si eliminas el campo, también elimina esta línea
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/usuarios/cliente/planes_entrenamiento")
    public String home(Model model) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();

        model.addAttribute("username", username);
        model.addAttribute("slogan", "Carátula inicial para app de gestión de gimnasios, esto desde el controlador");
        model.addAttribute("fecha", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        return "planes_entrenamiento";
    }

    // --- IMPLEMENTACIÓN DE MÉTODOS FALTANTES ---

    /**
     * POST /api/planes-entrenamiento/rutinas
     * Crea una nueva rutina de entrenamiento
     */
    @PostMapping("/rutinas")
    @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> crearRutina(@RequestBody Map<String, Object> rutinaRequest) {
        try {
            // Validación básica de datos requeridos usando constantes
            if (!rutinaRequest.containsKey(KEY_NOMBRE) || !rutinaRequest.containsKey(KEY_OBJETIVO)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put(KEY_STATUS, KEY_ERROR);
                errorResponse.put(KEY_MESSAGE, MSG_NOMBRE_OBJETIVO_REQUERIDO);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // NOTA: Para una implementación completa, se necesita:
            // 2. Conectar con la base de datos
            
            // Respuesta simulada
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, KEY_SUCCESS);
            response.put(KEY_MESSAGE, MSG_RUTINA_CREADA);
            response.put(KEY_RUTINA, Map.of(
                "id", 1, // ID generado
                KEY_NOMBRE, rutinaRequest.get(KEY_NOMBRE),
                KEY_OBJETIVO, rutinaRequest.get(KEY_OBJETIVO),
                "fechaCreacion", LocalDate.now().toString()
            ));
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(KEY_STATUS, KEY_ERROR);
            errorResponse.put(KEY_MESSAGE, "Error al crear rutina: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * POST /api/planes-entrenamiento/rutinas/asignar
     * Asigna una rutina existente a un cliente específico
     */
    @PostMapping("/rutinas/asignar")
    @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> asignarRutinaACliente(
            @RequestBody Map<String, Object> asignacionRequest) {
        try {
            // Validar datos requeridos usando constantes
            if (!asignacionRequest.containsKey(KEY_RUTINA_ID) || !asignacionRequest.containsKey(KEY_CLIENTE_ID)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put(KEY_STATUS, KEY_ERROR);
                errorResponse.put(KEY_MESSAGE, MSG_RUTINA_CLIENTE_REQUERIDO);
                return ResponseEntity.badRequest().body(errorResponse);
            }

            // NOTA: Para implementación completa:
            
            // Respuesta simulada
            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, KEY_SUCCESS);
            response.put(KEY_MESSAGE, MSG_RUTINA_ASIGNADA);
            response.put(KEY_ASIGNACION, Map.of(
                KEY_RUTINA_ID, asignacionRequest.get(KEY_RUTINA_ID),
                KEY_CLIENTE_ID, asignacionRequest.get(KEY_CLIENTE_ID),
                "fechaAsignacion", LocalDate.now().toString()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(KEY_STATUS, KEY_ERROR);
            errorResponse.put(KEY_MESSAGE, "Error al asignar rutina: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * PUT /api/planes-entrenamiento/rutinas/{id}
     * Modifica una rutina existente
     */
    @PutMapping("/rutinas/{id}")
    @PreAuthorize("hasRole('ENTRENADOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> modificarRutina(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> rutinaUpdates) {
        try {
            // Validar que el ID es válido
            if (id <= 0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put(KEY_STATUS, KEY_ERROR);
                errorResponse.put(KEY_MESSAGE, MSG_ID_RUTINA_INVALIDO);
                return ResponseEntity.badRequest().body(errorResponse);
            }


            Map<String, Object> response = new HashMap<>();
            response.put(KEY_STATUS, KEY_SUCCESS);
            response.put(KEY_MESSAGE, MSG_RUTINA_MODIFICADA);
            response.put(KEY_RUTINA, Map.of(
                "id", id,
                "actualizaciones", rutinaUpdates,
                "fechaModificacion", LocalDate.now().toString()
            ));
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(KEY_STATUS, KEY_ERROR);
            errorResponse.put(KEY_MESSAGE, "Error al modificar rutina: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
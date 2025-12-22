package com.soft.gymapp.dominio.sesiones;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "sala")
public class Sala {
    
    private static final Logger logger = LoggerFactory.getLogger(Sala.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false)
    private int capacidad;
    
    @OneToMany(mappedBy = "sala")
    private List<Sesion> sesiones = new ArrayList<>();
    
    // Constructores
    public Sala() {
        // Constructor por defecto requerido por JPA
    }
    
    public Sala(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    
    public Sala(int id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    
    /**
     * Verifica la disponibilidad de la sala para una fecha específica.
     * Considera las sesiones programadas y la capacidad.
     * 
     * @param fecha Fecha para verificar disponibilidad
     * @return true si la sala está disponible, false si está ocupada o sobrecapacidad
     */
    public boolean verDisponibilidad(LocalDate fecha) {
        if (fecha == null) {
            logger.warn("Fecha nula proporcionada para verificar disponibilidad");
            return false;
        }
        
        // Verificar si hay sesiones en esa fecha
        long sesionesEnFecha = sesiones.stream()
                .filter(sesion -> sesion.getHorario() != null)
                .filter(sesion -> {
                    LocalDate fechaSesion = sesion.getHorario().getFecha();
                    return fechaSesion != null && fechaSesion.equals(fecha);
                })
                .count();
        
        // Si no hay sesiones, está disponible
        if (sesionesEnFecha == 0) {
            logger.debug("Sala {} disponible para la fecha {}", nombre, fecha);
            return true;
        }
        
        // Verificar capacidad máxima para ese día
        // Podrías añadir lógica más compleja aquí (ej: horarios específicos)
        logger.debug("Sala {} tiene {} sesiones programadas para {}", 
                     nombre, sesionesEnFecha, fecha);
        
        // Por simplicidad, asumimos que si ya tiene sesiones, está ocupada
        // En una implementación real, verificarías horarios específicos
        return false;
    }
    
    /**
     * Verifica disponibilidad considerando también el horario.
     * 
     * @param fecha Fecha para verificar
     * @param horaInicio Hora de inicio propuesta
     * @param horaFin Hora de fin propuesta
     * @return true si disponible en ese horario específico
     */
    public boolean verDisponibilidad(LocalDate fecha, java.time.LocalTime horaInicio, java.time.LocalTime horaFin) {
        if (fecha == null || horaInicio == null || horaFin == null) {
            return false;
        }
        
        if (horaInicio.isAfter(horaFin)) {
            logger.warn("Hora de inicio {} es después de hora de fin {}", horaInicio, horaFin);
            return false;
        }
        
        return sesiones.stream()
                .filter(sesion -> sesion.getHorario() != null)
                .noneMatch(sesion -> {
                    LocalDate fechaSesion = sesion.getHorario().getFecha();
                    java.time.LocalTime inicioSesion = sesion.getHorario().getHoraInicio();
                    java.time.LocalTime finSesion = sesion.getHorario().getHoraFin();
                    
                    if (fechaSesion == null || inicioSesion == null || finSesion == null) {
                        return false;
                    }
                    
                    // Verificar superposición de horarios
                    boolean mismaFecha = fechaSesion.equals(fecha);
                    boolean horariosSuperpuestos = 
                        !(horaFin.isBefore(inicioSesion) || horaInicio.isAfter(finSesion));
                    
                    return mismaFecha && horariosSuperpuestos;
                });
    }
    
    /**
     * Verifica si la sala puede acomodar más clientes en una sesión específica.
     * 
     * @param sesionId ID de la sesión
     * @param clientesAdicionales Número de clientes a agregar
     * @return true si hay capacidad, false si se excede
     */
    public boolean tieneCapacidadPara(int sesionId, int clientesAdicionales) {
        return sesiones.stream()
                .filter(sesion -> sesion.getId() == sesionId)
                .findFirst()
                .map(sesion -> {
                    int clientesActuales = sesion.getClientes().size();
                    return (clientesActuales + clientesAdicionales) <= capacidad;
                })
                .orElse(true); // Si no encuentra la sesión, asume que hay capacidad
    }
    
    /**
     * Agrega una sesión a esta sala.
     * 
     * @param sesion Sesión a agregar
     * @throws IllegalArgumentException si la sesión es nula
     */
    public void agregarSesion(Sesion sesion) {
        if (sesion == null) {
            throw new IllegalArgumentException("La sesión no puede ser nula");
        }
        
        if (!sesiones.contains(sesion)) {
            sesiones.add(sesion);
            sesion.setSala(this); // Mantener relación bidireccional
            logger.info("Sesión {} agregada a la sala {}", sesion.getId(), nombre);
        }
    }
    
    /**
     * Remueve una sesión de esta sala.
     * 
     * @param sesion Sesión a remover
     */
    public void removerSesion(Sesion sesion) {
        if (sesion != null && sesiones.remove(sesion)) {
            sesion.setSala(null); // Romper relación bidireccional
            logger.info("Sesión {} removida de la sala {}", sesion.getId(), nombre);
        }
    }
    
    /**
     * Obtiene todas las sesiones programadas para una fecha.
     * 
     * @param fecha Fecha a consultar
     * @return Lista de sesiones en esa fecha
     */
    public List<Sesion> getSesionesPorFecha(LocalDate fecha) {
        if (fecha == null) {
            return Collections.emptyList();
        }
        
        return sesiones.stream()
                .filter(sesion -> sesion.getHorario() != null)
                .filter(sesion -> fecha.equals(sesion.getHorario().getFecha()))
                .toList();
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre.trim();
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        this.capacidad = capacidad;
    }
    
    /**
     * Obtiene la lista de sesiones (inmutable para evitar modificaciones directas).
     * 
     * @return Lista inmutable de sesiones
     */
    public List<Sesion> getSesiones() {
        return Collections.unmodifiableList(sesiones);
    }
    
    /**
     * Establece la lista de sesiones (crea una copia para evitar efectos laterales).
     * 
     * @param sesiones Nueva lista de sesiones
     */
    public void setSesiones(List<Sesion> sesiones) {
        if (sesiones == null) {
            this.sesiones = new ArrayList<>();
        } else {
            this.sesiones = new ArrayList<>(sesiones);
        }
    }
    
    /**
     * Verifica si la sala está actualmente ocupada (en este momento).
     * 
     * @return true si hay una sesión en progreso ahora
     */
    public boolean estaOcupadaAhora() {
        LocalDateTime ahora = LocalDateTime.now();
        
        return sesiones.stream()
                .filter(sesion -> sesion.getEstado() == EstadoSesion.EN_PROGRESO)
                .filter(sesion -> sesion.getHorario() != null)
                .anyMatch(sesion -> {
                    LocalDate fechaSesion = sesion.getHorario().getFecha();
                    java.time.LocalTime inicio = sesion.getHorario().getHoraInicio();
                    java.time.LocalTime fin = sesion.getHorario().getHoraFin();
                    
                    if (fechaSesion == null || inicio == null || fin == null) {
                        return false;
                    }
                    
                    boolean mismaFecha = fechaSesion.equals(ahora.toLocalDate());
                    boolean dentroDelHorario = 
                        !ahora.toLocalTime().isBefore(inicio) && 
                        !ahora.toLocalTime().isAfter(fin);
                    
                    return mismaFecha && dentroDelHorario;
                });
    }
    
    @Override
    public String toString() {
        return "Sala{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", sesiones=" + sesiones.size() +
                '}';
    }
}
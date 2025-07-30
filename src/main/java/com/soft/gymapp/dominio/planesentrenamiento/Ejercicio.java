package com.soft.gymapp.dominio.planesentrenamiento;

import jakarta.persistence.*;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    private Integer id;

    private String nombre;
    private String descripcion;
    private int repeticiones;
    private int series;

    @ManyToOne
    private Rutina rutina;

    public Ejercicio() {
        // Constructor por defecto requerido por JPA
    }

    /**
     * Calcula una estimación de calorías quemadas en base a la duración del ejercicio.
     * @param duracion duración del ejercicio en minutos
     * @return calorías quemadas (estimadas)
     */
    public float calcularCaloriasQuemadas(int duracion) {
        // Por ahora se retorna la duración como valor de calorías para propósitos de ejemplo.
        return duracion;
    }

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id != null && id > 0) {
            this.id = id;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre.trim();
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion != null && !descripcion.trim().isEmpty()) {
            this.descripcion = descripcion.trim();
        }
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        if (repeticiones >= 0) {
            this.repeticiones = repeticiones;
        }
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        if (series >= 0) {
            this.series = series;
        }
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }
}

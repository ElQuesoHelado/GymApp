package com.soft.gymapp.servicios.dto;

import java.time.LocalDate;
import java.util.Objects;

/**
 * DTO (Data Transfer Object) para la entidad Usuario.
 * Se utiliza para transferir datos entre capas sin exponer la entidad completa.
 */
public class UsuarioDTO {
    
    private int id;
    private String nombre;
    private String dni;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String username;  // De CuentaUsuario
    private String rol;       // De CuentaUsuario (CLIENTE, ENTRENADOR, ADMIN)
    private boolean enabled;  // De CuentaUsuario
    
    // Constructores
    
    /**
     * Constructor por defecto requerido para frameworks de serialización.
     */
    public UsuarioDTO() {
    }
    
    /**
     * Constructor con parámetros básicos.
     */
    public UsuarioDTO(int id, String nombre, String dni, String email, String telefono, 
                     LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    /**
     * Constructor completo incluyendo datos de cuenta de usuario.
     */
    public UsuarioDTO(int id, String nombre, String dni, String email, String telefono,
                     LocalDate fechaNacimiento, String username, String rol, boolean enabled) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.username = username;
        this.rol = rol;
        this.enabled = enabled;
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
        this.nombre = nombre;
    }
    
    public String getDni() {
        return dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    // Métodos utilitarios
    
    /**
     * Verifica si el usuario es un cliente.
     * @return true si el rol es CLIENTE
     */
    public boolean esCliente() {
        return "CLIENTE".equalsIgnoreCase(rol);
    }
    
    /**
     * Verifica si el usuario es un entrenador.
     * @return true si el rol es ENTRENADOR
     */
    public boolean esEntrenador() {
        return "ENTRENADOR".equalsIgnoreCase(rol);
    }
    
    /**
     * Verifica si el usuario es un administrador.
     * @return true si el rol es ADMIN
     */
    public boolean esAdministrador() {
        return "ADMIN".equalsIgnoreCase(rol);
    }
    
    /**
     * Verifica si el usuario está activo (habilitado).
     * @return true si está enabled
     */
    public boolean estaActivo() {
        return enabled;
    }
    
    /**
     * Obtiene la edad aproximada del usuario basada en la fecha de nacimiento.
     * @return Edad en años, o -1 si fechaNacimiento es null
     */
    public int getEdad() {
        if (fechaNacimiento == null) {
            return -1;
        }
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
    
    // equals, hashCode y toString
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioDTO that = (UsuarioDTO) o;
        return id == that.id && 
               Objects.equals(dni, that.dni) && 
               Objects.equals(email, that.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, dni, email);
    }
    
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", enabled=" + enabled +
                '}';
    }
    
    /**
     * Builder pattern para construcción fluida (opcional pero útil).
     */
    public static class Builder {
        private int id;
        private String nombre;
        private String dni;
        private String email;
        private String telefono;
        private LocalDate fechaNacimiento;
        private String username;
        private String rol;
        private boolean enabled;
        
        public Builder id(int id) {
            this.id = id;
            return this;
        }
        
        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public Builder dni(String dni) {
            this.dni = dni;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        
        public Builder fechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder rol(String rol) {
            this.rol = rol;
            return this;
        }
        
        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        
        public UsuarioDTO build() {
            return new UsuarioDTO(id, nombre, dni, email, telefono, 
                                 fechaNacimiento, username, rol, enabled);
        }
    }
    
    /**
     * Método estático para crear un Builder.
     * @return nueva instancia de Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}
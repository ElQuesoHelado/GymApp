package com.soft.gymapp.servicios.dto;

import java.util.List;

public class SesionDTO {
private int id;
    private String estado;
    private List<ClienteDTO> clientes;
    private EntrenadorDTO entrenador;
    private HorarioDTO horario;
    private SalaDTO sala;

    public SesionDTO() {}

    public SesionDTO(int id, String estado, List<ClienteDTO> clientes,
                     EntrenadorDTO entrenador, HorarioDTO horario, SalaDTO sala) {
        this.id = id;
        this.estado = estado;
        this.clientes = clientes;
        this.entrenador = entrenador;
        this.horario = horario;
        this.sala = sala;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<ClienteDTO> getClientes() { return clientes; }
    public void setClientes(List<ClienteDTO> clientes) { this.clientes = clientes; }

    public EntrenadorDTO getEntrenador() { return entrenador; }
    public void setEntrenador(EntrenadorDTO entrenador) { this.entrenador = entrenador; }

    public HorarioDTO getHorario() { return horario; }
    public void setHorario(HorarioDTO horario) { this.horario = horario; }

    public SalaDTO getSala() { return sala; }
    public void setSala(SalaDTO sala) { this.sala = sala; }
}
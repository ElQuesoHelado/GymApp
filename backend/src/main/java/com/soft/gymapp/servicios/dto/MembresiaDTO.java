package com.soft.gymapp.servicios.dto;

import com.soft.gymapp.dominio.membresias.EstadoMembresia;

import java.util.Date;
import java.util.List;

public record MembresiaDTO(
        Integer id,
        Date fechaInicio,
        Date fechaFin,
        EstadoMembresia estado,
        Double deuda,
        boolean activa,
        boolean vencida,
        TipoMembresiaDTO tipo,
        List<PagoMembresiaDTO> pagos,
        Integer clienteId
) {}
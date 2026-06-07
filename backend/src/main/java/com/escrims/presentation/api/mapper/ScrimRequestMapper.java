package com.escrims.presentation.api.mapper;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.presentation.api.dto.CreateScrimRequest;

public final class ScrimRequestMapper {

    private ScrimRequestMapper() {}

    public static CreateScrimDTO toCommand(CreateScrimRequest request) {
        CreateScrimDTO dto = new CreateScrimDTO();
        dto.setJuego(request.getJuego());
        dto.setJugadoresPorLado(request.getJugadoresPorLado());
        dto.setServidor(request.getServidor());
        dto.setZona(request.getZona());
        dto.setRangoMin(toRango(request.getRangoMin()));
        dto.setRangoMax(toRango(request.getRangoMax()));
        dto.setLatenciaMaxMs(request.getLatenciaMaxMs());
        dto.setFechaHora(request.getFechaHora());
        dto.setDuracionMinutos(request.getDuracionMinutos());
        dto.setModalidadNombre(request.getModalidadNombre());
        dto.setOrganizadorId(request.getOrganizadorId());
        return dto;
    }

    private static Rango toRango(CreateScrimRequest.RangoRequest rango) {
        if (rango == null) {
            throw new IllegalArgumentException("Rango requerido");
        }
        return new Rango(rango.getJuego(), rango.getTier(), rango.getNumerico());
    }
}

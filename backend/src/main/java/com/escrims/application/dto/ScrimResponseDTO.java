package com.escrims.application.dto;

import com.escrims.domain.model.scrim.Scrim;

import java.time.LocalDateTime;
import java.util.UUID;

public class ScrimResponseDTO {

    private UUID id;
    private String juego;
    private String estado;
    private String region;
    private int rangoMinMmr;
    private int rangoMaxMmr;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private UUID organizadorId;

    public static ScrimResponseDTO from(Scrim scrim) {
        ScrimResponseDTO dto = new ScrimResponseDTO();
        dto.id = scrim.getId();
        dto.juego = scrim.getJuego().getNombre();
        dto.estado = scrim.getEstadoNombre();
        dto.region = scrim.getRegion().getNombre();
        dto.rangoMinMmr = scrim.getRangoMin().getMmr();
        dto.rangoMaxMmr = scrim.getRangoMax().getMmr();
        dto.latenciaMaxMs = scrim.getLatenciaMax();
        dto.fechaHora = scrim.getFechaHora();
        dto.organizadorId = scrim.getOrganizador().getId();
        return dto;
    }

    public UUID getId() { return id; }
    public String getJuego() { return juego; }
    public String getEstado() { return estado; }
    public String getRegion() { return region; }
    public int getRangoMinMmr() { return rangoMinMmr; }
    public int getRangoMaxMmr() { return rangoMaxMmr; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public UUID getOrganizadorId() { return organizadorId; }
}

package com.escrims.application.dto;

import com.escrims.domain.model.scrim.Scrim;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ScrimResponseDTO {

    private UUID id;
    private String juego;
    private String estado;
    private int jugadoresPorLado;
    private String servidor;
    private String zona;
    private int rangoMin;
    private int rangoMax;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private String modalidad;
    private UUID organizadorId;
    private int cuposDisponibles;
    private List<UUID> participantes;

    public static ScrimResponseDTO from(Scrim scrim) {
        ScrimResponseDTO dto = new ScrimResponseDTO();
        dto.id = scrim.getId();
        dto.juego = scrim.getNombreJuego();
        dto.estado = scrim.getEstadoNombre();
        dto.jugadoresPorLado = scrim.getFormato().getJugadoresPorLado();
        dto.servidor = scrim.getRegion().getServidor();
        dto.zona = scrim.getRegion().getZona();
        dto.rangoMin = scrim.getRangosPermitidos().getMin().getNumerico();
        dto.rangoMax = scrim.getRangosPermitidos().getMax().getNumerico();
        dto.latenciaMaxMs = scrim.getLatenciaMax().getPingMs();
        dto.fechaHora = scrim.getFechaHora();
        dto.duracionMinutos = (int) scrim.getDuracionEstimada().toMinutes();
        dto.modalidad = scrim.getModalidad().getNombre();
        dto.organizadorId = scrim.getOrganizadorId();
        dto.cuposDisponibles = scrim.getCuposDisponibles();
        dto.participantes = scrim.getParticipantesIds();
        return dto;
    }

    public UUID getId() { return id; }
    public String getJuego() { return juego; }
    public String getEstado() { return estado; }
    public int getJugadoresPorLado() { return jugadoresPorLado; }
    public String getServidor() { return servidor; }
    public String getZona() { return zona; }
    public int getRangoMin() { return rangoMin; }
    public int getRangoMax() { return rangoMax; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public String getModalidad() { return modalidad; }
    public UUID getOrganizadorId() { return organizadorId; }
    public int getCuposDisponibles() { return cuposDisponibles; }
    public List<UUID> getParticipantes() { return participantes; }
}

package com.escrims.presentation.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateScrimRequest {

    private String juego;
    private int jugadoresPorLado;
    private String servidor;
    private String zona;
    private RangoRequest rangoMin;
    private RangoRequest rangoMax;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private String modalidadNombre;
    private UUID organizadorId;

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public int getJugadoresPorLado() { return jugadoresPorLado; }
    public void setJugadoresPorLado(int jugadoresPorLado) { this.jugadoresPorLado = jugadoresPorLado; }
    public String getServidor() { return servidor; }
    public void setServidor(String servidor) { this.servidor = servidor; }
    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }
    public RangoRequest getRangoMin() { return rangoMin; }
    public void setRangoMin(RangoRequest rangoMin) { this.rangoMin = rangoMin; }
    public RangoRequest getRangoMax() { return rangoMax; }
    public void setRangoMax(RangoRequest rangoMax) { this.rangoMax = rangoMax; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public void setLatenciaMaxMs(int latenciaMaxMs) { this.latenciaMaxMs = latenciaMaxMs; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getModalidadNombre() { return modalidadNombre; }
    public void setModalidadNombre(String modalidadNombre) { this.modalidadNombre = modalidadNombre; }
    public UUID getOrganizadorId() { return organizadorId; }
    public void setOrganizadorId(UUID organizadorId) { this.organizadorId = organizadorId; }

    public static class RangoRequest {
        private String juego;
        private String tier;
        private int numerico;

        public String getJuego() { return juego; }
        public void setJuego(String juego) { this.juego = juego; }
        public String getTier() { return tier; }
        public void setTier(String tier) { this.tier = tier; }
        public int getNumerico() { return numerico; }
        public void setNumerico(int numerico) { this.numerico = numerico; }
    }
}

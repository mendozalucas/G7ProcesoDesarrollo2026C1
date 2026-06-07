package com.escrims.application.dto;

import com.escrims.domain.valueobjects.Modalidad;
import com.escrims.domain.valueobjects.Rango;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateScrimDTO {

    private String juego;
    private int jugadoresPorLado;
    private String servidor;
    private String zona;
    private Rango rangoMin;
    private Rango rangoMax;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private int duracionMinutos;
    private Modalidad modalidad;
    private String modalidadNombre;
    private UUID organizadorId;

    public CreateScrimDTO() {}

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public int getJugadoresPorLado() { return jugadoresPorLado; }
    public void setJugadoresPorLado(int v) { this.jugadoresPorLado = v; }
    public String getServidor() { return servidor; }
    public void setServidor(String s) { this.servidor = s; }
    public String getZona() { return zona; }
    public void setZona(String z) { this.zona = z; }
    public Rango getRangoMin() { return rangoMin; }
    public void setRangoMin(Rango r) { this.rangoMin = r; }
    public Rango getRangoMax() { return rangoMax; }
    public void setRangoMax(Rango r) { this.rangoMax = r; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public void setLatenciaMaxMs(int v) { this.latenciaMaxMs = v; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime dt) { this.fechaHora = dt; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int v) { this.duracionMinutos = v; }
    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad m) { this.modalidad = m; }
    public String getModalidadNombre() { return modalidadNombre; }
    public void setModalidadNombre(String modalidadNombre) { this.modalidadNombre = modalidadNombre; }
    public UUID getOrganizadorId() { return organizadorId; }
    public void setOrganizadorId(UUID id) { this.organizadorId = id; }
}

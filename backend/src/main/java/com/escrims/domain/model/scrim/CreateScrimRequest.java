package com.escrims.domain.model.scrim;

import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;

import java.time.LocalDateTime;
import java.util.UUID;

public class CreateScrimRequest {

    private String juego;
    private FormatoScrim formato;
    private String modalidad;
    private Region region;
    private Rango rangoMin;
    private Rango rangoMax;
    private int latenciaMaxMs;
    private LocalDateTime fechaHora;
    private int duracionMinutos;

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public FormatoScrim getFormato() { return formato; }
    public void setFormato(FormatoScrim formato) { this.formato = formato; }
    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }
    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }
    public Rango getRangoMin() { return rangoMin; }
    public void setRangoMin(Rango rangoMin) { this.rangoMin = rangoMin; }
    public Rango getRangoMax() { return rangoMax; }
    public void setRangoMax(Rango rangoMax) { this.rangoMax = rangoMax; }
    public int getLatenciaMaxMs() { return latenciaMaxMs; }
    public void setLatenciaMaxMs(int latenciaMaxMs) { this.latenciaMaxMs = latenciaMaxMs; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
}

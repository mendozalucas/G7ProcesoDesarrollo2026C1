package com.escrims.application.dto;

public class SaveBusquedaFavoritaCommand {

    private String juego;
    private Integer rangoMin;
    private Integer rangoMax;
    private String servidor;
    private String zona;
    private String rolBuscado;
    private boolean activarAlerta;

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public Integer getRangoMin() { return rangoMin; }
    public void setRangoMin(Integer rangoMin) { this.rangoMin = rangoMin; }
    public Integer getRangoMax() { return rangoMax; }
    public void setRangoMax(Integer rangoMax) { this.rangoMax = rangoMax; }
    public String getServidor() { return servidor; }
    public void setServidor(String servidor) { this.servidor = servidor; }
    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }
    public String getRolBuscado() { return rolBuscado; }
    public void setRolBuscado(String rolBuscado) { this.rolBuscado = rolBuscado; }
    public boolean isActivarAlerta() { return activarAlerta; }
    public void setActivarAlerta(boolean activarAlerta) { this.activarAlerta = activarAlerta; }
}

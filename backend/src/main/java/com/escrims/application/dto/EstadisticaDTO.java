package com.escrims.application.dto;

import java.util.UUID;

public class EstadisticaDTO {

    private UUID usuarioId;
    private boolean esMvp;
    private int kills;
    private int deaths;
    private int assists;
    private String observaciones;

    public EstadisticaDTO() {}

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public boolean isEsMvp() { return esMvp; }
    public void setEsMvp(boolean esMvp) { this.esMvp = esMvp; }
    public int getKills() { return kills; }
    public void setKills(int kills) { this.kills = kills; }
    public int getDeaths() { return deaths; }
    public void setDeaths(int deaths) { this.deaths = deaths; }
    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

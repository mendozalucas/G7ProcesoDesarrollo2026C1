package com.escrims.domain.model.estadistica;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.KDA;

import java.util.UUID;

public class Estadistica {

    private final UUID id;
    private final Usuario usuario;
    private final UUID scrimId;
    private boolean esMvp;
    private int kills;
    private int deaths;
    private int assists;
    private String observaciones;
    private String estadoFeedback;

    public Estadistica(Usuario usuario, UUID scrimId, int kills, int deaths, int assists,
                       boolean esMvp, String observaciones) {
        this.id = UUID.randomUUID();
        this.usuario = usuario;
        this.scrimId = scrimId;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.esMvp = esMvp;
        this.observaciones = observaciones;
        this.estadoFeedback = "PENDIENTE";
    }

    public double calcularKDA() {
        return getKda().calcularRatio();
    }

    public KDA getKda() {
        return new KDA(kills, deaths, assists);
    }

    public void aprobarFeedback()  { this.estadoFeedback = "APROBADO"; }
    public void rechazarFeedback() { this.estadoFeedback = "RECHAZADO"; }

    public UUID getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public UUID getUsuarioId() { return usuario.getId(); }
    public UUID getScrimId() { return scrimId; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getAssists() { return assists; }
    public boolean isEsMvp() { return esMvp; }
    public String getObservaciones() { return observaciones; }
    public String getEstadoFeedback() { return estadoFeedback; }
}

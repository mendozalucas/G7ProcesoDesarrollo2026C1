package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "estadisticas")
public class EstadisticaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID usuarioId;

    @Column(nullable = false)
    private UUID scrimId;

    private boolean esMvp;
    private int kills;
    private int deaths;
    private int assists;
    private String observaciones;
    private String estadoFeedback;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public UUID getScrimId() { return scrimId; }
    public void setScrimId(UUID scrimId) { this.scrimId = scrimId; }
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
    public String getEstadoFeedback() { return estadoFeedback; }
    public void setEstadoFeedback(String estadoFeedback) { this.estadoFeedback = estadoFeedback; }
}

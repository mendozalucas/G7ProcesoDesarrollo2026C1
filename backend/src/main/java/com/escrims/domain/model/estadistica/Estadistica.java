package com.escrims.domain.model.estadistica;

import com.escrims.domain.model.usuario.Usuario;

public class Estadistica {

    private Long id;
    private Usuario usuario;
    private int kills;
    private int deaths;
    private int assists;
    private String observaciones;

    public Estadistica(Long id, Usuario usuario, int kills, int deaths, int assists,
                       String observaciones) {
        this.id = id;
        this.usuario = usuario;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.observaciones = observaciones;
    }

    public double calcularKDA() {
        return deaths == 0 ? kills + assists : (double)(kills + assists) / deaths;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public int getKills() { return kills; }
    public int getDeaths() { return deaths; }
    public int getAssists() { return assists; }
    public String getObservaciones() { return observaciones; }
}

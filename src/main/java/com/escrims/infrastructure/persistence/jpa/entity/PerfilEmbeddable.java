package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class PerfilEmbeddable {

    private String juego;
    private String servidor;
    private String zona;
    private Integer mmr;
    private String rolesPreferidos;

    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public String getServidor() { return servidor; }
    public void setServidor(String servidor) { this.servidor = servidor; }
    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }
    public Integer getMmr() { return mmr; }
    public void setMmr(Integer mmr) { this.mmr = mmr; }
    public String getRolesPreferidos() { return rolesPreferidos; }
    public void setRolesPreferidos(String rolesPreferidos) { this.rolesPreferidos = rolesPreferidos; }
}

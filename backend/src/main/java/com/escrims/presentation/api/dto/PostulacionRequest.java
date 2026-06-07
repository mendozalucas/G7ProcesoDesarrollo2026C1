package com.escrims.presentation.api.dto;

import java.util.UUID;

public class PostulacionRequest {

    private UUID usuarioId;
    private String juego;
    private String rol;

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getJuego() { return juego; }
    public void setJuego(String juego) { this.juego = juego; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

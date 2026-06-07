package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class ParticipanteEmbeddable {

    private String lado;
    private UUID usuarioId;
    private String rolJuego;
    private String rolNombre;

    public String getLado() { return lado; }
    public void setLado(String lado) { this.lado = lado; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
    public String getRolJuego() { return rolJuego; }
    public void setRolJuego(String rolJuego) { this.rolJuego = rolJuego; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
}

package com.escrims.domain.valueobjects;

import com.escrims.domain.model.usuario.Usuario;

import java.util.Objects;
import java.util.UUID;

public final class CandidatoMatchmaking {

    private final Usuario usuario;
    private final int mmr;
    private final int pingMs;
    private final String rolNombre;
    private final int strikes;
    private final double kdaPromedio;

    public CandidatoMatchmaking(Usuario usuario, int mmr, int pingMs, String rolNombre,
                                int strikes, double kdaPromedio) {
        this.usuario = Objects.requireNonNull(usuario);
        this.mmr = mmr;
        this.pingMs = pingMs;
        this.rolNombre = rolNombre != null ? rolNombre : "";
        this.strikes = Math.max(0, strikes);
        this.kdaPromedio = kdaPromedio;
    }

    public Usuario getUsuario() { return usuario; }
    public UUID getUsuarioId() { return usuario.getId(); }
    public int getMmr() { return mmr; }
    public int getPingMs() { return pingMs; }
    public String getRolNombre() { return rolNombre; }
    public int getStrikes() { return strikes; }
    public double getKdaPromedio() { return kdaPromedio; }
}

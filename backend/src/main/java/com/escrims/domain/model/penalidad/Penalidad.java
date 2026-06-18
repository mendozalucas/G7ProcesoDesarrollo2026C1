package com.escrims.domain.model.penalidad;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.moderation.ModerationHandler;

import java.time.LocalDateTime;

public class Penalidad {

    private Long id;
    private Usuario usuario;
    private ModerationHandler moderador;
    private TipoPenalidad tipo;
    private String motivo;
    private int strikes;
    private LocalDateTime cooldownHasta;

    public Penalidad(Long id, Usuario usuario, ModerationHandler moderador,
                     TipoPenalidad tipo, String motivo, int strikes, LocalDateTime cooldownHasta) {
        this.id = id;
        this.usuario = usuario;
        this.moderador = moderador;
        this.tipo = tipo;
        this.motivo = motivo;
        this.strikes = strikes;
        this.cooldownHasta = cooldownHasta;
    }

    public void aplicar() {}

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public ModerationHandler getModerador() { return moderador; }
    public TipoPenalidad getTipo() { return tipo; }
    public String getMotivo() { return motivo; }
    public int getStrikes() { return strikes; }
    public LocalDateTime getCooldownHasta() { return cooldownHasta; }
}

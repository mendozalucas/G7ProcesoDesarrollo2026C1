package com.escrims.domain.strategy.sancion;

import com.escrims.domain.model.usuario.Usuario;

import java.time.LocalDateTime;

public class CooldownSancion implements SancionEstrategia {

    private final int diasCooldown;

    public CooldownSancion(int diasCooldown) {
        this.diasCooldown = diasCooldown;
    }

    @Override
    public void aplicar(Usuario usuario) {
        usuario.aplicarCooldown(LocalDateTime.now().plusDays(diasCooldown));
    }

    @Override
    public String getNombre() { return "COOLDOWN_" + diasCooldown + "d"; }
}

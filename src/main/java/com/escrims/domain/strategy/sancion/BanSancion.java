package com.escrims.domain.strategy.sancion;

import com.escrims.domain.model.usuario.Usuario;

import java.time.LocalDateTime;

public class BanSancion implements SancionEstrategia {

    @Override
    public void aplicar(Usuario usuario) {
        usuario.aplicarCooldown(LocalDateTime.now().plusYears(100));
    }

    @Override
    public String getNombre() { return "BAN"; }
}

package com.escrims.domain.strategy.sancion;

import com.escrims.domain.model.usuario.Usuario;

public class StrikeSancion implements SancionEstrategia {

    @Override
    public void aplicar(Usuario usuario) {
        usuario.agregarStrike();
    }

    @Override
    public String getNombre() { return "STRIKE"; }
}

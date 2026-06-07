package com.escrims.domain.strategy.sancion;

import com.escrims.domain.model.usuario.Usuario;

public class WarningSancion implements SancionEstrategia {

    @Override
    public void aplicar(Usuario usuario) {
        // Un warning no agrega strike ni cooldown, solo queda registrado
        System.out.printf("[Sancion] WARNING aplicado a usuario %s%n", usuario.getId());
    }

    @Override
    public String getNombre() { return "WARNING"; }
}

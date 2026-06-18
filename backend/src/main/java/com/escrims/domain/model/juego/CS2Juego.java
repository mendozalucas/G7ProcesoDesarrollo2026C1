package com.escrims.domain.model.juego;

import com.escrims.domain.model.scrim.Scrim;

public class CS2Juego extends Juego {

    public CS2Juego() {
        super("cs2");
    }

    @Override
    public boolean validarRoles(Scrim scrim) {
        return true;
    }

    @Override
    public boolean validarRangos(Scrim scrim) {
        return true;
    }

    @Override
    public boolean validarReglasEspecificas(Scrim scrim) {
        return true;
    }
}

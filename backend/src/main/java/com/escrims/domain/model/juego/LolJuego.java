package com.escrims.domain.model.juego;

import com.escrims.domain.model.scrim.Scrim;

public class LolJuego extends Juego {

    public LolJuego() {
        super("lol");
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

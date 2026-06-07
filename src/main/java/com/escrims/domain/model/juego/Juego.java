package com.escrims.domain.model.juego;

import com.escrims.domain.model.scrim.Scrim;

/**
 * Clase abstracta del patrón Template Method (ver diagrama: ValorantJuego, LolJuego, CS2Juego).
 */
public abstract class Juego {

    private final String nombre;

    protected Juego(String nombre) {
        this.nombre = nombre;
    }

    public final boolean validar(Scrim scrim) {
        return validarRoles(scrim) && validarRangos(scrim) && validarReglasEspecificas(scrim);
    }

    public abstract boolean validarRoles(Scrim scrim);

    public abstract boolean validarRangos(Scrim scrim);

    public abstract boolean validarReglasEspecificas(Scrim scrim);

    public String getNombre() {
        return nombre;
    }
}

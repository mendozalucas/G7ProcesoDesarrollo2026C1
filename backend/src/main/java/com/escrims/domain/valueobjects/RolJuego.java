package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class RolJuego {

    private final String juego;
    private final String nombreRol;

    public RolJuego(String juego, String nombreRol) {
        this.juego = juego;
        this.nombreRol = nombreRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RolJuego)) return false;
        RolJuego rol = (RolJuego) o;
        return Objects.equals(juego, rol.juego) && Objects.equals(nombreRol, rol.nombreRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(juego, nombreRol);
    }

    public String getJuego() { return juego; }
    public String getNombreRol() { return nombreRol; }

    @Override
    public String toString() {
        return juego + ":" + nombreRol;
    }
}

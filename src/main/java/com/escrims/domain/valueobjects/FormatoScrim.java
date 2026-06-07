package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class FormatoScrim {

    private final int jugadoresPorLado;

    public FormatoScrim(int jugadoresPorLado) {
        if (jugadoresPorLado < 1) throw new IllegalArgumentException("Jugadores por lado debe ser >= 1");
        this.jugadoresPorLado = jugadoresPorLado;
    }

    public int getJugadoresPorLado() { return jugadoresPorLado; }
    public int getTotalJugadores() { return jugadoresPorLado * 2; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormatoScrim)) return false;
        return jugadoresPorLado == ((FormatoScrim) o).jugadoresPorLado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jugadoresPorLado);
    }

    @Override
    public String toString() {
        return jugadoresPorLado + "v" + jugadoresPorLado;
    }
}

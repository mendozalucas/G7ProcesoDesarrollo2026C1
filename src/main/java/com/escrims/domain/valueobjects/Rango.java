package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class Rango implements Comparable<Rango> {

    private final String juego;
    private final String tier;
    private final int numerico;

    public Rango(String juego, String tier, int numerico) {
        this.juego = juego;
        this.tier = tier;
        this.numerico = numerico;
    }

    public boolean estaEntre(Rango min, Rango max) {
        return this.numerico >= min.numerico && this.numerico <= max.numerico;
    }

    @Override
    public int compareTo(Rango otro) {
        return Integer.compare(this.numerico, otro.numerico);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rango)) return false;
        Rango rango = (Rango) o;
        return numerico == rango.numerico
                && Objects.equals(juego, rango.juego)
                && Objects.equals(tier, rango.tier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(juego, tier, numerico);
    }

    public String getJuego() { return juego; }
    public String getTier() { return tier; }
    public int getNumerico() { return numerico; }

    @Override
    public String toString() {
        return juego + ":" + tier + "(" + numerico + ")";
    }
}

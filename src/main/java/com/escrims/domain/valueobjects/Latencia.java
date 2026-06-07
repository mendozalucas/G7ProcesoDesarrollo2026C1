package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class Latencia {

    private final int pingMs;

    public Latencia(int pingMs) {
        if (pingMs < 0) throw new IllegalArgumentException("El ping no puede ser negativo");
        this.pingMs = pingMs;
    }

    public boolean estaEnUmbral(Latencia max) {
        return this.pingMs <= max.pingMs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Latencia)) return false;
        return pingMs == ((Latencia) o).pingMs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pingMs);
    }

    public int getPingMs() { return pingMs; }

    @Override
    public String toString() {
        return pingMs + "ms";
    }
}

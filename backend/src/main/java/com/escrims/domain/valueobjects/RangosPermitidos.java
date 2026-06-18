package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class RangosPermitidos {

    private final Rango min;
    private final Rango max;

    public RangosPermitidos(Rango min, Rango max) {
        if (min.getMmr() > max.getMmr()) throw new IllegalArgumentException("Rango min no puede ser mayor que max");
        this.min = min;
        this.max = max;
    }

    public boolean contiene(Rango rango) {
        return rango != null && rango.getMmr() >= min.getMmr() && rango.getMmr() <= max.getMmr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RangosPermitidos)) return false;
        RangosPermitidos that = (RangosPermitidos) o;
        return Objects.equals(min, that.min) && Objects.equals(max, that.max);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    public Rango getMin() { return min; }
    public Rango getMax() { return max; }
}

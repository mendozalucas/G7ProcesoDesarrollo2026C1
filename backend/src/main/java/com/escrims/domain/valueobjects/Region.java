package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class Region {

    private final String servidor;
    private final String zona;

    public Region(String servidor, String zona) {
        this.servidor = servidor;
        this.zona = zona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return Objects.equals(servidor, region.servidor)
                && Objects.equals(zona, region.zona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(servidor, zona);
    }

    public String getServidor() { return servidor; }
    public String getZona() { return zona; }

    @Override
    public String toString() {
        return servidor + "/" + zona;
    }
}

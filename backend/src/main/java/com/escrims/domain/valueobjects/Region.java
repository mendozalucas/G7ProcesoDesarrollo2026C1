package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class Region {

    private Long id;
    private String nombre;

    public Region(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;
        Region region = (Region) o;
        return Objects.equals(nombre, region.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}

package com.escrims.domain.valueobjects;

import java.util.Objects;

/**
 * Value object extensible que reemplaza al enum LadoEquipo.
 * Permite agregar nuevos lados o comportamientos sin modificar esta clase.
 */
public final class LadoEquipo {

    public static final LadoEquipo EQUIPO_A = new LadoEquipo("EQUIPO_A");
    public static final LadoEquipo EQUIPO_B = new LadoEquipo("EQUIPO_B");

    private final String nombre;

    private LadoEquipo(String nombre) {
        this.nombre = nombre;
    }

    public static LadoEquipo de(String nombre) {
        return new LadoEquipo(nombre);
    }

    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LadoEquipo)) return false;
        return Objects.equals(nombre, ((LadoEquipo) o).nombre);
    }

    @Override
    public int hashCode() { return Objects.hash(nombre); }

    @Override
    public String toString() { return nombre; }
}

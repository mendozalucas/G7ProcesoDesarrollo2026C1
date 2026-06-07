package com.escrims.domain.valueobjects;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Value Object.
 * Removido CanalNotificacion (enum): el canal preferido
 * queda encapsulado en la Abstract Factory al momento de notificar.
 */
public final class PreferenciasUsuario {

    private final List<String> juegosPreferidos;
    private final boolean recibirAlertas;

    public PreferenciasUsuario(List<String> juegosPreferidos, boolean recibirAlertas) {
        this.juegosPreferidos = Collections.unmodifiableList(juegosPreferidos);
        this.recibirAlertas = recibirAlertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreferenciasUsuario)) return false;
        PreferenciasUsuario that = (PreferenciasUsuario) o;
        return recibirAlertas == that.recibirAlertas
                && Objects.equals(juegosPreferidos, that.juegosPreferidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(juegosPreferidos, recibirAlertas);
    }

    public List<String> getJuegosPreferidos() { return juegosPreferidos; }
    public boolean isRecibirAlertas() { return recibirAlertas; }
}

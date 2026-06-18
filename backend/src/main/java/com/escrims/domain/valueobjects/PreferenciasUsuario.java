package com.escrims.domain.valueobjects;

import java.util.Objects;

public final class PreferenciasUsuario {

    private final DisponibilidadHoraria disponibilidadHoraria;
    private final boolean recibirEmail;
    private final boolean recibirPush;

    public PreferenciasUsuario(DisponibilidadHoraria disponibilidadHoraria,
                                boolean recibirEmail, boolean recibirPush) {
        this.disponibilidadHoraria = disponibilidadHoraria;
        this.recibirEmail = recibirEmail;
        this.recibirPush = recibirPush;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreferenciasUsuario)) return false;
        PreferenciasUsuario that = (PreferenciasUsuario) o;
        return recibirEmail == that.recibirEmail
                && recibirPush == that.recibirPush
                && Objects.equals(disponibilidadHoraria, that.disponibilidadHoraria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(disponibilidadHoraria, recibirEmail, recibirPush);
    }

    public DisponibilidadHoraria getDisponibilidadHoraria() { return disponibilidadHoraria; }
    public boolean isRecibirEmail() { return recibirEmail; }
    public boolean isRecibirPush() { return recibirPush; }
}

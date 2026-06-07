package com.escrims.domain.valueobjects;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public final class DisponibilidadHoraria {

    private final DayOfWeek dia;
    private final LocalTime inicio;
    private final LocalTime fin;

    public DisponibilidadHoraria(DayOfWeek dia, LocalTime inicio, LocalTime fin) {
        if (inicio.isAfter(fin)) throw new IllegalArgumentException("Hora de inicio debe ser anterior al fin");
        this.dia = dia;
        this.inicio = inicio;
        this.fin = fin;
    }

    public boolean cubre(DayOfWeek dia, LocalTime hora) {
        return this.dia == dia && !hora.isBefore(inicio) && !hora.isAfter(fin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisponibilidadHoraria)) return false;
        DisponibilidadHoraria that = (DisponibilidadHoraria) o;
        return dia == that.dia && Objects.equals(inicio, that.inicio) && Objects.equals(fin, that.fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dia, inicio, fin);
    }

    public DayOfWeek getDia() { return dia; }
    public LocalTime getInicio() { return inicio; }
    public LocalTime getFin() { return fin; }
}

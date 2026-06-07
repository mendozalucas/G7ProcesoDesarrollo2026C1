package com.escrims.domain.valueobjects;

public class ModalidadPracticaEstratos implements Modalidad {

    @Override public String getNombre()       { return "PRACTICA_ESTRATOS"; }
    @Override public boolean permiteRanking() { return false; }
    @Override public String descripcion()     { return "Sesión de práctica de estrategias y coordinación de equipo"; }
}

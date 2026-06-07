package com.escrims.domain.valueobjects;

public class ModalidadCasual implements Modalidad {

    @Override public String getNombre()       { return "CASUAL"; }
    @Override public boolean permiteRanking() { return false; }
    @Override public String descripcion()     { return "Partida amistosa sin impacto en ranking"; }
}

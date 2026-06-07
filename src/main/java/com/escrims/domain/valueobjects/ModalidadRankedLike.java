package com.escrims.domain.valueobjects;

public class ModalidadRankedLike implements Modalidad {

    @Override public String getNombre()       { return "RANKED_LIKE"; }
    @Override public boolean permiteRanking() { return true; }
    @Override public String descripcion()     { return "Partida competitiva con impacto en ranking interno"; }
}

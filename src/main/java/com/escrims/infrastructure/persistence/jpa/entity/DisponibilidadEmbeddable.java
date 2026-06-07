package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class DisponibilidadEmbeddable {

    private String dia;
    private String inicio;
    private String fin;

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    public String getInicio() { return inicio; }
    public void setInicio(String inicio) { this.inicio = inicio; }
    public String getFin() { return fin; }
    public void setFin(String fin) { this.fin = fin; }
}

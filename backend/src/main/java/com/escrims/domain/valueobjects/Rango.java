package com.escrims.domain.valueobjects;

public final class Rango {

    private Long id;
    private String nombre;
    private int mmr;

    public Rango(Long id, String nombre, int mmr) {
        this.id = id;
        this.nombre = nombre;
        this.mmr = mmr;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public int getMmr() { return mmr; }
}

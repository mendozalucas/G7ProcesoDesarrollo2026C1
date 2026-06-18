package com.escrims.domain.valueobjects;

public final class Region {

    private Long id;
    private String nombre;

    public Region(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}

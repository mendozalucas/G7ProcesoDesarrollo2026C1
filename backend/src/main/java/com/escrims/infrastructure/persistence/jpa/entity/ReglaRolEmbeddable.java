package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class ReglaRolEmbeddable {

    private String rolJuego;
    private String rolNombre;
    private int cantidad;

    public String getRolJuego() { return rolJuego; }
    public void setRolJuego(String rolJuego) { this.rolJuego = rolJuego; }
    public String getRolNombre() { return rolNombre; }
    public void setRolNombre(String rolNombre) { this.rolNombre = rolNombre; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}

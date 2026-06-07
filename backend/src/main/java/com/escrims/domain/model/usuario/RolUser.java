package com.escrims.domain.model.usuario;

public class RolUser implements RolSistema {

    @Override public boolean puedeModerar()    { return false; }
    @Override public boolean puedeAdministrar() { return false; }
    @Override public String getNombre()         { return "USER"; }
}

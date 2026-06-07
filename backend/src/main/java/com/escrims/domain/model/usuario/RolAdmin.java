package com.escrims.domain.model.usuario;

public class RolAdmin implements RolSistema {

    @Override public boolean puedeModerar()    { return true; }
    @Override public boolean puedeAdministrar() { return true; }
    @Override public String getNombre()         { return "ADMIN"; }
}

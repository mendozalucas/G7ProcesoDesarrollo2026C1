package com.escrims.domain.model.usuario;

public class RolModerador implements RolSistema {

    @Override public boolean puedeModerar()    { return true; }
    @Override public boolean puedeAdministrar() { return false; }
    @Override public String getNombre()         { return "MOD"; }
}

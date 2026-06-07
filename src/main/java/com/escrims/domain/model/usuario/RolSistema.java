package com.escrims.domain.model.usuario;

public interface RolSistema {

    boolean puedeModerar();

    boolean puedeAdministrar();

    String getNombre();
}

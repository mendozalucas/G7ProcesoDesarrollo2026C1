package com.escrims.domain.strategy.sancion;

import com.escrims.domain.model.usuario.Usuario;

public interface SancionEstrategia {

    void aplicar(Usuario usuario);

    String getNombre();
}

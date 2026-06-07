package com.escrims.domain.state.postulacion;

import com.escrims.domain.model.postulacion.Postulacion;

public interface PostulacionState {

    void aceptar(Postulacion ctx);

    void rechazar(Postulacion ctx);

    String getNombre();
}

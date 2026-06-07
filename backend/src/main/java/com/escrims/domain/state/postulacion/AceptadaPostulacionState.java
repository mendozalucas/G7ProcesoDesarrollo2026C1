package com.escrims.domain.state.postulacion;

import com.escrims.domain.model.postulacion.Postulacion;

public class AceptadaPostulacionState implements PostulacionState {

    @Override
    public void aceptar(Postulacion ctx) {
        throw new IllegalStateException("La postulación ya fue aceptada");
    }

    @Override
    public void rechazar(Postulacion ctx) {
        throw new IllegalStateException("No se puede rechazar una postulación ya aceptada");
    }

    @Override
    public String getNombre() { return "ACEPTADA"; }
}

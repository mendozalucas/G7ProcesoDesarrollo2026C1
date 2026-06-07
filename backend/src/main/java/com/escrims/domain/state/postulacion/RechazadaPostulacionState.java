package com.escrims.domain.state.postulacion;

import com.escrims.domain.model.postulacion.Postulacion;

public class RechazadaPostulacionState implements PostulacionState {

    @Override
    public void aceptar(Postulacion ctx) {
        throw new IllegalStateException("No se puede aceptar una postulación rechazada");
    }

    @Override
    public void rechazar(Postulacion ctx) {
        throw new IllegalStateException("La postulación ya fue rechazada");
    }

    @Override
    public String getNombre() { return "RECHAZADA"; }
}

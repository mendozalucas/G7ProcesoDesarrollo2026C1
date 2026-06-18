package com.escrims.domain.state.postulacion;

import com.escrims.domain.model.postulacion.Postulacion;

public class PendientePostulacionState implements PostulacionState {

    @Override
    public void aceptar(Postulacion ctx) {
        ctx.aceptar();
    }

    @Override
    public void rechazar(Postulacion ctx) {
        ctx.rechazar();
    }

    @Override
    public String getNombre() { return "PENDIENTE"; }
}

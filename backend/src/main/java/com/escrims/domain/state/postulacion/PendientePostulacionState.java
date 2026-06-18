package com.escrims.domain.state.postulacion;

import com.escrims.domain.model.postulacion.Postulacion;

public class PendientePostulacionState implements PostulacionState {

    @Override
    public void aceptar(Postulacion ctx) {
        ctx.transicionarA(new AceptadaPostulacionState());
    }

    @Override
    public void rechazar(Postulacion ctx) {
        ctx.transicionarA(new RechazadaPostulacionState());
    }

    @Override
    public String getNombre() { return "PENDIENTE"; }
}

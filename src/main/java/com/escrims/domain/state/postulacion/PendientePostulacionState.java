package com.escrims.domain.state.postulacion;

import com.escrims.domain.events.PostulacionAceptadaEvent;
import com.escrims.domain.events.PostulacionRechazadaEvent;
import com.escrims.domain.model.postulacion.Postulacion;

public class PendientePostulacionState implements PostulacionState {

    @Override
    public void aceptar(Postulacion ctx) {
        ctx.cambiarEstado(new AceptadaPostulacionState());
        ctx.agregarEvento(new PostulacionAceptadaEvent(ctx.getId(), ctx.getUsuarioId(), ctx.getScrimId()));
    }

    @Override
    public void rechazar(Postulacion ctx) {
        ctx.cambiarEstado(new RechazadaPostulacionState());
        ctx.agregarEvento(new PostulacionRechazadaEvent(ctx.getId(), ctx.getUsuarioId(), ctx.getScrimId()));
    }

    @Override
    public String getNombre() { return "PENDIENTE"; }
}

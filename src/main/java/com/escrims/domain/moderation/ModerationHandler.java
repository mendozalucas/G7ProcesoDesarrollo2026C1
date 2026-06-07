package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

public abstract class ModerationHandler {

    private ModerationHandler sucesor;

    public ModerationHandler setNext(ModerationHandler handler) {
        this.sucesor = handler;
        return handler;
    }

    public final void handle(ReporteConducta reporte) {
        doHandle(reporte);
    }

    protected abstract void doHandle(ReporteConducta reporte);

    protected void pasarAlSiguiente(ReporteConducta reporte) {
        if (sucesor != null) {
            sucesor.handle(reporte);
        } else {
            throw new IllegalStateException("Ningún handler pudo procesar el reporte: " + reporte.getId());
        }
    }
}

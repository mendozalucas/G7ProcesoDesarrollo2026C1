package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

public abstract class ModerationHandler {

    private ModerationHandler next;

    public ModerationHandler setNext(ModerationHandler handler) {
        this.next = handler;
        return handler;
    }

    public abstract void handle(ReporteConducta reporte);

    protected void pasarAlSiguiente(ReporteConducta reporte) {
        if (next != null) {
            next.handle(reporte);
        }
    }

    public ModerationHandler getNext() { return next; }
}

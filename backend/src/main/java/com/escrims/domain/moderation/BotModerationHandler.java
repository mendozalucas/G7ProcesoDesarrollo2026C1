package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

public class BotModerationHandler extends ModerationHandler {

    private static final int LONGITUD_MINIMA = 20;

    @Override
    public void handle(ReporteConducta reporte) {
        if (puedeResolver(reporte)) {
            reporte.resolver();
        } else {
            pasarAlSiguiente(reporte);
        }
    }

    private boolean puedeResolver(ReporteConducta reporte) {
        return reporte.getMotivo() != null
                && reporte.getMotivo().length() >= LONGITUD_MINIMA
                && reporte.getMotivo().toLowerCase().contains("no-show");
    }
}

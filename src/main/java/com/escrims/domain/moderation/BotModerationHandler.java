package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.strategy.sancion.StrikeSancion;

public class BotModerationHandler extends ModerationHandler {

    private static final int LONGITUD_MINIMA = 20;

    @Override
    protected void doHandle(ReporteConducta reporte) {
        if (puedeResolver(reporte)) {
            reporte.asignarSancion(new StrikeSancion());
            reporte.resolver("RESUELTO_BOT");
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

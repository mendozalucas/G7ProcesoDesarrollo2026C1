package com.escrims.domain.moderation;

import com.escrims.domain.model.reporte.ReporteConducta;

import java.util.Set;

public class AutoModerationHandler extends ModerationHandler {

    private static final Set<String> PALABRAS_CLAVE = Set.of("spam", "bot", "flood", "cheat");

    @Override
    public void handle(ReporteConducta reporte) {
        String motivo = reporte.getMotivo();
        if (motivo == null || motivo.isBlank()) {
            pasarAlSiguiente(reporte);
            return;
        }
        boolean esObvio = PALABRAS_CLAVE.stream()
                .anyMatch(p -> motivo.toLowerCase().contains(p));
        if (esObvio) {
            reporte.resolver();
        } else {
            pasarAlSiguiente(reporte);
        }
    }
}

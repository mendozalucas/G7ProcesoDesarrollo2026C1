package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;

import java.util.ArrayDeque;
import java.util.Deque;

public class ScrimCommandHistory {

    private final Deque<ScrimCommand> historial = new ArrayDeque<>();

    public void ejecutar(ScrimCommand comando, Scrim scrim) {
        comando.ejecutar(scrim);
        historial.push(comando);
    }

    public void deshacer(Scrim scrim) {
        if (historial.isEmpty()) {
            throw new IllegalStateException("No hay comandos para deshacer");
        }
        historial.pop().deshacer(scrim);
    }

    public boolean hayHistorial() {
        return !historial.isEmpty();
    }

    public void limpiar() {
        historial.clear();
    }
}

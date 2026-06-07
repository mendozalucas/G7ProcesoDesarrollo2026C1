package com.escrims.domain.command;

import com.escrims.domain.model.scrim.Scrim;

public interface ScrimCommand {

    void ejecutar(Scrim scrim);

    void deshacer(Scrim scrim);

    default void execute(Scrim scrim) {
        ejecutar(scrim);
    }

    default void undo(Scrim scrim) {
        deshacer(scrim);
    }
}

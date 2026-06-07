package com.escrims.domain.state;

import com.escrims.domain.model.scrim.Scrim;

public interface ScrimState {

    void avanzar(Scrim scrim);

    void cancelar(Scrim scrim);

    String getNombreEstado();
}

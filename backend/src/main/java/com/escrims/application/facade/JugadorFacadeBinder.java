package com.escrims.application.facade;

import com.escrims.domain.facade.LobbyFacadePort;
import com.escrims.domain.facade.ModeracionFacadePort;
import com.escrims.domain.facade.ScrimFacadePort;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Usuario;
import org.springframework.stereotype.Service;

@Service
public class JugadorFacadeBinder {

    private final ScrimFacadePort scrimFacade;
    private final LobbyFacadePort lobbyFacade;
    private final ModeracionFacadePort moderacionFacade;

    public JugadorFacadeBinder(ScrimFacade scrimFacade,
                               LobbyFacade lobbyFacade,
                               ModeracionFacade moderacionFacade) {
        this.scrimFacade = scrimFacade;
        this.lobbyFacade = lobbyFacade;
        this.moderacionFacade = moderacionFacade;
    }

    public Jugador vincular(Usuario usuario) {
        if (!(usuario instanceof Jugador jugador)) {
            throw new IllegalArgumentException("Solo un jugador puede vincular facades");
        }
        jugador.conectarFacades(scrimFacade, lobbyFacade, moderacionFacade);
        return jugador;
    }
}

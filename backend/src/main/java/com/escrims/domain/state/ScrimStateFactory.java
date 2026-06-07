package com.escrims.domain.state;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ScrimStateFactory {

    private static final Map<String, Supplier<ScrimState>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("LOBBY_ARMADO", LobbyArmadoState::new);
        REGISTRO.put("CONFIRMADO", ConfirmadoState::new);
        REGISTRO.put("EN_JUEGO", EnJuegoState::new);
        REGISTRO.put("FINALIZADO", FinalizadoState::new);
        REGISTRO.put("CANCELADO", CanceladoState::new);
        REGISTRO.put("BUSCANDO", BuscandoJugadoresState::new);
    }

    private ScrimStateFactory() {}

    public static ScrimState para(String nombre) {
        if (nombre == null) {
            return new BuscandoJugadoresState();
        }
        Supplier<ScrimState> proveedor = REGISTRO.get(nombre.toUpperCase());
        return proveedor != null ? proveedor.get() : new BuscandoJugadoresState();
    }
}

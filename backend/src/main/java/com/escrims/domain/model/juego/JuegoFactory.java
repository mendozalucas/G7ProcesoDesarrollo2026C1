package com.escrims.domain.model.juego;

public final class JuegoFactory {

    private JuegoFactory() {}

    public static Juego para(String nombreJuego) {
        if (nombreJuego == null) throw new IllegalArgumentException("Juego no puede ser null");
        switch (nombreJuego.toLowerCase()) {
            case "valorant": return new ValorantJuego();
            case "lol":      return new LolJuego();
            case "cs2":      return new CS2Juego();
            default:         throw new IllegalArgumentException("Juego no soportado: " + nombreJuego);
        }
    }
}

package com.escrims.support;

import com.escrims.domain.builder.ScrimBuilder;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.Moderador;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.MatchmakingContext;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class TestFixtures {

    private TestFixtures() {}

    public static Jugador jugador(String username) {
        return new Jugador(UUID.randomUUID(), username, username + "@test.local", "hash");
    }

    public static Moderador moderador(String username) {
        return new Moderador(UUID.randomUUID(), username, username + "@test.local", "hash");
    }

    public static Scrim scrim(Jugador organizador) {
        return scrim(organizador, new FormatoScrim(5));
    }

    public static Scrim scrim(Jugador organizador, FormatoScrim formato) {
        return new ScrimBuilder()
                .conJuego(JuegoFactory.para("valorant"))
                .conFormato(formato)
                .conModalidad("CASUAL")
                .conRegion(new Region(null, "LAN/AR"))
                .conRango(new Rango(null, "Gold", 1500), new Rango(null, "Plat", 2000))
                .conLatenciaMax(80)
                .conFechaHora(LocalDateTime.now().plusHours(1))
                .creadoPor(organizador)
                .build();
    }

    public static CandidatoMatchmaking candidato(Jugador jugador, int mmr, int pingMs, String rol) {
        return new CandidatoMatchmaking(jugador, mmr, pingMs, rol, 0, 1.5);
    }

    public static CandidatoMatchmaking candidato(Jugador jugador, int mmr, int pingMs, String rol,
                                                 int strikes, double kda) {
        return new CandidatoMatchmaking(jugador, mmr, pingMs, rol, strikes, kda);
    }

    public static MatchmakingContext matchmakingContext(Scrim scrim, List<CandidatoMatchmaking> candidatos) {
        return new MatchmakingContext(scrim, scrim.getFormato().getTotalJugadores(), candidatos);
    }
}

package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class MatchmakingSupport {

    private MatchmakingSupport() {}

    public static int mmrCentro(Scrim scrim) {
        return (scrim.getRangoMin().getMmr() + scrim.getRangoMax().getMmr()) / 2;
    }

    public static int anchoRangoMmr(Scrim scrim) {
        int ancho = scrim.getRangoMax().getMmr() - scrim.getRangoMin().getMmr();
        return Math.max(ancho, 1);
    }

    public static List<Usuario> aUsuarios(List<CandidatoMatchmaking> candidatos) {
        return candidatos.stream().map(CandidatoMatchmaking::getUsuario).collect(Collectors.toList());
    }

    public static List<Usuario> limitarCupo(List<CandidatoMatchmaking> ordenados, int cupo) {
        return ordenados.stream()
                .limit(cupo)
                .map(CandidatoMatchmaking::getUsuario)
                .collect(Collectors.toList());
    }

    public static Comparator<CandidatoMatchmaking> porCercaniaMmr(Scrim scrim) {
        int centro = mmrCentro(scrim);
        return Comparator.comparingInt(c -> Math.abs(c.getMmr() - centro));
    }
}

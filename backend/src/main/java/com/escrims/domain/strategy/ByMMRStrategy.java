package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;
import com.escrims.domain.valueobjects.MatchmakingContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Filtra por rango MMR del scrim y arma un grupo con diferencia máxima entre jugadores.
 */
public class ByMMRStrategy implements IMatchmakingStrategy {

    private final int diferenciaMMRMaxima;

    public ByMMRStrategy(int diferenciaMMRMaxima) {
        this.diferenciaMMRMaxima = Math.max(1, diferenciaMMRMaxima);
    }

    @Override
    public List<Usuario> seleccionar(MatchmakingContext context) {
        Scrim scrim = context.getScrim();
        int min = scrim.getRangoMin().getMmr();
        int max = scrim.getRangoMax().getMmr();

        List<CandidatoMatchmaking> enRango = context.getCandidatos().stream()
                .filter(c -> c.getMmr() >= min && c.getMmr() <= max)
                .sorted(MatchmakingSupport.porCercaniaMmr(scrim))
                .toList();

        if (enRango.isEmpty()) {
            throw new IllegalStateException(
                    "Ningún candidato aceptado tiene MMR dentro del rango " + min + "–" + max);
        }

        List<CandidatoMatchmaking> grupo = new ArrayList<>();
        for (CandidatoMatchmaking candidato : enRango) {
            if (grupo.size() >= context.getCupoMaximo()) {
                break;
            }
            if (grupo.isEmpty() || diferenciaGrupo(grupo, candidato) <= diferenciaMMRMaxima) {
                grupo.add(candidato);
            }
        }

        if (grupo.isEmpty()) {
            grupo.add(enRango.get(0));
        }

        return MatchmakingSupport.aUsuarios(grupo);
    }

    private int diferenciaGrupo(List<CandidatoMatchmaking> grupo, CandidatoMatchmaking candidato) {
        int min = grupo.stream().mapToInt(CandidatoMatchmaking::getMmr).min().orElse(candidato.getMmr());
        int max = grupo.stream().mapToInt(CandidatoMatchmaking::getMmr).max().orElse(candidato.getMmr());
        min = Math.min(min, candidato.getMmr());
        max = Math.max(max, candidato.getMmr());
        return max - min;
    }

    public int getDiferenciaMMRMaxima() { return diferenciaMMRMaxima; }
}

package com.escrims.domain.strategy;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;
import com.escrims.domain.valueobjects.MatchmakingContext;

import java.util.Comparator;
import java.util.List;

/**
 * Prioriza jugadores con menor ping dentro del umbral configurado.
 */
public class ByLatencyStrategy implements IMatchmakingStrategy {

    private final int latenciaMaxima;

    public ByLatencyStrategy(int latenciaMaxima) {
        this.latenciaMaxima = Math.max(1, latenciaMaxima);
    }

    @Override
    public List<Usuario> seleccionar(MatchmakingContext context) {
        int umbral = Math.min(latenciaMaxima, context.getScrim().getLatenciaMax());

        List<CandidatoMatchmaking> aptos = context.getCandidatos().stream()
                .filter(c -> c.getPingMs() <= umbral)
                .sorted(Comparator.comparingInt(CandidatoMatchmaking::getPingMs))
                .toList();

        if (aptos.isEmpty()) {
            throw new IllegalStateException(
                    "Ningún candidato cumple el umbral de latencia (" + umbral + " ms)");
        }

        return MatchmakingSupport.limitarCupo(aptos, context.getCupoMaximo());
    }

    public int getLatenciaMaxima() { return latenciaMaxima; }
}

package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.Latencia;
import com.escrims.domain.valueobjects.Region;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ByLatencyStrategy implements IMatchmakingStrategy {

    private final int latenciaMaxima;
    private final Scrim scrim;

    public ByLatencyStrategy(Scrim scrim) {
        this.scrim = scrim;
        this.latenciaMaxima = scrim.getLatenciaMax().getPingMs();
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos.stream()
                .filter(u -> !u.estaBloqueado())
                .filter(u -> {
                    Latencia lat = latenciaEstimada(u);
                    return lat != null && lat.getPingMs() <= latenciaMaxima;
                })
                .sorted(Comparator.comparingDouble(u -> latenciaEstimada(u).getPingMs()))
                .collect(Collectors.toList());
    }

    private Latencia latenciaEstimada(Usuario usuario) {
        Region regionUsuario = usuario.getRegion();
        if (regionUsuario != null && regionUsuario.equals(scrim.getRegion())) {
            return new Latencia(20);
        }
        return new Latencia(120);
    }
}

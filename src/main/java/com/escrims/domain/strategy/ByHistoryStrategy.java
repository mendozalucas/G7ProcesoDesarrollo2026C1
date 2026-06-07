package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ByHistoryStrategy implements IMatchmakingStrategy {

    private final double pesoSinergia;
    private final double penalizacionAbandono;
    private final double bonusFairPlay;
    private final Scrim scrim;

    public ByHistoryStrategy(double pesoSinergia, double penalizacionAbandono,
                              double bonusFairPlay, Scrim scrim) {
        this.pesoSinergia = pesoSinergia;
        this.penalizacionAbandono = penalizacionAbandono;
        this.bonusFairPlay = bonusFairPlay;
        this.scrim = scrim;
    }

    @Override
    public List<Usuario> seleccionar(List<Usuario> candidatos) {
        return candidatos.stream()
                .filter(u -> !u.estaBloqueado())
                .filter(u -> scrim.getRangosPermitidos().contiene(u.getRangoPara(scrim.getNombreJuego())))
                .sorted(Comparator.comparingDouble(u -> -calcularPuntuacion(u)))
                .collect(Collectors.toList());
    }

    private double calcularPuntuacion(Usuario usuario) {
        double score = 0;
        score += calcularSinergia(usuario) * pesoSinergia;
        score -= usuario.getStrikes() * penalizacionAbandono;
        score += calcularFairPlay(usuario) * bonusFairPlay;
        return score;
    }

    private double calcularSinergia(Usuario usuario) {
        return usuario.getPerfilesJuego().stream()
                .flatMap(p -> p.getRolesPreferidos().stream())
                .filter(r -> scrim.getReglasRoles().containsKey(r))
                .count();
    }

    private double calcularFairPlay(Usuario usuario) {
        return Math.max(0, 10 - usuario.getStrikes() * 3);
    }
}

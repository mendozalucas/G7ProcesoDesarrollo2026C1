package com.escrims.domain.strategy;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.valueobjects.CandidatoMatchmaking;
import com.escrims.domain.valueobjects.MatchmakingContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Puntúa candidatos por MMR, latencia e historial (strikes + KDA) y elige los mejores,
 * favoreciendo diversidad de roles.
 */
public class ByHistoryStrategy implements IMatchmakingStrategy {

    private final double pesoMMR;
    private final double pesoLatencia;
    private final double pesoHistorial;

    public ByHistoryStrategy(double pesoMMR, double pesoLatencia, double pesoHistorial) {
        this.pesoMMR = pesoMMR;
        this.pesoLatencia = pesoLatencia;
        this.pesoHistorial = pesoHistorial;
    }

    @Override
    public List<Usuario> seleccionar(MatchmakingContext context) {
        Scrim scrim = context.getScrim();
        int centroMmr = MatchmakingSupport.mmrCentro(scrim);
        int anchoMmr = MatchmakingSupport.anchoRangoMmr(scrim);
        int umbralPing = scrim.getLatenciaMax();

        int maxStrikes = context.getCandidatos().stream()
                .mapToInt(CandidatoMatchmaking::getStrikes)
                .max()
                .orElse(0);

        List<CandidatoMatchmaking> rankeados = context.getCandidatos().stream()
                .sorted(Comparator
                        .comparingDouble((CandidatoMatchmaking c) -> puntaje(
                                c, centroMmr, anchoMmr, umbralPing, maxStrikes))
                        .reversed()
                        .thenComparing(MatchmakingSupport.porCercaniaMmr(scrim)))
                .toList();

        List<CandidatoMatchmaking> seleccionados = elegirConRoles(rankeados, context.getCupoMaximo());

        if (seleccionados.isEmpty()) {
            throw new IllegalStateException("No hay candidatos para emparejar por historial");
        }

        return MatchmakingSupport.aUsuarios(seleccionados);
    }

    private List<CandidatoMatchmaking> elegirConRoles(List<CandidatoMatchmaking> rankeados, int cupo) {
        List<CandidatoMatchmaking> seleccionados = new ArrayList<>();
        Set<String> rolesUsados = new HashSet<>();
        Set<UUID> idsUsados = new HashSet<>();

        for (CandidatoMatchmaking candidato : rankeados) {
            if (seleccionados.size() >= cupo) {
                break;
            }
            String rol = candidato.getRolNombre();
            if (!rol.isBlank() && rolesUsados.contains(rol)) {
                continue;
            }
            seleccionados.add(candidato);
            idsUsados.add(candidato.getUsuarioId());
            if (!rol.isBlank()) {
                rolesUsados.add(rol);
            }
        }

        for (CandidatoMatchmaking candidato : rankeados) {
            if (seleccionados.size() >= cupo) {
                break;
            }
            if (!idsUsados.contains(candidato.getUsuarioId())) {
                seleccionados.add(candidato);
                idsUsados.add(candidato.getUsuarioId());
            }
        }

        return seleccionados;
    }

    private double puntaje(CandidatoMatchmaking c, int centroMmr, int anchoMmr,
                           int umbralPing, int maxStrikes) {
        double mmrFit = 1.0 - (Math.abs(c.getMmr() - centroMmr) / (double) anchoMmr);
        mmrFit = Math.max(0, Math.min(1, mmrFit));

        double latFit = umbralPing > 0
                ? 1.0 - (c.getPingMs() / (double) umbralPing)
                : 1.0;
        latFit = Math.max(0, Math.min(1, latFit));

        double strikePenalty = maxStrikes > 0 ? c.getStrikes() / (double) maxStrikes : 0;
        double kdaBonus = Math.min(c.getKdaPromedio() / 3.0, 1.0);
        double histFit = Math.max(0, 1.0 - strikePenalty + 0.2 * kdaBonus);

        return pesoMMR * mmrFit + pesoLatencia * latFit + pesoHistorial * histFit;
    }

    public double getPesoMMR() { return pesoMMR; }
    public double getPesoLatencia() { return pesoLatencia; }
    public double getPesoHistorial() { return pesoHistorial; }
}

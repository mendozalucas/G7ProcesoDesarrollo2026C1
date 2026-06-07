package com.escrims.domain.model.juego;

import com.escrims.domain.model.scrim.Equipo;
import com.escrims.domain.model.scrim.Scrim;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValorantJuego extends Juego {

    private static final Set<String> ROLES_REQUERIDOS = Set.of("Duelist", "Initiator", "Controller", "Sentinel");
    private static final int DIFERENCIA_MAXIMA_RANGOS = 3;

    public ValorantJuego() {
        super("valorant");
    }

    @Override
    public boolean validarRoles(Scrim scrim) {
        return scrim.getEquipos().stream().allMatch(equipo -> {
            Set<String> rolesPresentes = equipo.getParticipantes().stream()
                    .filter(p -> p.getRolAsignado() != null)
                    .map(p -> p.getRolAsignado().getNombreRol())
                    .collect(Collectors.toSet());
            return rolesPresentes.containsAll(ROLES_REQUERIDOS);
        });
    }

    @Override
    public boolean validarRangos(Scrim scrim) {
        List<Integer> rangos = scrim.getEquipos().stream()
                .flatMap(e -> e.getParticipantes().stream())
                .map(p -> scrim.getRangosPermitidos().getMin().getNumerico())
                .collect(Collectors.toList());
        if (rangos.isEmpty()) return true;
        int max = rangos.stream().mapToInt(Integer::intValue).max().orElse(0);
        int min = rangos.stream().mapToInt(Integer::intValue).min().orElse(0);
        return (max - min) <= DIFERENCIA_MAXIMA_RANGOS;
    }

    @Override
    public boolean validarReglasEspecificas(Scrim scrim) {
        return scrim.getFormato().getJugadoresPorLado() == 5;
    }
}

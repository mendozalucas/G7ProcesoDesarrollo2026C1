package com.escrims.domain.model.juego;

import com.escrims.domain.model.scrim.Scrim;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LolJuego extends Juego {

    private static final List<String> ROLES_OBLIGATORIOS = List.of("Top", "Jungle", "Mid", "ADC", "Support");

    public LolJuego() {
        super("lol");
    }

    @Override
    public boolean validarRoles(Scrim scrim) {
        return scrim.getEquipos().stream().allMatch(equipo -> {
            Set<String> rolesPresentes = equipo.getParticipantes().stream()
                    .filter(p -> p.getRolAsignado() != null)
                    .map(p -> p.getRolAsignado().getNombreRol())
                    .collect(Collectors.toSet());
            return rolesPresentes.containsAll(ROLES_OBLIGATORIOS)
                    && rolesPresentes.size() == ROLES_OBLIGATORIOS.size();
        });
    }

    @Override
    public boolean validarRangos(Scrim scrim) {
        return true;
    }

    @Override
    public boolean validarReglasEspecificas(Scrim scrim) {
        return scrim.getFormato().getJugadoresPorLado() == 5;
    }
}

package com.escrims.domain.composite;

import com.escrims.domain.model.usuario.Usuario;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registro en memoria de equipos/lobby por scrim (swap, invitaciones, roles).
 */
public final class LobbyTeamRegistry {

    private static final LobbyTeamRegistry INSTANCE = new LobbyTeamRegistry();

    private final Map<UUID, EquipoComposite> equipos = new ConcurrentHashMap<>();
    private final Map<UUID, java.util.Set<UUID>> invitados = new ConcurrentHashMap<>();

    private LobbyTeamRegistry() {}

    public static LobbyTeamRegistry getInstance() {
        return INSTANCE;
    }

    public EquipoComposite equipoDe(UUID scrimId) {
        return equipos.computeIfAbsent(scrimId, id -> new EquipoComposite("Equipo-" + id));
    }

    public void registrarParticipante(UUID scrimId, Usuario usuario, String rol, String lado) {
        equipoDe(scrimId).agregar(new Participante(usuario, rol, lado));
    }

    public Optional<Participante> buscarParticipante(UUID scrimId, UUID usuarioId) {
        return equipoDe(scrimId).getMiembros().stream()
                .filter(Participante.class::isInstance)
                .map(Participante.class::cast)
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .findFirst();
    }

    public void invitar(UUID scrimId, UUID usuarioId) {
        invitados.computeIfAbsent(scrimId, id -> ConcurrentHashMap.newKeySet()).add(usuarioId);
    }

    public void desinvitar(UUID scrimId, UUID usuarioId) {
        java.util.Set<UUID> set = invitados.get(scrimId);
        if (set != null) {
            set.remove(usuarioId);
        }
    }

    public boolean fueInvitado(UUID scrimId, UUID usuarioId) {
        java.util.Set<UUID> set = invitados.get(scrimId);
        return set != null && set.contains(usuarioId);
    }

    public void limpiar(UUID scrimId) {
        equipos.remove(scrimId);
        invitados.remove(scrimId);
    }
}

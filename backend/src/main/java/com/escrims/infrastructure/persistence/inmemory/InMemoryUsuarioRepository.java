package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryUsuarioRepository implements UsuarioRepository {

    private final Map<UUID, Usuario> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return store.values().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public Optional<Usuario> findByCredencialOAuth(String proveedorNombre, String externalId) {
        return store.values().stream()
                .filter(u -> u.getCredencialesOAuth().stream()
                        .anyMatch(c -> c.getProveedor().getNombre().equalsIgnoreCase(proveedorNombre)
                                && c.getExternalId().equals(externalId)))
                .findFirst();
    }

    @Override
    public List<Usuario> findCandidatosParaScrim(Scrim scrim) {
        Set<UUID> participantes = new HashSet<>(scrim.getParticipantesIds());
        return store.values().stream()
                .filter(u -> !participantes.contains(u.getId()))
                .filter(u -> !u.estaBloqueado())
                .filter(u -> {
                    if (u.getRangoPara(scrim.getNombreJuego()) == null) return false;
                    return scrim.getRangosPermitidos().contiene(u.getRangoPara(scrim.getNombreJuego()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Usuario save(Usuario usuario) {
        store.put(usuario.getId(), usuario);
        return usuario;
    }
}

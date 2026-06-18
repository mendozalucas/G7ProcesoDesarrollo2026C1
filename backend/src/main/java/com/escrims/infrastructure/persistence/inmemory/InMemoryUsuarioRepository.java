package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    public Usuario save(Usuario usuario) {
        store.put(usuario.getId(), usuario);
        return usuario;
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(store.values());
    }
}

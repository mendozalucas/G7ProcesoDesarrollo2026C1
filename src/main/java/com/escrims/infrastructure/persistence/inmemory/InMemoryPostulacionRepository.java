package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.repository.PostulacionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryPostulacionRepository implements PostulacionRepository {

    private final Map<UUID, Postulacion> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Postulacion> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Postulacion> findByScrimId(UUID scrimId) {
        return store.values().stream()
                .filter(p -> p.getScrimId().equals(scrimId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Postulacion> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(p -> p.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public Postulacion save(Postulacion postulacion) {
        store.put(postulacion.getId(), postulacion);
        return postulacion;
    }
}

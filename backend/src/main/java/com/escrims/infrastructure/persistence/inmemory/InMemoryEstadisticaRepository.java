package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.repository.EstadisticaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryEstadisticaRepository implements EstadisticaRepository {

    private final Map<UUID, Estadistica> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Estadistica> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Estadistica> findByScrimId(UUID scrimId) {
        return store.values().stream()
                .filter(e -> e.getScrimId().equals(scrimId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Estadistica> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(e -> e.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public Estadistica save(Estadistica estadistica) {
        store.put(estadistica.getId(), estadistica);
        return estadistica;
    }
}

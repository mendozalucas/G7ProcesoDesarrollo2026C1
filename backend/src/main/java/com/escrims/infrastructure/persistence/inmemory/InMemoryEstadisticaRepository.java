package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.repository.EstadisticaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryEstadisticaRepository implements EstadisticaRepository {

    private final Map<Long, Estadistica> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<Estadistica> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Estadistica> findByScrimId(UUID scrimId) {
        return List.of();
    }

    @Override
    public List<Estadistica> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(e -> e.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public Estadistica save(Estadistica estadistica) {
        Long id = estadistica.getId() != null ? estadistica.getId() : idGenerator.getAndIncrement();
        Estadistica guardada = new Estadistica(
                id,
                estadistica.getUsuario(),
                estadistica.getKills(),
                estadistica.getDeaths(),
                estadistica.getAssists(),
                estadistica.getObservaciones());
        store.put(id, guardada);
        return guardada;
    }
}

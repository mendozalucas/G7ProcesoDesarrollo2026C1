package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.repository.BusquedaFavoritaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBusquedaFavoritaRepository implements BusquedaFavoritaRepository {

    private final Map<UUID, BusquedaFavorita> store = new ConcurrentHashMap<>();

    @Override
    public Optional<BusquedaFavorita> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<BusquedaFavorita> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(b -> b.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BusquedaFavorita> findAlertasActivas() {
        return store.values().stream()
                .filter(BusquedaFavorita::isAlertaActiva)
                .collect(Collectors.toList());
    }

    @Override
    public BusquedaFavorita save(BusquedaFavorita busqueda) {
        store.put(busqueda.getId(), busqueda);
        return busqueda;
    }
}

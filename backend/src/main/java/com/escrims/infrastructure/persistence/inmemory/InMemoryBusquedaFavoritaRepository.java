package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;
import com.escrims.domain.repository.BusquedaFavoritaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBusquedaFavoritaRepository implements BusquedaFavoritaRepository {

    private final Map<UUID, BusquedaFavoritaAlmacenada> store = new ConcurrentHashMap<>();

    @Override
    public Optional<BusquedaFavoritaAlmacenada> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<BusquedaFavoritaAlmacenada> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(b -> b.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<BusquedaFavoritaAlmacenada> findAlertasActivas() {
        return store.values().stream()
                .filter(BusquedaFavoritaAlmacenada::isAlertaActiva)
                .collect(Collectors.toList());
    }

    @Override
    public BusquedaFavoritaAlmacenada save(BusquedaFavoritaAlmacenada busqueda) {
        store.put(busqueda.getId(), busqueda);
        return busqueda;
    }
}

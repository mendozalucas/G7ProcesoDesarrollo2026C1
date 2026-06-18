package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.repository.PostulacionRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryPostulacionRepository implements PostulacionRepository {

    private final Map<Long, Postulacion> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<Postulacion> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Postulacion> findByScrimId(UUID scrimId) {
        return store.values().stream()
                .filter(p -> p.getScrim().getId().equals(scrimId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Postulacion> findByUsuarioId(UUID usuarioId) {
        return store.values().stream()
                .filter(p -> p.getUsuario().getId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public Postulacion save(Postulacion postulacion) {
        Long id = postulacion.getId() != null ? postulacion.getId() : idGenerator.getAndIncrement();
        Postulacion guardada = new Postulacion(
                id,
                postulacion.getJugador(),
                postulacion.getScrim(),
                postulacion.getRolDeseado(),
                postulacion.getEstado());
        store.put(id, guardada);
        return guardada;
    }
}

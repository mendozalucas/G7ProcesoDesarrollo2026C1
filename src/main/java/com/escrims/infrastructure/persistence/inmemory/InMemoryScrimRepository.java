package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.valueobjects.CriteriosBusqueda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryScrimRepository implements ScrimRepository {

    private final Map<UUID, Scrim> store = new ConcurrentHashMap<>();

    @Override
    public Optional<Scrim> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Scrim save(Scrim scrim) {
        store.put(scrim.getId(), scrim);
        return scrim;
    }

    @Override
    public List<Scrim> findByCriteria(CriteriosBusqueda criterios) {
        return store.values().stream()
                .filter(s -> criterios.coincideCon(
                        s.getNombreJuego(),
                        s.getFormato(),
                        s.getRegion(),
                        s.getFechaHora(),
                        s.getLatenciaMax()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findCandidatosParaScrim(Scrim scrim) {
        return new ArrayList<>();
    }

    @Override
    public List<Scrim> findByEstado(String estadoNombre) {
        return store.values().stream()
                .filter(s -> s.getEstadoNombre().equalsIgnoreCase(estadoNombre))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }

    public List<Scrim> findAll() {
        return new ArrayList<>(store.values());
    }
}

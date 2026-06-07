package com.escrims.domain.services;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.repository.BusquedaFavoritaRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SearchAlertService {

    private final BusquedaFavoritaRepository busquedaRepository;

    public SearchAlertService(BusquedaFavoritaRepository busquedaRepository) {
        this.busquedaRepository = busquedaRepository;
    }

    public List<UUID> evaluarNuevoScrim(Scrim scrim) {
        return busquedaRepository.findAlertasActivas().stream()
                .filter(b -> b.coincideCon(scrim))
                .map(BusquedaFavorita::getUsuarioId)
                .collect(Collectors.toList());
    }

    public BusquedaFavorita guardarBusqueda(BusquedaFavorita busqueda) {
        return busquedaRepository.save(busqueda);
    }

    public void activarAlerta(UUID busquedaId) {
        busquedaRepository.findById(busquedaId).ifPresent(b -> {
            b.activarAlerta();
            busquedaRepository.save(b);
        });
    }
}

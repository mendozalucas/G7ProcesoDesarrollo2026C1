package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.services.SearchAlertService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToggleAlertaBusquedaUseCase {

    private final SearchAlertService searchAlertService;
    private final BusquedaFavoritaRepository busquedaRepository;

    public ToggleAlertaBusquedaUseCase(SearchAlertService searchAlertService,
                                        BusquedaFavoritaRepository busquedaRepository) {
        this.searchAlertService = searchAlertService;
        this.busquedaRepository = busquedaRepository;
    }

    public BusquedaFavoritaDTO activar(UUID busquedaId) {
        searchAlertService.activarAlerta(busquedaId);
        return obtener(busquedaId);
    }

    public BusquedaFavoritaDTO desactivar(UUID busquedaId) {
        searchAlertService.desactivarAlerta(busquedaId);
        return obtener(busquedaId);
    }

    private BusquedaFavoritaDTO obtener(UUID busquedaId) {
        return busquedaRepository.findById(busquedaId)
                .map(BusquedaFavoritaDTO::from)
                .orElseThrow(() -> new IllegalArgumentException("Búsqueda favorita no encontrada: " + busquedaId));
    }
}

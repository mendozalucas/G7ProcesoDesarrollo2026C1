package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ToggleAlertaBusquedaUseCase {

    private final BusquedaFavoritaRepository busquedaRepository;

    public ToggleAlertaBusquedaUseCase(BusquedaFavoritaRepository busquedaRepository) {
        this.busquedaRepository = busquedaRepository;
    }

    public BusquedaFavoritaDTO activar(UUID busquedaId) {
        return BusquedaFavoritaDTO.from(actualizarAlerta(busquedaId, true));
    }

    public BusquedaFavoritaDTO desactivar(UUID busquedaId) {
        return BusquedaFavoritaDTO.from(actualizarAlerta(busquedaId, false));
    }

    private BusquedaFavoritaAlmacenada actualizarAlerta(UUID busquedaId, boolean activa) {
        BusquedaFavoritaAlmacenada actual = busquedaRepository.findById(busquedaId)
                .orElseThrow(() -> new IllegalArgumentException("Búsqueda favorita no encontrada: " + busquedaId));
        BusquedaFavoritaAlmacenada actualizada = new BusquedaFavoritaAlmacenada(
                actual.getId(),
                actual.getUsuarioId(),
                activa,
                actual.getCriterios());
        return busquedaRepository.save(actualizada);
    }
}

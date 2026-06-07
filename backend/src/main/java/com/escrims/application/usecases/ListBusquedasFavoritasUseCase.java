package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.domain.services.SearchAlertService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListBusquedasFavoritasUseCase {

    private final SearchAlertService searchAlertService;

    public ListBusquedasFavoritasUseCase(SearchAlertService searchAlertService) {
        this.searchAlertService = searchAlertService;
    }

    public List<BusquedaFavoritaDTO> execute(UUID usuarioId) {
        return searchAlertService.listarPorUsuario(usuarioId).stream()
                .map(BusquedaFavoritaDTO::from)
                .collect(Collectors.toList());
    }
}

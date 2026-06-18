package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListBusquedasFavoritasUseCase {

    private final BusquedaFavoritaRepository busquedaRepository;

    public ListBusquedasFavoritasUseCase(BusquedaFavoritaRepository busquedaRepository) {
        this.busquedaRepository = busquedaRepository;
    }

    public List<BusquedaFavoritaDTO> execute(UUID usuarioId) {
        return busquedaRepository.findByUsuarioId(usuarioId).stream()
                .map(BusquedaFavoritaDTO::from)
                .collect(Collectors.toList());
    }
}

package com.escrims.domain.repository;

import com.escrims.domain.model.busqueda.BusquedaFavorita;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BusquedaFavoritaRepository {

    Optional<BusquedaFavorita> findById(UUID id);

    List<BusquedaFavorita> findByUsuarioId(UUID usuarioId);

    List<BusquedaFavorita> findAlertasActivas();

    BusquedaFavorita save(BusquedaFavorita busqueda);
}

package com.escrims.domain.repository;

import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BusquedaFavoritaRepository {

    Optional<BusquedaFavoritaAlmacenada> findById(UUID id);

    List<BusquedaFavoritaAlmacenada> findByUsuarioId(UUID usuarioId);

    List<BusquedaFavoritaAlmacenada> findAlertasActivas();

    BusquedaFavoritaAlmacenada save(BusquedaFavoritaAlmacenada busqueda);
}

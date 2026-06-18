package com.escrims.domain.model.busqueda;

import java.util.UUID;

public class BusquedaFavoritaAlmacenada {

    private final UUID id;
    private final UUID usuarioId;
    private final boolean alertaActiva;
    private final BusquedaFavorita criterios;

    public BusquedaFavoritaAlmacenada(UUID id, UUID usuarioId, boolean alertaActiva, BusquedaFavorita criterios) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.alertaActiva = alertaActiva;
        this.criterios = criterios;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public boolean isAlertaActiva() { return alertaActiva; }
    public BusquedaFavorita getCriterios() { return criterios; }
}

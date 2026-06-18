package com.escrims.application.dto;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;

import java.util.UUID;

public class BusquedaFavoritaDTO {

    private UUID id;
    private UUID usuarioId;
    private String juego;
    private Integer rangoMin;
    private Integer rangoMax;
    private String region;
    private String rolBuscado;
    private boolean alertaActiva;

    public static BusquedaFavoritaDTO from(BusquedaFavoritaAlmacenada almacenada) {
        BusquedaFavoritaDTO dto = new BusquedaFavoritaDTO();
        BusquedaFavorita busqueda = almacenada.getCriterios();
        dto.id = almacenada.getId();
        dto.usuarioId = almacenada.getUsuarioId();
        dto.juego = busqueda.getJuego().getNombre();
        if (busqueda.getRangoMinimo() != null) dto.rangoMin = busqueda.getRangoMinimo().getMmr();
        if (busqueda.getRangoMaximo() != null) dto.rangoMax = busqueda.getRangoMaximo().getMmr();
        if (busqueda.getRegion() != null) dto.region = busqueda.getRegion().getNombre();
        if (busqueda.getRolBuscado() != null) dto.rolBuscado = busqueda.getRolBuscado().getNombre();
        dto.alertaActiva = almacenada.isAlertaActiva();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getJuego() { return juego; }
    public Integer getRangoMin() { return rangoMin; }
    public Integer getRangoMax() { return rangoMax; }
    public String getRegion() { return region; }
    public String getRolBuscado() { return rolBuscado; }
    public boolean isAlertaActiva() { return alertaActiva; }
}

package com.escrims.application.dto;

import com.escrims.domain.model.busqueda.BusquedaFavorita;

import java.util.UUID;

public class BusquedaFavoritaDTO {

    private UUID id;
    private UUID usuarioId;
    private String juego;
    private Integer rangoMin;
    private Integer rangoMax;
    private String servidor;
    private String zona;
    private String rolBuscado;
    private boolean alertaActiva;

    public static BusquedaFavoritaDTO from(BusquedaFavorita busqueda) {
        BusquedaFavoritaDTO dto = new BusquedaFavoritaDTO();
        dto.id = busqueda.getId();
        dto.usuarioId = busqueda.getUsuarioId();
        dto.juego = busqueda.getJuego().getNombre();
        if (busqueda.getRangoMinimo() != null) dto.rangoMin = busqueda.getRangoMinimo().getNumerico();
        if (busqueda.getRangoMaximo() != null) dto.rangoMax = busqueda.getRangoMaximo().getNumerico();
        if (busqueda.getRegion() != null) {
            dto.servidor = busqueda.getRegion().getServidor();
            dto.zona = busqueda.getRegion().getZona();
        }
        if (busqueda.getRolBuscado() != null) dto.rolBuscado = busqueda.getRolBuscado().getNombreRol();
        dto.alertaActiva = busqueda.isAlertaActiva();
        return dto;
    }

    public UUID getId() { return id; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getJuego() { return juego; }
    public Integer getRangoMin() { return rangoMin; }
    public Integer getRangoMax() { return rangoMax; }
    public String getServidor() { return servidor; }
    public String getZona() { return zona; }
    public String getRolBuscado() { return rolBuscado; }
    public boolean isAlertaActiva() { return alertaActiva; }
}

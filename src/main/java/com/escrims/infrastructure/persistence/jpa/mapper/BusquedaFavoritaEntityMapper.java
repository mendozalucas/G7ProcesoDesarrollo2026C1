package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;
import com.escrims.infrastructure.persistence.jpa.entity.BusquedaFavoritaEntity;
import org.springframework.stereotype.Component;

@Component
public class BusquedaFavoritaEntityMapper {

    public BusquedaFavorita toDomain(BusquedaFavoritaEntity entity) {
        Rango rangoMin = entity.getRangoMinNumerico() != null
                ? new Rango(entity.getRangoMinJuego(), entity.getRangoMinTier(), entity.getRangoMinNumerico())
                : null;
        Rango rangoMax = entity.getRangoMaxNumerico() != null
                ? new Rango(entity.getRangoMaxJuego(), entity.getRangoMaxTier(), entity.getRangoMaxNumerico())
                : null;
        Region region = entity.getRegionServidor() != null
                ? new Region(entity.getRegionServidor(), entity.getRegionZona())
                : null;
        DisponibilidadHoraria horario = entity.getHorarioDia() != null
                ? new DisponibilidadHoraria(entity.getHorarioDia(),
                entity.getHorarioInicio(), entity.getHorarioFin())
                : null;
        RolJuego rol = entity.getRolJuego() != null
                ? new RolJuego(entity.getRolJuego(), entity.getRolNombre())
                : null;

        return BusquedaFavorita.reconstituir(entity.getId(), entity.getUsuarioId(),
                JuegoFactory.para(entity.getJuego()), rangoMin, rangoMax, region, horario, rol,
                entity.isAlertaActiva());
    }

    public BusquedaFavoritaEntity toEntity(BusquedaFavorita busqueda) {
        BusquedaFavoritaEntity entity = new BusquedaFavoritaEntity();
        entity.setId(busqueda.getId());
        entity.setUsuarioId(busqueda.getUsuarioId());
        entity.setJuego(busqueda.getJuego().getNombre());
        if (busqueda.getRangoMinimo() != null) {
            entity.setRangoMinJuego(busqueda.getRangoMinimo().getJuego());
            entity.setRangoMinTier(busqueda.getRangoMinimo().getTier());
            entity.setRangoMinNumerico(busqueda.getRangoMinimo().getNumerico());
        }
        if (busqueda.getRangoMaximo() != null) {
            entity.setRangoMaxJuego(busqueda.getRangoMaximo().getJuego());
            entity.setRangoMaxTier(busqueda.getRangoMaximo().getTier());
            entity.setRangoMaxNumerico(busqueda.getRangoMaximo().getNumerico());
        }
        if (busqueda.getRegion() != null) {
            entity.setRegionServidor(busqueda.getRegion().getServidor());
            entity.setRegionZona(busqueda.getRegion().getZona());
        }
        if (busqueda.getHorarioPreferido() != null) {
            entity.setHorarioDia(busqueda.getHorarioPreferido().getDia());
            entity.setHorarioInicio(busqueda.getHorarioPreferido().getInicio());
            entity.setHorarioFin(busqueda.getHorarioPreferido().getFin());
        }
        if (busqueda.getRolBuscado() != null) {
            entity.setRolJuego(busqueda.getRolBuscado().getJuego());
            entity.setRolNombre(busqueda.getRolBuscado().getNombreRol());
        }
        entity.setAlertaActiva(busqueda.isAlertaActiva());
        return entity;
    }
}

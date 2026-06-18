package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.infrastructure.persistence.jpa.entity.BusquedaFavoritaEntity;
import org.springframework.stereotype.Component;

@Component
public class BusquedaFavoritaEntityMapper {

    public BusquedaFavoritaAlmacenada toDomain(BusquedaFavoritaEntity entity) {
        Rango rangoMin = entity.getRangoMinNumerico() != null
                ? new Rango(null, entity.getRangoMinTier(), entity.getRangoMinNumerico()) : null;
        Rango rangoMax = entity.getRangoMaxNumerico() != null
                ? new Rango(null, entity.getRangoMaxTier(), entity.getRangoMaxNumerico()) : null;

        String regionNombre = entity.getRegionServidor();
        if (entity.getRegionZona() != null && !entity.getRegionZona().isBlank()) {
            regionNombre = entity.getRegionServidor() + "/" + entity.getRegionZona();
        }
        Region region = entity.getRegionServidor() != null ? new Region(null, regionNombre) : null;

        DisponibilidadHoraria horario = entity.getHorarioDia() != null
                ? new DisponibilidadHoraria(entity.getHorarioDia(),
                entity.getHorarioInicio(), entity.getHorarioFin())
                : null;
        Rol rol = entity.getRolNombre() != null ? new Rol(null, entity.getRolNombre()) : null;

        BusquedaFavorita criterios = new BusquedaFavorita(
                JuegoFactory.para(entity.getJuego()),
                rangoMin,
                rangoMax,
                region,
                horario,
                rol);

        return new BusquedaFavoritaAlmacenada(
                entity.getId(),
                entity.getUsuarioId(),
                entity.isAlertaActiva(),
                criterios);
    }

    public BusquedaFavoritaEntity toEntity(BusquedaFavoritaAlmacenada almacenada) {
        BusquedaFavorita busqueda = almacenada.getCriterios();
        BusquedaFavoritaEntity entity = new BusquedaFavoritaEntity();
        entity.setId(almacenada.getId());
        entity.setUsuarioId(almacenada.getUsuarioId());
        entity.setJuego(busqueda.getJuego().getNombre());
        if (busqueda.getRangoMinimo() != null) {
            entity.setRangoMinTier(busqueda.getRangoMinimo().getNombre());
            entity.setRangoMinNumerico(busqueda.getRangoMinimo().getMmr());
        }
        if (busqueda.getRangoMaximo() != null) {
            entity.setRangoMaxTier(busqueda.getRangoMaximo().getNombre());
            entity.setRangoMaxNumerico(busqueda.getRangoMaximo().getMmr());
        }
        if (busqueda.getRegion() != null) {
            String regionNombre = busqueda.getRegion().getNombre();
            String servidor = regionNombre;
            String zona = "";
            int slash = regionNombre.indexOf('/');
            if (slash >= 0) {
                servidor = regionNombre.substring(0, slash);
                zona = regionNombre.substring(slash + 1);
            }
            entity.setRegionServidor(servidor);
            entity.setRegionZona(zona);
        }
        if (busqueda.getHorarioPreferido() != null) {
            entity.setHorarioDia(busqueda.getHorarioPreferido().getDia());
            entity.setHorarioInicio(busqueda.getHorarioPreferido().getInicio());
            entity.setHorarioFin(busqueda.getHorarioPreferido().getFin());
        }
        if (busqueda.getRolBuscado() != null) {
            entity.setRolNombre(busqueda.getRolBuscado().getNombre());
        }
        entity.setAlertaActiva(almacenada.isAlertaActiva());
        return entity;
    }
}

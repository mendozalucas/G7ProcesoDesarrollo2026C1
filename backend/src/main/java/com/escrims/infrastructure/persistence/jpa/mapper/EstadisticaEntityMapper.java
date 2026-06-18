package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity;
import org.springframework.stereotype.Component;

@Component
public class EstadisticaEntityMapper {

    public Estadistica toDomain(EstadisticaEntity entity, Usuario usuario) {
        return new Estadistica(
                entity.getId(),
                usuario,
                entity.getKills(),
                entity.getDeaths(),
                entity.getAssists(),
                entity.getObservaciones());
    }

    public EstadisticaEntity toEntity(Estadistica estadistica) {
        EstadisticaEntity entity = new EstadisticaEntity();
        entity.setId(estadistica.getId());
        entity.setUsuarioId(estadistica.getUsuario().getId());
        entity.setKills(estadistica.getKills());
        entity.setDeaths(estadistica.getDeaths());
        entity.setAssists(estadistica.getAssists());
        entity.setObservaciones(estadistica.getObservaciones());
        return entity;
    }
}

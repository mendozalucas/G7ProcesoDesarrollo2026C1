package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity;
import org.springframework.stereotype.Component;

@Component
public class EstadisticaEntityMapper {

    public Estadistica toDomain(EstadisticaEntity entity, Usuario usuario) {
        return Estadistica.reconstituir(entity.getId(), usuario, entity.getScrimId(),
                entity.getKills(), entity.getDeaths(), entity.getAssists(),
                entity.isEsMvp(), entity.getObservaciones(), entity.getEstadoFeedback());
    }

    public EstadisticaEntity toEntity(Estadistica estadistica) {
        EstadisticaEntity entity = new EstadisticaEntity();
        entity.setId(estadistica.getId());
        entity.setUsuarioId(estadistica.getUsuarioId());
        entity.setScrimId(estadistica.getScrimId());
        entity.setKills(estadistica.getKills());
        entity.setDeaths(estadistica.getDeaths());
        entity.setAssists(estadistica.getAssists());
        entity.setEsMvp(estadistica.isEsMvp());
        entity.setObservaciones(estadistica.getObservaciones());
        entity.setEstadoFeedback(estadistica.getEstadoFeedback());
        return entity;
    }
}

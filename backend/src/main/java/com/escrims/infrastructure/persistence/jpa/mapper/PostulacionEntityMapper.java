package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.valueobjects.RolJuego;
import com.escrims.infrastructure.persistence.jpa.entity.PostulacionEntity;
import org.springframework.stereotype.Component;

@Component
public class PostulacionEntityMapper {

    public Postulacion toDomain(PostulacionEntity entity) {
        RolJuego rol = entity.getRolJuego() != null
                ? new RolJuego(entity.getRolJuego(), entity.getRolNombre())
                : null;
        return Postulacion.reconstituir(entity.getId(), entity.getUsuarioId(), entity.getScrimId(),
                rol, entity.getEstado(), entity.getFechaPostulacion());
    }

    public PostulacionEntity toEntity(Postulacion postulacion) {
        PostulacionEntity entity = new PostulacionEntity();
        entity.setId(postulacion.getId());
        entity.setUsuarioId(postulacion.getUsuarioId());
        entity.setScrimId(postulacion.getScrimId());
        RolJuego rol = postulacion.getRolDeseado();
        if (rol != null) {
            entity.setRolJuego(rol.getJuego());
            entity.setRolNombre(rol.getNombreRol());
        }
        entity.setEstado(postulacion.getEstadoNombre());
        entity.setFechaPostulacion(postulacion.getFechaPostulacion());
        return entity;
    }
}

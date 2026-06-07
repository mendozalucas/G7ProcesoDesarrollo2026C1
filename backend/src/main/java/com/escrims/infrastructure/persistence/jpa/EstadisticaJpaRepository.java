package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EstadisticaJpaRepository extends JpaRepository<EstadisticaEntity, UUID> {

    List<EstadisticaEntity> findByScrimId(UUID scrimId);

    List<EstadisticaEntity> findByUsuarioId(UUID usuarioId);
}

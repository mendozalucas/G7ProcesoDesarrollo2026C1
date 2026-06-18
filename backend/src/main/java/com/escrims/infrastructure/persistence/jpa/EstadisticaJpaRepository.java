package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EstadisticaJpaRepository extends JpaRepository<EstadisticaEntity, Long> {

    List<EstadisticaEntity> findByUsuarioId(UUID usuarioId);
}

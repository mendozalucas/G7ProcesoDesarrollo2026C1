package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.BusquedaFavoritaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BusquedaFavoritaJpaRepository extends JpaRepository<BusquedaFavoritaEntity, UUID> {

    List<BusquedaFavoritaEntity> findByUsuarioId(UUID usuarioId);

    List<BusquedaFavoritaEntity> findByAlertaActivaTrue();
}

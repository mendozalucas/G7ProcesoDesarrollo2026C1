package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.PostulacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostulacionJpaRepository extends JpaRepository<PostulacionEntity, UUID> {

    List<PostulacionEntity> findByScrimId(UUID scrimId);

    List<PostulacionEntity> findByUsuarioId(UUID usuarioId);
}

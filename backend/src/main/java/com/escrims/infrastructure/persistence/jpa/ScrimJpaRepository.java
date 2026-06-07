package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScrimJpaRepository extends JpaRepository<ScrimEntity, UUID> {

    List<ScrimEntity> findByEstadoIgnoreCase(String estado);
}

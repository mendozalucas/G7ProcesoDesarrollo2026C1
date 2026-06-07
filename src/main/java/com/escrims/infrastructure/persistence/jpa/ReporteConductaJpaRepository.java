package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.ReporteConductaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReporteConductaJpaRepository extends JpaRepository<ReporteConductaEntity, UUID> {

    List<ReporteConductaEntity> findByEstadoResolucionIgnoreCase(String estadoResolucion);

    List<ReporteConductaEntity> findByReportadoId(UUID reportadoId);
}

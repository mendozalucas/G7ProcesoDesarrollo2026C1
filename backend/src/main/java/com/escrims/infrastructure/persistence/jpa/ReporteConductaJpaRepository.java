package com.escrims.infrastructure.persistence.jpa;

import com.escrims.infrastructure.persistence.jpa.entity.ReporteConductaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteConductaJpaRepository extends JpaRepository<ReporteConductaEntity, Long> {

    List<ReporteConductaEntity> findByResuelto(boolean resuelto);
}

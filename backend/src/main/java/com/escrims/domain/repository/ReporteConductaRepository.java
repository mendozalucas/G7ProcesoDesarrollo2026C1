package com.escrims.domain.repository;

import com.escrims.domain.model.reporte.ReporteConducta;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReporteConductaRepository {

    Optional<ReporteConducta> findById(UUID id);

    List<ReporteConducta> findByEstado(String estadoResolucion);

    List<ReporteConducta> findByReportadoId(UUID reportadoId);

    ReporteConducta save(ReporteConducta reporte);
}

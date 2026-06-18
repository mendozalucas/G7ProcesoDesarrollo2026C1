package com.escrims.domain.repository;

import com.escrims.domain.model.reporte.ReporteConducta;

import java.util.List;
import java.util.Optional;

public interface ReporteConductaRepository {

    Optional<ReporteConducta> findById(Long id);

    List<ReporteConducta> findByEstado(String estadoResolucion);

    ReporteConducta save(ReporteConducta reporte);
}

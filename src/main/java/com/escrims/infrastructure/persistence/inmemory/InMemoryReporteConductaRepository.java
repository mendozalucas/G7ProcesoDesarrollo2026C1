package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.ReporteConductaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryReporteConductaRepository implements ReporteConductaRepository {

    private final Map<UUID, ReporteConducta> store = new ConcurrentHashMap<>();

    @Override
    public Optional<ReporteConducta> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ReporteConducta> findByEstado(String estadoResolucion) {
        return store.values().stream()
                .filter(r -> r.getEstadoResolucion().equalsIgnoreCase(estadoResolucion))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteConducta> findByReportadoId(UUID reportadoId) {
        return store.values().stream()
                .filter(r -> r.getReportadoId().equals(reportadoId))
                .collect(Collectors.toList());
    }

    @Override
    public ReporteConducta save(ReporteConducta reporte) {
        store.put(reporte.getId(), reporte);
        return reporte;
    }
}

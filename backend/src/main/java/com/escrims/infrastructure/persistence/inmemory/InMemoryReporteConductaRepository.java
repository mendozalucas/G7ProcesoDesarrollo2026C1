package com.escrims.infrastructure.persistence.inmemory;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.ReporteConductaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryReporteConductaRepository implements ReporteConductaRepository {

    private final Map<Long, ReporteConducta> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<ReporteConducta> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<ReporteConducta> findByEstado(String estadoResolucion) {
        boolean resuelto = "RESUELTO".equalsIgnoreCase(estadoResolucion);
        return store.values().stream()
                .filter(r -> r.isResuelto() == resuelto)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteConducta save(ReporteConducta reporte) {
        Long id = reporte.getId() != null ? reporte.getId() : idGenerator.getAndIncrement();
        ReporteConducta guardado = new ReporteConducta(id, reporte.getMotivo());
        if (reporte.isResuelto()) {
            guardado.resolver();
        }
        store.put(id, guardado);
        return guardado;
    }
}

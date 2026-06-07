package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.repository.ReporteConductaRepository;
import com.escrims.infrastructure.persistence.jpa.mapper.ReporteConductaEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaReporteConductaRepository implements ReporteConductaRepository {

    private final ReporteConductaJpaRepository jpaRepository;
    private final ReporteConductaEntityMapper mapper;

    public JpaReporteConductaRepository(ReporteConductaJpaRepository jpaRepository,
                                         ReporteConductaEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ReporteConducta> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ReporteConducta> findByEstado(String estadoResolucion) {
        return jpaRepository.findByEstadoResolucionIgnoreCase(estadoResolucion).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReporteConducta> findByReportadoId(UUID reportadoId) {
        return jpaRepository.findByReportadoId(reportadoId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ReporteConducta save(ReporteConducta reporte) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(reporte)));
    }
}

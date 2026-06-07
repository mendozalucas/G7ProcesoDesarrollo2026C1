package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.strategy.sancion.SancionFactory;
import com.escrims.infrastructure.persistence.jpa.entity.ReporteConductaEntity;
import org.springframework.stereotype.Component;

@Component
public class ReporteConductaEntityMapper {

    public ReporteConducta toDomain(ReporteConductaEntity entity) {
        return ReporteConducta.reconstituir(entity.getId(), entity.getScrimId(),
                entity.getReportanteId(), entity.getReportadoId(), entity.getMotivo(),
                entity.getEstadoResolucion(), entity.getSancionTipo(), entity.getFechaReporte());
    }

    public ReporteConductaEntity toEntity(ReporteConducta reporte) {
        ReporteConductaEntity entity = new ReporteConductaEntity();
        entity.setId(reporte.getId());
        entity.setScrimId(reporte.getScrimId());
        entity.setReportanteId(reporte.getReportanteId());
        entity.setReportadoId(reporte.getReportadoId());
        entity.setMotivo(reporte.getMotivo());
        entity.setEstadoResolucion(reporte.getEstadoResolucion());
        entity.setSancionTipo(SancionFactory.nombre(reporte.getSancion()));
        entity.setFechaReporte(reporte.getFechaReporte());
        return entity;
    }
}

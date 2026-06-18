package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.infrastructure.persistence.jpa.entity.ReporteConductaEntity;
import org.springframework.stereotype.Component;

@Component
public class ReporteConductaEntityMapper {

    public ReporteConducta toDomain(ReporteConductaEntity entity) {
        ReporteConducta reporte = new ReporteConducta(entity.getId(), entity.getMotivo());
        if (entity.isResuelto()) {
            reporte.resolver();
        }
        return reporte;
    }

    public ReporteConductaEntity toEntity(ReporteConducta reporte) {
        ReporteConductaEntity entity = new ReporteConductaEntity();
        entity.setId(reporte.getId());
        entity.setMotivo(reporte.getMotivo());
        entity.setResuelto(reporte.isResuelto());
        entity.setFechaReporte(reporte.getFecha());
        return entity;
    }
}

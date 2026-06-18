package com.escrims.domain.repository;

import com.escrims.domain.model.estadistica.Estadistica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstadisticaRepository {

    Optional<Estadistica> findById(Long id);

    List<Estadistica> findByScrimId(UUID scrimId);

    List<Estadistica> findByUsuarioId(UUID usuarioId);

    Estadistica save(Estadistica estadistica);
}

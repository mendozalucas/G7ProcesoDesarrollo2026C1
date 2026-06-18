package com.escrims.domain.repository;

import com.escrims.domain.model.postulacion.Postulacion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostulacionRepository {

    Optional<Postulacion> findById(Long id);

    List<Postulacion> findByScrimId(UUID scrimId);

    List<Postulacion> findByUsuarioId(UUID usuarioId);

    Postulacion save(Postulacion postulacion);
}

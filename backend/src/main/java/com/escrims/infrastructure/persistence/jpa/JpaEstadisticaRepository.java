package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.estadistica.Estadistica;
import com.escrims.domain.repository.EstadisticaRepository;
import com.escrims.infrastructure.persistence.jpa.mapper.EstadisticaEntityMapper;
import com.escrims.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaEstadisticaRepository implements EstadisticaRepository {

    private final EstadisticaJpaRepository jpaRepository;
    private final UsuarioJpaRepository usuarioJpaRepository;
    private final EstadisticaEntityMapper mapper;
    private final UsuarioEntityMapper usuarioMapper;

    public JpaEstadisticaRepository(EstadisticaJpaRepository jpaRepository,
                                     UsuarioJpaRepository usuarioJpaRepository,
                                     EstadisticaEntityMapper mapper,
                                     UsuarioEntityMapper usuarioMapper) {
        this.jpaRepository = jpaRepository;
        this.usuarioJpaRepository = usuarioJpaRepository;
        this.mapper = mapper;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public Optional<Estadistica> findById(Long id) {
        return jpaRepository.findById(id).flatMap(entity ->
                usuarioJpaRepository.findById(entity.getUsuarioId())
                        .map(usuarioMapper::toDomain)
                        .map(usuario -> mapper.toDomain(entity, usuario)));
    }

    @Override
    public List<Estadistica> findByScrimId(UUID scrimId) {
        return List.of();
    }

    @Override
    public List<Estadistica> findByUsuarioId(UUID usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDomainOrThrow)
                .collect(Collectors.toList());
    }

    @Override
    public Estadistica save(Estadistica estadistica) {
        return mapper.toDomain(
                jpaRepository.save(mapper.toEntity(estadistica)),
                estadistica.getUsuario());
    }

    private Estadistica toDomainOrThrow(com.escrims.infrastructure.persistence.jpa.entity.EstadisticaEntity entity) {
        return usuarioJpaRepository.findById(entity.getUsuarioId())
                .map(usuarioMapper::toDomain)
                .map(usuario -> mapper.toDomain(entity, usuario))
                .orElseThrow(() -> new IllegalStateException(
                        "Usuario no encontrado para estadística: " + entity.getUsuarioId()));
    }
}

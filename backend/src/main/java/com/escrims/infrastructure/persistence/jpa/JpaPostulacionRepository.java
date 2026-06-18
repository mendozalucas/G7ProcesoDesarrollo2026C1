package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.repository.PostulacionRepository;
import com.escrims.infrastructure.persistence.jpa.mapper.PostulacionEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaPostulacionRepository implements PostulacionRepository {

    private final PostulacionJpaRepository jpaRepository;
    private final PostulacionEntityMapper mapper;

    public JpaPostulacionRepository(PostulacionJpaRepository jpaRepository,
                                     PostulacionEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Postulacion> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Postulacion> findByScrimId(UUID scrimId) {
        return jpaRepository.findByScrimId(scrimId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Postulacion> findByUsuarioId(UUID usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Postulacion save(Postulacion postulacion) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(postulacion)));
    }
}

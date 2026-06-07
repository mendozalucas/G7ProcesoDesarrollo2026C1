package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.infrastructure.persistence.jpa.mapper.BusquedaFavoritaEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaBusquedaFavoritaRepository implements BusquedaFavoritaRepository {

    private final BusquedaFavoritaJpaRepository jpaRepository;
    private final BusquedaFavoritaEntityMapper mapper;

    public JpaBusquedaFavoritaRepository(BusquedaFavoritaJpaRepository jpaRepository,
                                          BusquedaFavoritaEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<BusquedaFavorita> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<BusquedaFavorita> findByUsuarioId(UUID usuarioId) {
        return jpaRepository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<BusquedaFavorita> findAlertasActivas() {
        return jpaRepository.findByAlertaActivaTrue().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public BusquedaFavorita save(BusquedaFavorita busqueda) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(busqueda)));
    }
}

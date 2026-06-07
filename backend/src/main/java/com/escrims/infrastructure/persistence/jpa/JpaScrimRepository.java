package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.valueobjects.CriteriosBusqueda;
import com.escrims.infrastructure.persistence.jpa.mapper.ScrimEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaScrimRepository implements ScrimRepository {

    private final ScrimJpaRepository jpaRepository;
    private final ScrimEntityMapper mapper;

    public JpaScrimRepository(ScrimJpaRepository jpaRepository, ScrimEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Scrim> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Scrim save(Scrim scrim) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(scrim)));
    }

    @Override
    public List<Scrim> findByCriteria(CriteriosBusqueda criterios) {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .filter(s -> criterios.coincideCon(
                        s.getNombreJuego(),
                        s.getFormato(),
                        s.getRegion(),
                        s.getFechaHora(),
                        s.getLatenciaMax()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> findCandidatosParaScrim(Scrim scrim) {
        return new ArrayList<>();
    }

    @Override
    public List<Scrim> findByEstado(String estadoNombre) {
        return jpaRepository.findByEstadoIgnoreCase(estadoNombre).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}

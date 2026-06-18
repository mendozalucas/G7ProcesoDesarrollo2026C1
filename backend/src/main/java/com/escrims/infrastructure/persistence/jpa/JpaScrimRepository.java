package com.escrims.infrastructure.persistence.jpa;



import com.escrims.domain.model.scrim.Scrim;

import com.escrims.domain.model.usuario.Usuario;

import com.escrims.domain.repository.ScrimRepository;

import com.escrims.domain.repository.UsuarioRepository;

import com.escrims.domain.valueobjects.CriteriosBusqueda;
import com.escrims.domain.valueobjects.MatchmakingContext;

import com.escrims.infrastructure.persistence.jpa.entity.PostulacionEntity;
import com.escrims.infrastructure.persistence.jpa.entity.ScrimEntity;

import com.escrims.infrastructure.persistence.jpa.mapper.ScrimEntityMapper;

import org.springframework.context.annotation.Primary;

import org.springframework.stereotype.Repository;



import java.util.List;

import java.util.Objects;

import java.util.Optional;

import java.util.UUID;

import java.util.stream.Collectors;



@Repository

@Primary

public class JpaScrimRepository implements ScrimRepository {



    private final ScrimJpaRepository jpaRepository;

    private final ScrimEntityMapper mapper;

    private final PostulacionJpaRepository postulacionJpaRepository;

    private final UsuarioRepository usuarioRepository;

    private final MatchmakingContextAssembler matchmakingContextAssembler;



    public JpaScrimRepository(ScrimJpaRepository jpaRepository,

                              ScrimEntityMapper mapper,

                              PostulacionJpaRepository postulacionJpaRepository,

                              UsuarioRepository usuarioRepository,

                              MatchmakingContextAssembler matchmakingContextAssembler) {

        this.jpaRepository = jpaRepository;

        this.mapper = mapper;

        this.postulacionJpaRepository = postulacionJpaRepository;

        this.usuarioRepository = usuarioRepository;

        this.matchmakingContextAssembler = matchmakingContextAssembler;

    }



    @Override

    public Optional<Scrim> findById(UUID id) {

        return jpaRepository.findById(id).map(mapper::toDomain);

    }



    @Override

    public Scrim save(Scrim scrim) {

        ScrimEntity entity = jpaRepository.findById(scrim.getId()).orElseGet(ScrimEntity::new);

        if (entity.getId() == null) {

            entity.setId(scrim.getId());

        }

        mapper.populateEntity(entity, scrim);

        return mapper.toDomain(jpaRepository.save(entity));

    }



    @Override

    public List<Scrim> findByCriteria(CriteriosBusqueda criterios) {

        return jpaRepository.findAll().stream()

                .map(mapper::toDomain)

                .filter(criterios::coincideCon)

                .collect(Collectors.toList());

    }



    @Override

    public List<Usuario> findCandidatosParaScrim(Scrim scrim) {

        return postulacionJpaRepository.findByScrimId(scrim.getId()).stream()

                .filter(p -> "ACEPTADA".equalsIgnoreCase(p.getEstado()))

                .map(PostulacionEntity::getUsuarioId)

                .map(usuarioRepository::findById)

                .flatMap(Optional::stream)

                .collect(Collectors.toList());

    }



    @Override

    public MatchmakingContext buildMatchmakingContext(Scrim scrim) {

        return matchmakingContextAssembler.build(scrim);

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



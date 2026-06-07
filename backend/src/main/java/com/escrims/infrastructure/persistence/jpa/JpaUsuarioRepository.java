package com.escrims.infrastructure.persistence.jpa;

import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.persistence.jpa.mapper.UsuarioEntityMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class JpaUsuarioRepository implements UsuarioRepository {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioEntityMapper mapper;

    public JpaUsuarioRepository(UsuarioJpaRepository jpaRepository, UsuarioEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaRepository.findByEmailIgnoreCase(email).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return jpaRepository.findByUsernameIgnoreCase(username).map(mapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByCredencialOAuth(String proveedorNombre, String externalId) {
        return jpaRepository.findByOAuthCredencial(proveedorNombre, externalId).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findCandidatosParaScrim(Scrim scrim) {
        Set<UUID> participantes = new HashSet<>(scrim.getParticipantesIds());
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .filter(u -> !participantes.contains(u.getId()))
                .filter(u -> !u.estaBloqueado())
                .filter(u -> {
                    if (u.getRangoPara(scrim.getNombreJuego()) == null) return false;
                    return scrim.getRangosPermitidos().contiene(u.getRangoPara(scrim.getNombreJuego()));
                })
                .collect(Collectors.toList());
    }

    @Override
    public Usuario save(Usuario usuario) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(usuario)));
    }
}

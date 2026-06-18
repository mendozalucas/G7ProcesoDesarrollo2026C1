package com.escrims.domain.repository;

import com.escrims.domain.model.usuario.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Optional<Usuario> findById(UUID id);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    Usuario save(Usuario usuario);

    List<Usuario> findAll();
}

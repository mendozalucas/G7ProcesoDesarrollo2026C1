package com.escrims.infrastructure.persistence.jpa.mapper;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.model.usuario.factory.UsuarioFactoryRegistry;
import com.escrims.infrastructure.persistence.jpa.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEntityMapper {

    private final UsuarioFactoryRegistry usuarioFactoryRegistry;

    public UsuarioEntityMapper(UsuarioFactoryRegistry usuarioFactoryRegistry) {
        this.usuarioFactoryRegistry = usuarioFactoryRegistry;
    }

    public Usuario toDomain(UsuarioEntity entity) {
        Usuario usuario = usuarioFactoryRegistry.crear(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                entity.getRol());
        usuario.setVerificado(entity.isVerificado());
        return usuario;
    }

    public UsuarioEntity toEntity(Usuario usuario) {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(usuario.getId());
        entity.setUsername(usuario.getUsername());
        entity.setEmail(usuario.getEmail());
        entity.setPasswordHash(usuario.getPasswordHash());
        entity.setVerificado(usuario.isVerificado());
        entity.setRol(usuario.getTipoUsuario());
        return entity;
    }
}

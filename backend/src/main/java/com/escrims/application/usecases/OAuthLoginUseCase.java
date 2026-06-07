package com.escrims.application.usecases;

import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.CredencialOAuth;
import com.escrims.domain.valueobjects.OAuthProveedor;
import com.escrims.domain.valueobjects.OAuthProveedorFactory;
import com.escrims.infrastructure.security.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OAuthLoginUseCase {

    private final UsuarioRepository usuarioRepository;

    public OAuthLoginUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UUID execute(String proveedorNombre, String externalId, String email, String username) {
        OAuthProveedor proveedor = OAuthProveedorFactory.para(proveedorNombre);

        return usuarioRepository.findByCredencialOAuth(proveedor.getNombre(), externalId)
                .map(Usuario::getId)
                .orElseGet(() -> vincularOCrear(proveedor, externalId, email, username));
    }

    private UUID vincularOCrear(OAuthProveedor proveedor, String externalId, String email, String username) {
        CredencialOAuth credencial = new CredencialOAuth(proveedor, externalId);

        if (email != null && !email.isBlank()) {
            return usuarioRepository.findByEmail(email)
                    .map(usuario -> {
                        usuario.agregarCredencialOAuth(credencial);
                        usuarioRepository.save(usuario);
                        return usuario.getId();
                    })
                    .orElseGet(() -> crearUsuarioOAuth(proveedor, externalId, email, username));
        }

        return crearUsuarioOAuth(proveedor, externalId, email, username);
    }

    private UUID crearUsuarioOAuth(OAuthProveedor proveedor, String externalId, String email, String username) {
        String resolvedEmail = (email != null && !email.isBlank())
                ? email
                : proveedor.getNombre().toLowerCase() + "_" + externalId + "@oauth.local";
        String resolvedUsername = (username != null && !username.isBlank())
                ? username
                : proveedor.getNombre() + "_" + externalId;

        if (usuarioRepository.findByEmail(resolvedEmail).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        if (usuarioRepository.findByUsername(resolvedUsername).isPresent()) {
            resolvedUsername = resolvedUsername + "_" + UUID.randomUUID().toString().substring(0, 8);
        }

        Usuario usuario = new Usuario(
                UUID.randomUUID(),
                resolvedUsername,
                resolvedEmail,
                PasswordHasher.hash(UUID.randomUUID().toString()));
        usuario.agregarCredencialOAuth(new CredencialOAuth(proveedor, externalId));
        usuario.verificarEmail();
        usuarioRepository.save(usuario);
        return usuario.getId();
    }
}

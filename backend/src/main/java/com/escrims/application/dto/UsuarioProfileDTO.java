package com.escrims.application.dto;

import com.escrims.domain.model.usuario.Usuario;

import java.util.UUID;

public class UsuarioProfileDTO {

    private UUID id;
    private String username;
    private String email;
    private boolean verificado;

    public static UsuarioProfileDTO from(Usuario usuario) {
        UsuarioProfileDTO dto = new UsuarioProfileDTO();
        dto.id = usuario.getId();
        dto.username = usuario.getUsername();
        dto.email = usuario.getEmail();
        dto.verificado = usuario.isVerificado();
        return dto;
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isVerificado() { return verificado; }
}

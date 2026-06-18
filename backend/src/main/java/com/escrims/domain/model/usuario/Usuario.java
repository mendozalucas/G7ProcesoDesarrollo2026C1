package com.escrims.domain.model.usuario;

import java.util.UUID;

public abstract class Usuario {

    private final UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private boolean verificado;

    protected Usuario(UUID id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.verificado = false;
    }

    public abstract String getTipoUsuario();

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
    public void setUsername(String username) { this.username = username; }
}

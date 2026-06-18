package com.escrims.domain.model.usuario;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.Scrim;

import java.util.UUID;

public class Usuario {

    private final UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private boolean verificado;

    public Usuario(UUID id, String username, String email, String passwordHash) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.verificado = false;
    }

    public Postulacion postular(Scrim scrim, Rol rol) {
        return new Postulacion(null, this, scrim, rol, EstadoPostulacion.PENDIENTE);
    }

    public Confirmacion confirmar(Scrim scrim) {
        return new Confirmacion(null, this, scrim, true);
    }

    public UUID getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }
}

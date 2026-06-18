package com.escrims.domain.composite;

import com.escrims.domain.model.usuario.Usuario;

import java.util.UUID;

public class Participante implements JugadorComponent {

    private final Usuario usuario;
    private String rolNombre;
    private String lado;

    public Participante(Usuario usuario, String rolNombre, String lado) {
        this.usuario = usuario;
        this.rolNombre = rolNombre != null ? rolNombre : "";
        this.lado = lado != null ? lado : "A";
    }

    @Override
    public UUID getUsuarioId() {
        return usuario.getId();
    }

    @Override
    public String getRolNombre() {
        return rolNombre;
    }

    @Override
    public String getLado() {
        return lado;
    }

    @Override
    public void asignarLado(String lado) {
        this.lado = lado;
    }

    @Override
    public void asignarRol(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}

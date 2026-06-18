package com.escrims.domain.model.postulacion;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Usuario;

public class Postulacion {

    private Long id;
    private Usuario usuario;
    private Scrim scrim;
    private Rol rolDeseado;
    private EstadoPostulacion estado;

    public Postulacion(Long id, Usuario usuario, Scrim scrim, Rol rolDeseado, EstadoPostulacion estado) {
        this.id = id;
        this.usuario = usuario;
        this.scrim = scrim;
        this.rolDeseado = rolDeseado;
        this.estado = estado != null ? estado : EstadoPostulacion.PENDIENTE;
    }

    public void aceptar() {
        this.estado = EstadoPostulacion.ACEPTADA;
    }

    public void rechazar() {
        this.estado = EstadoPostulacion.RECHAZADA;
    }

    public boolean estaPendiente() {
        return estado == EstadoPostulacion.PENDIENTE;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public Scrim getScrim() { return scrim; }
    public Rol getRolDeseado() { return rolDeseado; }
    public EstadoPostulacion getEstado() { return estado; }
}

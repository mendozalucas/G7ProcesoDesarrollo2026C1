package com.escrims.domain.model.usuario;

import com.escrims.domain.facade.LobbyFacadePort;
import com.escrims.domain.facade.ModeracionFacadePort;
import com.escrims.domain.facade.ScrimFacadePort;
import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.CreateScrimRequest;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.Modalidad;

import java.util.UUID;

public class Jugador extends Usuario {

    private Modalidad modalidad;
    private PerfilJuego perfilJuego;
    private ScrimFacadePort scrimFacade;
    private LobbyFacadePort lobbyFacade;
    private ModeracionFacadePort moderacionFacade;

    public Jugador(UUID id, String username, String email, String passwordHash) {
        super(id, username, email, passwordHash);
    }

    @Override
    public String getTipoUsuario() {
        return "JUGADOR";
    }

    public void conectarFacades(ScrimFacadePort scrimFacade,
                                LobbyFacadePort lobbyFacade,
                                ModeracionFacadePort moderacionFacade) {
        this.scrimFacade = scrimFacade;
        this.lobbyFacade = lobbyFacade;
        this.moderacionFacade = moderacionFacade;
    }

    public Postulacion postular(Scrim scrim, Rol rol) {
        if (scrimFacade != null) {
            return scrimFacade.postularse(this, scrim, rol);
        }
        return new Postulacion(null, this, scrim, rol, EstadoPostulacion.PENDIENTE);
    }

    public Confirmacion confirmar(Scrim scrim) {
        if (scrimFacade != null) {
            return scrimFacade.confirmarJugador(this, scrim);
        }
        return new Confirmacion(null, this, scrim, true);
    }

    public Scrim crearScrim(CreateScrimRequest request) {
        if (scrimFacade != null) {
            return scrimFacade.crearScrim(this, request);
        }
        throw new IllegalStateException("ScrimFacade no configurado");
    }

    public ReporteConducta reportarJugador(Jugador reportado, String motivo) {
        if (moderacionFacade != null) {
            return moderacionFacade.reportarJugador(this, reportado, motivo);
        }
        return new ReporteConducta(null, motivo);
    }

    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }
    public PerfilJuego getPerfilJuego() { return perfilJuego; }
    public void setPerfilJuego(PerfilJuego perfilJuego) { this.perfilJuego = perfilJuego; }
    public ScrimFacadePort getScrimFacade() { return scrimFacade; }
    public LobbyFacadePort getLobbyFacade() { return lobbyFacade; }
    public ModeracionFacadePort getModeracionFacade() { return moderacionFacade; }
}

package com.escrims.domain.model.lobby;

import com.escrims.domain.model.postulacion.EstadoPostulacion;
import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.valueobjects.FormatoScrim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Lobby {

    private final UUID scrimId;
    private final List<Postulacion> postulaciones = new ArrayList<>();
    private final List<Confirmacion> confirmaciones = new ArrayList<>();
    private final GestorLobby gestorLobby;
    private FormatoScrim formato;

    public Lobby(UUID scrimId, FormatoScrim formato) {
        this.scrimId = scrimId;
        this.formato = formato != null ? formato : new FormatoScrim(5);
        this.gestorLobby = new GestorLobby();
    }

    public Postulacion agregarPostulacion(Jugador jugador, Rol rol, Scrim scrim) {
        Postulacion postulacion = new Postulacion(null, jugador, scrim, rol, EstadoPostulacion.PENDIENTE);
        postulaciones.add(postulacion);
        return postulacion;
    }

    public Confirmacion agregarConfirmacion(Jugador jugador, Scrim scrim) {
        Confirmacion confirmacion = new Confirmacion(null, jugador, scrim, false);
        confirmaciones.add(confirmacion);
        return confirmacion;
    }

    public boolean cupoCompleto() {
        int total = gestorLobby.getEquipoA().getCuposOcupados() + gestorLobby.getEquipoB().getCuposOcupados();
        return total >= formato.getTotalJugadores();
    }

    public boolean todosConfirmados() {
        return !confirmaciones.isEmpty() && confirmaciones.stream().allMatch(Confirmacion::isConfirmado);
    }

    public UUID getScrimId() { return scrimId; }
    public List<Postulacion> getPostulaciones() { return Collections.unmodifiableList(postulaciones); }
    public List<Confirmacion> getConfirmaciones() { return Collections.unmodifiableList(confirmaciones); }
    public GestorLobby getGestorLobby() { return gestorLobby; }
    public FormatoScrim getFormato() { return formato; }
    public void setFormato(FormatoScrim formato) { this.formato = formato; }

    /** IDs de jugadores en ambos equipos (compatibilidad con eventos y persistencia). */
    public List<UUID> getParticipantes() {
        List<UUID> ids = new ArrayList<>();
        gestorLobby.getEquipoA().getJugadores().forEach(j -> ids.add(j.getId()));
        gestorLobby.getEquipoB().getJugadores().forEach(j -> ids.add(j.getId()));
        return Collections.unmodifiableList(ids);
    }
}

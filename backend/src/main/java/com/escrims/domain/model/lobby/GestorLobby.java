package com.escrims.domain.model.lobby;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Equipo;
import com.escrims.domain.model.usuario.Jugador;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GestorLobby {

    private final Equipo equipoA;
    private final Equipo equipoB;
    private final Map<UUID, Rol> rolesAsignados = new HashMap<>();

    public GestorLobby() {
        this.equipoA = new Equipo(1L, "Equipo A");
        this.equipoB = new Equipo(2L, "Equipo B");
    }

    public void asignarRol(Jugador jugador, Rol rol) {
        rolesAsignados.put(jugador.getId(), rol);
    }

    public void swapJugadores(Jugador j1, Jugador j2) {
        boolean j1EnA = equipoA.getJugadores().contains(j1);
        boolean j2EnA = equipoA.getJugadores().contains(j2);
        boolean j1EnB = equipoB.getJugadores().contains(j1);
        boolean j2EnB = equipoB.getJugadores().contains(j2);

        if (j1EnA && j2EnB) {
            equipoA.quitarJugador(j1);
            equipoB.quitarJugador(j2);
            equipoA.agregarJugador(j2);
            equipoB.agregarJugador(j1);
        } else if (j1EnB && j2EnA) {
            equipoB.quitarJugador(j1);
            equipoA.quitarJugador(j2);
            equipoB.agregarJugador(j2);
            equipoA.agregarJugador(j1);
        }
    }

    public void invitarJugador(Jugador jugador) {
        balancearEquipos(jugador);
    }

    public void balancearEquipos(Jugador jugador) {
        if (contieneJugador(jugador)) {
            return;
        }
        if (equipoA.getCuposOcupados() <= equipoB.getCuposOcupados()) {
            equipoA.agregarJugador(jugador);
        } else {
            equipoB.agregarJugador(jugador);
        }
    }

    public void quitarJugador(Jugador jugador) {
        equipoA.quitarJugador(jugador);
        equipoB.quitarJugador(jugador);
        rolesAsignados.remove(jugador.getId());
    }

    public boolean contieneJugador(Jugador jugador) {
        return equipoA.getJugadores().contains(jugador) || equipoB.getJugadores().contains(jugador);
    }

    public Equipo getEquipoA() { return equipoA; }
    public Equipo getEquipoB() { return equipoB; }
    public Map<UUID, Rol> getRolesAsignados() { return Map.copyOf(rolesAsignados); }
    public Rol getRolDe(Jugador jugador) { return rolesAsignados.get(jugador.getId()); }

    public void quitarRol(Jugador jugador) {
        rolesAsignados.remove(jugador.getId());
    }
}

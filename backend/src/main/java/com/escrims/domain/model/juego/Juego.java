package com.escrims.domain.model.juego;

import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.valueobjects.Rango;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase abstracta del patrón Template Method (ver diagrama: ValorantJuego, LolJuego, CS2Juego).
 */
public abstract class Juego {

    private final String nombre;
    private Rango rango;
    private Rol rol;
    private final List<Rol> rolesPreferidos = new ArrayList<>();

    protected Juego(String nombre) {
        this.nombre = nombre;
    }

    protected Juego(String nombre, Rango rango, Rol rol) {
        this.nombre = nombre;
        this.rango = rango;
        this.rol = rol;
    }

    public final boolean validar(Scrim scrim) {
        return validarRoles(scrim) && validarRangos(scrim) && validarReglasEspecificas(scrim);
    }

    public abstract boolean validarRoles(Scrim scrim);

    public abstract boolean validarRangos(Scrim scrim);

    public abstract boolean validarReglasEspecificas(Scrim scrim);

    public String getNombre() { return nombre; }
    public Rango getRango() { return rango; }
    public Rol getRol() { return rol; }
    public List<Rol> getRolesPreferidos() { return Collections.unmodifiableList(rolesPreferidos); }

    public void agregarRolPreferido(Rol rolPreferido) {
        rolesPreferidos.add(rolPreferido);
    }
}

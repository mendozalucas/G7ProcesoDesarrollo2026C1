package com.escrims.domain.model.usuario;

import com.escrims.domain.model.juego.Juego;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerfilJuego {

    private final Juego juego;
    private final List<RolJuego> rolesPreferidos;
    private Region region;
    private Integer mmr;

    public PerfilJuego(Juego juego, Region region, Integer mmr) {
        this.juego = juego;
        this.region = region;
        this.mmr = mmr;
        this.rolesPreferidos = new ArrayList<>();
    }

    public void agregarRolPreferido(RolJuego rol) {
        rolesPreferidos.add(rol);
    }

    public Juego getJuego() { return juego; }
    public Region getRegion() { return region; }
    public Integer getMmr() { return mmr; }
    public List<RolJuego> getRolesPreferidos() { return Collections.unmodifiableList(rolesPreferidos); }
}

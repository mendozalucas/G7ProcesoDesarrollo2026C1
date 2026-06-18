package com.escrims.domain.model.busqueda;

import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.valueobjects.Region;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusquedaFavoritaTest {

    @Test
    void coincideCon_mismoJuegoYRegion() {
        var org = TestFixtures.jugador("org");
        var scrim = TestFixtures.scrim(org);
        BusquedaFavorita busqueda = new BusquedaFavorita(
                JuegoFactory.para("valorant"),
                null, null,
                new Region(null, "LAN/AR"),
                null,
                new Rol(null, "Duelist"));

        assertTrue(busqueda.coincideCon(scrim));
    }

    @Test
    void coincideCon_rechazaRegionDistinta() {
        var org = TestFixtures.jugador("org");
        var scrim = TestFixtures.scrim(org);
        BusquedaFavorita busqueda = new BusquedaFavorita(
                JuegoFactory.para("valorant"),
                null, null,
                new Region(null, "NA/EAST"),
                null,
                null);

        assertFalse(busqueda.coincideCon(scrim));
    }
}

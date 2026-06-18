package com.escrims.domain.facade;

import com.escrims.domain.model.postulacion.Postulacion;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.Confirmacion;
import com.escrims.domain.model.scrim.CreateScrimRequest;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;

/** Contrato del facade de scrims (referenciado por {@link Jugador}). */
public interface ScrimFacadePort {

    Postulacion postularse(Jugador jugador, Scrim scrim, Rol rol);

    Confirmacion confirmarJugador(Jugador jugador, Scrim scrim);

    Scrim crearScrim(Jugador organizador, CreateScrimRequest request);
}

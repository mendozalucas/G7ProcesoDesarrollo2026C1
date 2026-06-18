package com.escrims.domain.facade;

import com.escrims.domain.model.rating.Rating;
import com.escrims.domain.model.reporte.ReporteConducta;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;

/** Contrato del facade de moderación (referenciado por {@link Jugador}). */
public interface ModeracionFacadePort {

    ReporteConducta reportarJugador(Jugador jugador, Jugador reportado, String motivo);

    Rating calificarJugador(Jugador califica, Jugador calificado, Scrim scrim,
                            int puntuacion, String comentario);
}

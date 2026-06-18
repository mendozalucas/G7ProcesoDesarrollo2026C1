package com.escrims.domain.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ModalidadFactoryTest {

    @Test
    void para_casualPorDefectoConNombreVacio() {
        Modalidad modalidad = ModalidadFactory.para(null);
        assertInstanceOf(ModalidadCasual.class, modalidad);
        assertEquals("CASUAL", modalidad.getNombre());
    }

    @Test
    void para_rankedLike() {
        Modalidad modalidad = ModalidadFactory.para("RANKED_LIKE");
        assertInstanceOf(ModalidadRankedLike.class, modalidad);
    }

    @Test
    void para_practicaEstratos() {
        Modalidad modalidad = ModalidadFactory.para("PRACTICA");
        assertInstanceOf(ModalidadPracticaEstratos.class, modalidad);
    }

    @Test
    void para_desconocido_usaCasual() {
        Modalidad modalidad = ModalidadFactory.para("OTRO");
        assertInstanceOf(ModalidadCasual.class, modalidad);
    }
}

package com.escrims.domain.valueobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ModalidadFactory {

    private static final Map<String, Supplier<Modalidad>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("CASUAL", ModalidadCasual::new);
        REGISTRO.put("RANKED_LIKE", ModalidadRankedLike::new);
        REGISTRO.put("RANKED", ModalidadRankedLike::new);
        REGISTRO.put("PRACTICA_ESTRATOS", ModalidadPracticaEstratos::new);
        REGISTRO.put("PRACTICA", ModalidadPracticaEstratos::new);
    }

    private ModalidadFactory() {}

    public static Modalidad para(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return new ModalidadCasual();
        }
        Supplier<Modalidad> proveedor = REGISTRO.get(nombre.toUpperCase());
        return proveedor != null ? proveedor.get() : new ModalidadCasual();
    }
}

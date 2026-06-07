package com.escrims.domain.state.postulacion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class PostulacionStateFactory {

    private static final Map<String, Supplier<PostulacionState>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("ACEPTADA", AceptadaPostulacionState::new);
        REGISTRO.put("RECHAZADA", RechazadaPostulacionState::new);
        REGISTRO.put("PENDIENTE", PendientePostulacionState::new);
    }

    private PostulacionStateFactory() {}

    public static PostulacionState para(String nombre) {
        if (nombre == null) {
            return new PendientePostulacionState();
        }
        Supplier<PostulacionState> proveedor = REGISTRO.get(nombre.toUpperCase());
        return proveedor != null ? proveedor.get() : new PendientePostulacionState();
    }
}

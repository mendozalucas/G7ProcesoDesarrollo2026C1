package com.escrims.domain.model.usuario;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class RolSistemaFactory {

    private static final Map<String, Supplier<RolSistema>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("ADMIN", RolAdmin::new);
        REGISTRO.put("MODERADOR", RolModerador::new);
        REGISTRO.put("MOD", RolModerador::new);
        REGISTRO.put("USER", RolUser::new);
    }

    private RolSistemaFactory() {}

    public static RolSistema para(String nombre) {
        if (nombre == null) {
            return new RolUser();
        }
        Supplier<RolSistema> proveedor = REGISTRO.get(nombre.toUpperCase());
        return proveedor != null ? proveedor.get() : new RolUser();
    }

    public static String nombre(RolSistema rol) {
        return rol.getNombre();
    }
}

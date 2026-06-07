package com.escrims.domain.valueobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class OAuthProveedorFactory {

    private static final Map<String, Supplier<OAuthProveedor>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("STEAM", SteamProveedor::new);
        REGISTRO.put("RIOT", RiotProveedor::new);
        REGISTRO.put("DISCORD", DiscordProveedor::new);
    }

    private OAuthProveedorFactory() {}

    public static OAuthProveedor para(String nombre) {
        if (nombre == null) {
            throw new IllegalArgumentException("Proveedor OAuth requerido");
        }
        Supplier<OAuthProveedor> proveedor = REGISTRO.get(nombre.toUpperCase());
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor OAuth no soportado: " + nombre);
        }
        return proveedor.get();
    }
}

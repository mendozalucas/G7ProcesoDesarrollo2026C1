package com.escrims.domain.strategy.sancion;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class SancionFactory {

    private static final Map<String, Supplier<SancionEstrategia>> REGISTRO = new HashMap<>();

    static {
        REGISTRO.put("STRIKE", StrikeSancion::new);
        REGISTRO.put("WARNING", WarningSancion::new);
        REGISTRO.put("BAN", BanSancion::new);
    }

    private SancionFactory() {}

    public static SancionEstrategia para(String tipo) {
        if (tipo == null) {
            return null;
        }
        String normalizado = tipo.toUpperCase();
        if (normalizado.startsWith("COOLDOWN")) {
            return new CooldownSancion(extraerDiasCooldown(normalizado));
        }
        Supplier<SancionEstrategia> proveedor = REGISTRO.get(normalizado);
        return proveedor != null ? proveedor.get() : null;
    }

    public static String nombre(SancionEstrategia sancion) {
        return sancion != null ? sancion.getNombre() : null;
    }

    private static int extraerDiasCooldown(String tipo) {
        if ("COOLDOWN".equals(tipo)) {
            return 7;
        }
        int indice = tipo.indexOf('_');
        if (indice > 0 && tipo.endsWith("D")) {
            try {
                return Integer.parseInt(tipo.substring(indice + 1, tipo.length() - 1));
            } catch (NumberFormatException ignored) {
                return 7;
            }
        }
        return 7;
    }
}

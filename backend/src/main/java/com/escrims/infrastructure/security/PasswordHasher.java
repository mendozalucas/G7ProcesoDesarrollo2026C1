package com.escrims.infrastructure.security;

public class PasswordHasher {

    private PasswordHasher() {}

    public static String hash(String plainPassword) {
        // En producción: BCrypt.hashpw(plainPassword, BCrypt.gensalt())
        return "hashed:" + plainPassword;
    }

    public static boolean verificar(String plainPassword, String hash) {
        // En producción: BCrypt.checkpw(plainPassword, hash)
        return hash.equals("hashed:" + plainPassword);
    }
}

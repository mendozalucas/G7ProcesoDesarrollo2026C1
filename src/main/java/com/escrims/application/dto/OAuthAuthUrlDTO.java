package com.escrims.application.dto;

public class OAuthAuthUrlDTO {

    private final String proveedor;
    private final String authUrl;

    public OAuthAuthUrlDTO(String proveedor, String authUrl) {
        this.proveedor = proveedor;
        this.authUrl = authUrl;
    }

    public String getProveedor() { return proveedor; }
    public String getAuthUrl() { return authUrl; }
}

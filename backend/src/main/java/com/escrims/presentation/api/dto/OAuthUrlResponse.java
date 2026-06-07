package com.escrims.presentation.api.dto;

public class OAuthUrlResponse {

    private final String proveedor;
    private final String authUrl;

    public OAuthUrlResponse(String proveedor, String authUrl) {
        this.proveedor = proveedor;
        this.authUrl = authUrl;
    }

    public String getProveedor() { return proveedor; }
    public String getAuthUrl() { return authUrl; }
}

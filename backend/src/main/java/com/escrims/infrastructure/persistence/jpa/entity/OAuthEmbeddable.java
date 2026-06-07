package com.escrims.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class OAuthEmbeddable {

    private String proveedor;
    private String externalId;

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }
}

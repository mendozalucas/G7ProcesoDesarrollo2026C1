package com.escrims.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object.
 * Usa OAuthProveedor (interfaz) en lugar del enum ProveedorOAuth.
 */
public final class CredencialOAuth {

    private final OAuthProveedor proveedor;
    private final String externalId;

    public CredencialOAuth(OAuthProveedor proveedor, String externalId) {
        this.proveedor = proveedor;
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CredencialOAuth)) return false;
        CredencialOAuth that = (CredencialOAuth) o;
        return Objects.equals(proveedor.getNombre(), that.proveedor.getNombre())
                && Objects.equals(externalId, that.externalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proveedor.getNombre(), externalId);
    }

    public OAuthProveedor getProveedor() { return proveedor; }
    public String getExternalId() { return externalId; }
}

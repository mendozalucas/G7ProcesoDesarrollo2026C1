package com.escrims.application.usecases;

import com.escrims.application.dto.OAuthAuthUrlDTO;
import org.springframework.stereotype.Service;

@Service
public class GetOAuthAuthUrlUseCase {

    public OAuthAuthUrlDTO execute(String proveedorNombre) {
        String proveedor = proveedorNombre.toUpperCase();
        String url = "https://oauth." + proveedorNombre.toLowerCase() + ".example/authorize"
                + "?client_id=escrims-dev"
                + "&redirect_uri=http://localhost:8080/api/auth/oauth/callback"
                + "&response_type=code"
                + "&state=" + proveedorNombre.toLowerCase();
        return new OAuthAuthUrlDTO(proveedor, url);
    }
}

package com.escrims.application.usecases;

import com.escrims.application.dto.OAuthAuthUrlDTO;
import com.escrims.domain.valueobjects.OAuthProveedor;
import com.escrims.domain.valueobjects.OAuthProveedorFactory;
import org.springframework.stereotype.Service;

@Service
public class GetOAuthAuthUrlUseCase {

    public OAuthAuthUrlDTO execute(String proveedorNombre) {
        OAuthProveedor proveedor = OAuthProveedorFactory.para(proveedorNombre);
        String url = proveedor.getAuthUrlBase()
                + "?client_id=escrims-dev"
                + "&redirect_uri=http://localhost:8080/api/auth/oauth/callback"
                + "&response_type=code"
                + "&state=" + proveedor.getNombre().toLowerCase();
        return new OAuthAuthUrlDTO(proveedor.getNombre(), url);
    }
}

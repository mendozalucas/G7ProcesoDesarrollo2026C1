package com.escrims.presentation.api;

import com.escrims.application.usecases.GetOAuthAuthUrlUseCase;
import com.escrims.application.usecases.LoginUseCase;
import com.escrims.application.usecases.OAuthLoginUseCase;
import com.escrims.application.usecases.RegisterUserUseCase;
import com.escrims.infrastructure.security.PasswordHasher;
import com.escrims.presentation.api.dto.AuthResponse;
import com.escrims.presentation.api.dto.LoginRequest;
import com.escrims.presentation.api.dto.OAuthLoginRequest;
import com.escrims.presentation.api.dto.OAuthUrlResponse;
import com.escrims.presentation.api.dto.RegisterRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final GetOAuthAuthUrlUseCase getOAuthAuthUrlUseCase;

    public AuthController(RegisterUserUseCase registerUserUseCase,
                          LoginUseCase loginUseCase,
                          OAuthLoginUseCase oAuthLoginUseCase,
                          GetOAuthAuthUrlUseCase getOAuthAuthUrlUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.oAuthLoginUseCase = oAuthLoginUseCase;
        this.getOAuthAuthUrlUseCase = getOAuthAuthUrlUseCase;
    }

    @PostMapping("/register")
    public AuthResponse registrar(@RequestBody RegisterRequest request) {
        String hash = PasswordHasher.hash(request.getPassword());
        return new AuthResponse(registerUserUseCase.execute(
                request.getUsername(), request.getEmail(), hash));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return new AuthResponse(loginUseCase.execute(request.getEmail(), request.getPassword()));
    }

    @GetMapping("/oauth/{proveedor}/url")
    public OAuthUrlResponse obtenerUrlOAuth(@PathVariable String proveedor) {
        var dto = getOAuthAuthUrlUseCase.execute(proveedor);
        return new OAuthUrlResponse(dto.getProveedor(), dto.getAuthUrl());
    }

    @PostMapping("/oauth/login")
    public AuthResponse loginOAuth(@RequestBody OAuthLoginRequest request) {
        return new AuthResponse(oAuthLoginUseCase.execute(
                request.getProveedor(),
                request.getExternalId(),
                request.getEmail(),
                request.getUsername()));
    }
}

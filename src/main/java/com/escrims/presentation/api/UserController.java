package com.escrims.presentation.api;

import com.escrims.application.dto.UpdateProfileCommand;
import com.escrims.application.dto.UsuarioProfileDTO;
import com.escrims.application.usecases.GetUserProfileUseCase;
import com.escrims.application.usecases.UpdateUserProfileUseCase;
import com.escrims.application.usecases.VerifyEmailUseCase;
import com.escrims.presentation.api.dto.UpdateProfileRequest;
import com.escrims.presentation.api.mapper.ProfileRequestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    private final GetUserProfileUseCase getUserProfileUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;

    public UserController(GetUserProfileUseCase getUserProfileUseCase,
                          UpdateUserProfileUseCase updateUserProfileUseCase,
                          VerifyEmailUseCase verifyEmailUseCase) {
        this.getUserProfileUseCase = getUserProfileUseCase;
        this.updateUserProfileUseCase = updateUserProfileUseCase;
        this.verifyEmailUseCase = verifyEmailUseCase;
    }

    @GetMapping("/{id}")
    public UsuarioProfileDTO obtenerPerfil(@PathVariable UUID id) {
        return getUserProfileUseCase.execute(id);
    }

    @PutMapping("/{id}/perfil")
    public UsuarioProfileDTO actualizarPerfil(@PathVariable UUID id,
                                               @RequestBody UpdateProfileRequest request) {
        UpdateProfileCommand command = ProfileRequestMapper.toCommand(request);
        return updateUserProfileUseCase.execute(id, command);
    }

    @PostMapping("/{id}/verificar-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verificarEmail(@PathVariable UUID id) {
        verifyEmailUseCase.execute(id);
    }
}

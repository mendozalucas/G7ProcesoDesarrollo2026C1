package com.escrims.application.usecases;

import com.escrims.application.dto.UpdateProfileCommand;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.model.usuario.factory.FactoryJugador;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.security.PasswordHasher;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthAndUserUseCasesTest {

    @Mock private UsuarioRepository usuarioRepository;

    @Test
    void registerUser_creaJugador() {
        RegisterUserUseCase useCase = new RegisterUserUseCase(usuarioRepository, new FactoryJugador());
        when(usuarioRepository.findByEmail("new@test.local")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UUID id = useCase.execute("newuser", "new@test.local", "hash");

        assertNotNullId(id);
        verify(usuarioRepository).save(any(Jugador.class));
    }

    @Test
    void registerUser_rechazaEmailDuplicado() {
        RegisterUserUseCase useCase = new RegisterUserUseCase(usuarioRepository, new FactoryJugador());
        Jugador existente = TestFixtures.jugador("old");
        when(usuarioRepository.findByEmail("old@test.local")).thenReturn(Optional.of(existente));

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("new", "old@test.local", "hash"));
    }

    @Test
    void login_credencialesValidas() {
        LoginUseCase useCase = new LoginUseCase(usuarioRepository);
        String hash = PasswordHasher.hash("secret123");
        Jugador jugador = new Jugador(UUID.randomUUID(), "user", "user@test.local", hash);
        when(usuarioRepository.findByEmail("user@test.local")).thenReturn(Optional.of(jugador));

        UUID id = useCase.execute("user@test.local", "secret123");

        assertEquals(jugador.getId(), id);
    }

    @Test
    void login_credencialesInvalidas() {
        LoginUseCase useCase = new LoginUseCase(usuarioRepository);
        when(usuarioRepository.findByEmail("user@test.local")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute("user@test.local", "wrong"));
    }

    @Test
    void oAuthLogin_creaUsuarioSiNoExiste() {
        OAuthLoginUseCase useCase = new OAuthLoginUseCase(usuarioRepository, new FactoryJugador());
        when(usuarioRepository.findByEmail("google_123@oauth.local")).thenReturn(Optional.empty());
        when(usuarioRepository.findByUsername("google_123")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UUID id = useCase.execute("google", "123", null, null);

        assertNotNullId(id);
        verify(usuarioRepository).save(any(Jugador.class));
    }

    @Test
    void getOAuthAuthUrl_generaUrl() {
        GetOAuthAuthUrlUseCase useCase = new GetOAuthAuthUrlUseCase();
        var dto = useCase.execute("google");

        assertEquals("GOOGLE", dto.getProveedor());
        assertTrue(dto.getAuthUrl().contains("google"));
    }

    @Test
    void getUserProfile_devuelveDto() {
        GetUserProfileUseCase useCase = new GetUserProfileUseCase(usuarioRepository);
        Jugador jugador = TestFixtures.jugador("j");
        when(usuarioRepository.findById(jugador.getId())).thenReturn(Optional.of(jugador));

        var dto = useCase.execute(jugador.getId());

        assertEquals("j", dto.getUsername());
    }

    @Test
    void updateUserProfile_actualizaUsername() {
        UpdateUserProfileUseCase useCase = new UpdateUserProfileUseCase(usuarioRepository);
        Jugador jugador = TestFixtures.jugador("old");
        when(usuarioRepository.findById(jugador.getId())).thenReturn(Optional.of(jugador));
        when(usuarioRepository.findByUsername("new")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UpdateProfileCommand cmd = new UpdateProfileCommand();
        cmd.setUsername("new");

        var dto = useCase.execute(jugador.getId(), cmd);

        assertEquals("new", dto.getUsername());
    }

    @Test
    void verifyEmail_marcaComoVerificado() {
        VerifyEmailUseCase useCase = new VerifyEmailUseCase(usuarioRepository);
        Jugador jugador = TestFixtures.jugador("j");
        when(usuarioRepository.findById(jugador.getId())).thenReturn(Optional.of(jugador));

        useCase.execute(jugador.getId());

        assertTrue(jugador.isVerificado());
        verify(usuarioRepository).save(jugador);
    }

    private static void assertNotNullId(UUID id) {
        org.junit.jupiter.api.Assertions.assertNotNull(id);
    }
}

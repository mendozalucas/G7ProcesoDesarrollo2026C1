package com.escrims.presentation.api;



import com.escrims.application.dto.ScrimResponseDTO;

import com.escrims.application.dto.UsuarioProfileDTO;

import com.escrims.application.facade.JugadorFacadeBinder;

import com.escrims.application.facade.LobbyFacade;

import com.escrims.application.facade.ModeracionFacade;

import com.escrims.application.facade.ScrimFacade;

import com.escrims.application.usecases.*;

import com.escrims.domain.model.scrim.Scrim;

import com.escrims.domain.model.usuario.Jugador;

import com.escrims.domain.repository.ScrimRepository;

import com.escrims.domain.repository.UsuarioRepository;

import com.escrims.support.TestFixtures;

import com.escrims.application.dto.CreateScrimDTO;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;



import java.util.List;

import java.util.Optional;

import java.util.UUID;



import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(controllers = {AuthController.class, ScrimController.class, UserController.class,

        ModerationController.class, BusquedaFavoritaController.class, GlobalExceptionHandler.class})

class ApiControllersTest {



    @Autowired private MockMvc mockMvc;



    @MockBean private RegisterUserUseCase registerUserUseCase;

    @MockBean private LoginUseCase loginUseCase;

    @MockBean private OAuthLoginUseCase oAuthLoginUseCase;

    @MockBean private GetOAuthAuthUrlUseCase getOAuthAuthUrlUseCase;

    @MockBean private ScrimFacade scrimFacade;

    @MockBean private LobbyFacade lobbyFacade;

    @MockBean private SearchScrimsUseCase searchScrimsUseCase;

    @MockBean private GetScrimUseCase getScrimUseCase;

    @MockBean private AcceptPostulacionUseCase acceptPostulacionUseCase;

    @MockBean private CancelScrimUseCase cancelScrimUseCase;

    @MockBean private ListPostulacionesByScrimUseCase listPostulacionesByScrimUseCase;

    @MockBean private GetUserProfileUseCase getUserProfileUseCase;

    @MockBean private UpdateUserProfileUseCase updateUserProfileUseCase;

    @MockBean private VerifyEmailUseCase verifyEmailUseCase;

    @MockBean private ModeracionFacade moderacionFacade;

    @MockBean private ModerateReportUseCase moderateReportUseCase;

    @MockBean private ListBusquedasFavoritasUseCase listBusquedasFavoritasUseCase;

    @MockBean private SaveBusquedaFavoritaUseCase saveBusquedaFavoritaUseCase;

    @MockBean private ToggleAlertaBusquedaUseCase toggleAlertaBusquedaUseCase;

    @MockBean private UsuarioRepository usuarioRepository;

    @MockBean private ScrimRepository scrimRepository;

    @MockBean private JugadorFacadeBinder jugadorFacadeBinder;



    @Test

    void authLogin_devuelveUserId() throws Exception {

        UUID userId = UUID.randomUUID();

        when(loginUseCase.execute("user@test.local", "secret")).thenReturn(userId);



        mockMvc.perform(post("/api/auth/login")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"email\":\"user@test.local\",\"password\":\"secret\"}"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.usuarioId").value(userId.toString()));

    }



    @Test

    void getScrim_devuelveScrim() throws Exception {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);

        ScrimResponseDTO dto = ScrimResponseDTO.from(scrim);

        when(getScrimUseCase.execute(scrim.getId())).thenReturn(dto);



        mockMvc.perform(get("/api/scrims/{id}", scrim.getId()))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.juego").value("valorant"));

    }



    @Test

    void listScrims_devuelveLista() throws Exception {

        Jugador org = TestFixtures.jugador("org");

        when(searchScrimsUseCase.execute(any(), any(), any(), any(), any(), any(), any()))

                .thenReturn(List.of(ScrimResponseDTO.from(TestFixtures.scrim(org))));



        mockMvc.perform(get("/api/scrims"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].juego").value("valorant"));

    }



    @Test

    void getUserProfile_devuelvePerfil() throws Exception {

        Jugador jugador = TestFixtures.jugador("j");

        when(getUserProfileUseCase.execute(jugador.getId()))

                .thenReturn(UsuarioProfileDTO.from(jugador));



        mockMvc.perform(get("/api/usuarios/{id}", jugador.getId()))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.username").value("j"));

    }



    @Test

    void crearScrim_requiereJugador() throws Exception {

        Jugador org = TestFixtures.jugador("org");

        Scrim scrim = TestFixtures.scrim(org);

        when(usuarioRepository.findById(org.getId())).thenReturn(Optional.of(org));

        when(jugadorFacadeBinder.vincular(org)).thenReturn(org);

        when(scrimFacade.crearScrim(eq(org), any(CreateScrimDTO.class))).thenReturn(scrim);



        String body = """
                {
                  "organizadorId": "%s",
                  "juego": "valorant",
                  "jugadoresPorLado": 5,
                  "servidor": "LAN",
                  "zona": "AR",
                  "rangoMin": {"tier": "Gold", "numerico": 1500},
                  "rangoMax": {"tier": "Plat", "numerico": 2000},
                  "latenciaMaxMs": 80,
                  "fechaHora": "2026-06-20T18:00:00",
                  "modalidadNombre": "CASUAL"
                }
                """.formatted(org.getId());



        mockMvc.perform(post("/api/scrims")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content(body))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").value(scrim.getId().toString()));

    }



    @Test

    void reportar_sinIds_usaModerateReport() throws Exception {

        when(moderateReportUseCase.execute("spam")).thenReturn(7L);



        mockMvc.perform(post("/api/reportes")

                        .contentType(MediaType.APPLICATION_JSON)

                        .content("{\"motivo\":\"spam\"}"))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.reporteId").value(7));

    }

}



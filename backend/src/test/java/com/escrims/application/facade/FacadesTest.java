package com.escrims.application.facade;

import com.escrims.application.dto.CreateScrimDTO;
import com.escrims.application.dto.EstadisticaDTO;
import com.escrims.application.usecases.ApplyToScrimUseCase;
import com.escrims.application.usecases.ConfirmParticipationUseCase;
import com.escrims.application.usecases.CreateScrimUseCase;
import com.escrims.application.usecases.FinalizeScrimUseCase;
import com.escrims.application.usecases.RunMatchmakingUseCase;
import com.escrims.domain.command.AsignarRolCommand;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.model.scrim.CreateScrimRequest;
import com.escrims.domain.model.scrim.Scrim;
import com.escrims.domain.model.usuario.Jugador;
import com.escrims.domain.repository.ScrimRepository;
import com.escrims.domain.services.NotificationService;
import com.escrims.domain.valueobjects.FormatoScrim;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.support.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacadesTest {

    @Mock private CreateScrimUseCase createScrimUseCase;
    @Mock private ApplyToScrimUseCase applyToScrimUseCase;
    @Mock private ConfirmParticipationUseCase confirmParticipationUseCase;
    @Mock private FinalizeScrimUseCase finalizeScrimUseCase;
    @Mock private RunMatchmakingUseCase runMatchmakingUseCase;
    @Mock private ScrimRepository scrimRepository;
    @Mock private NotificationService notificationService;
    @Mock private com.escrims.domain.services.ModerationService moderationService;
    @Mock private com.escrims.domain.repository.ReporteConductaRepository reporteRepository;

    private Jugador organizador;
    private Scrim scrim;
    private ScrimFacade scrimFacade;
    private LobbyFacade lobbyFacade;
    private ModeracionFacade moderacionFacade;

    @BeforeEach
    void setUp() {
        organizador = TestFixtures.jugador("org");
        scrim = TestFixtures.scrim(organizador);
        scrimFacade = new ScrimFacade(
                createScrimUseCase, applyToScrimUseCase,
                confirmParticipationUseCase, finalizeScrimUseCase, scrimRepository);
        lobbyFacade = new LobbyFacade(scrimRepository, notificationService, runMatchmakingUseCase);
        moderacionFacade = new ModeracionFacade(moderationService, reporteRepository);
    }

    @Test
    void scrimFacade_crearScrim_recargaDesdeRepositorio() {
        UUID scrimId = scrim.getId();
        when(createScrimUseCase.execute(any(CreateScrimDTO.class))).thenReturn(scrimId);
        when(scrimRepository.findById(scrimId)).thenReturn(Optional.of(scrim));

        CreateScrimRequest request = new CreateScrimRequest();
        request.setJuego("valorant");
        request.setFormato(new FormatoScrim(5));
        request.setModalidad("CASUAL");
        request.setRegion(new Region(null, "LAN/AR"));
        request.setRangoMin(new Rango(null, "Gold", 1500));
        request.setRangoMax(new Rango(null, "Plat", 2000));
        request.setLatenciaMaxMs(80);
        request.setFechaHora(LocalDateTime.now().plusHours(1));

        Scrim creado = scrimFacade.crearScrim(organizador, request);

        assertEquals(scrimId, creado.getId());
    }

    @Test
    void scrimFacade_cargarResultados_soloOrganizador() {
        Jugador otro = TestFixtures.jugador("otro");
        assertThrows(IllegalStateException.class,
                () -> scrimFacade.cargarResultados(otro, scrim, List.of()));
    }

    @Test
    void scrimFacade_cargarResultados_delegaAFinalize() {
        EstadisticaDTO stat = new EstadisticaDTO();
        scrimFacade.cargarResultados(organizador, scrim, List.of(stat));
        verify(finalizeScrimUseCase).execute(scrim.getId(), List.of(stat));
    }

    @Test
    void lobbyFacade_armarLobby_ejecutaMatchmaking() {
        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));

        lobbyFacade.armarLobby(scrim.getLobby());

        verify(runMatchmakingUseCase).execute(scrim.getId());
        verify(notificationService).notificar(any());
    }

    @Test
    void lobbyFacade_ejecutarComando_guardaScrim() {
        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));
        Jugador j = TestFixtures.jugador("j");
        scrim.getLobby().getGestorLobby().balancearEquipos(j);

        lobbyFacade.ejecutarComando(
                new AsignarRolCommand(j, new Rol(null, "Duelist")),
                scrim.getLobby());

        verify(scrimRepository).save(scrim);
    }

    @Test
    void moderacionFacade_calificar_validaPuntuacion() {
        Jugador a = TestFixtures.jugador("a");
        Jugador b = TestFixtures.jugador("b");
        assertThrows(IllegalArgumentException.class,
                () -> moderacionFacade.calificarJugador(a, b, scrim, 0, "mal"));
    }

    @Test
    void moderacionFacade_calificar_creaRating() {
        Jugador a = TestFixtures.jugador("a");
        Jugador b = TestFixtures.jugador("b");
        var rating = moderacionFacade.calificarJugador(a, b, scrim, 4, "buen compañero");
        assertEquals(4, rating.getPuntuacion());
    }
}

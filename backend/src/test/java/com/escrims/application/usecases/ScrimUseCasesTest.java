package com.escrims.application.usecases;



import com.escrims.application.dto.CreateScrimDTO;

import com.escrims.application.dto.EstadisticaDTO;

import com.escrims.domain.model.postulacion.EstadoPostulacion;

import com.escrims.domain.model.postulacion.Postulacion;

import com.escrims.domain.model.rol.Rol;

import com.escrims.domain.model.scrim.Scrim;

import com.escrims.domain.model.usuario.Jugador;

import com.escrims.domain.model.usuario.Moderador;

import com.escrims.domain.observer.DomainEventBus;

import com.escrims.domain.repository.EstadisticaRepository;

import com.escrims.domain.repository.PostulacionRepository;

import com.escrims.domain.repository.ScrimRepository;

import com.escrims.domain.repository.UsuarioRepository;

import com.escrims.domain.services.MatchmakingService;

import com.escrims.domain.services.ScrimLifecycleService;

import com.escrims.domain.strategy.ByMMRStrategy;

import com.escrims.domain.valueobjects.CandidatoMatchmaking;

import com.escrims.domain.valueobjects.MatchmakingContext;

import com.escrims.domain.valueobjects.Rango;

import com.escrims.infrastructure.security.PasswordHasher;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;



import java.time.LocalDateTime;

import java.util.List;

import java.util.Optional;

import java.util.UUID;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)

class ScrimUseCasesTest {



    @Mock private ScrimRepository scrimRepository;

    @Mock private UsuarioRepository usuarioRepository;

    @Mock private PostulacionRepository postulacionRepository;

    @Mock private EstadisticaRepository estadisticaRepository;

    @Mock private DomainEventBus eventBus;



    private Jugador organizador;

    private Scrim scrim;



    @BeforeEach

    void setUp() {

        organizador = TestFixtures.jugador("org");

        scrim = TestFixtures.scrim(organizador);

    }



    @Test

    void createScrim_guardaYRetornaId() {

        CreateScrimUseCase useCase = new CreateScrimUseCase(scrimRepository, usuarioRepository, eventBus);

        when(usuarioRepository.findById(organizador.getId())).thenReturn(Optional.of(organizador));

        when(scrimRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));



        CreateScrimDTO dto = new CreateScrimDTO();

        dto.setOrganizadorId(organizador.getId());

        dto.setJuego("valorant");

        dto.setJugadoresPorLado(5);

        dto.setServidor("LAN");

        dto.setZona("AR");

        dto.setRangoMin(new Rango(null, "Gold", 1500));

        dto.setRangoMax(new Rango(null, "Plat", 2000));

        dto.setLatenciaMaxMs(80);

        dto.setFechaHora(LocalDateTime.now().plusHours(2));



        UUID id = useCase.execute(dto);



        assertNotNull(id);

        verify(scrimRepository).save(any(Scrim.class));

    }



    @Test

    void createScrim_rechazaOrganizadorNoJugador() {

        CreateScrimUseCase useCase = new CreateScrimUseCase(scrimRepository, usuarioRepository, eventBus);

        Moderador mod = TestFixtures.moderador("mod");

        when(usuarioRepository.findById(mod.getId())).thenReturn(Optional.of(mod));



        CreateScrimDTO dto = new CreateScrimDTO();

        dto.setOrganizadorId(mod.getId());

        dto.setJuego("valorant");

        dto.setJugadoresPorLado(5);

        dto.setServidor("LAN");

        dto.setRangoMin(new Rango(null, "Gold", 1500));

        dto.setRangoMax(new Rango(null, "Plat", 2000));

        dto.setFechaHora(LocalDateTime.now().plusHours(2));



        assertThrows(IllegalArgumentException.class, () -> useCase.execute(dto));

    }



    @Test

    void applyToScrim_creaPostulacion() {

        ApplyToScrimUseCase useCase = new ApplyToScrimUseCase(

                postulacionRepository, scrimRepository, usuarioRepository, eventBus);

        Jugador postulante = TestFixtures.jugador("post");

        when(usuarioRepository.findById(postulante.getId())).thenReturn(Optional.of(postulante));

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));

        when(postulacionRepository.save(any())).thenAnswer(inv -> {

            Postulacion p = inv.getArgument(0);

            return new Postulacion(42L, p.getJugador(), p.getScrim(), p.getRolDeseado(), EstadoPostulacion.PENDIENTE);

        });



        Long id = useCase.execute(postulante.getId(), scrim.getId(), "Duelist");



        assertEquals(42L, id);

    }



    @Test

    void acceptPostulacion_aceptaYGuarda() {

        AcceptPostulacionUseCase useCase = new AcceptPostulacionUseCase(postulacionRepository, eventBus);

        Jugador postulante = TestFixtures.jugador("post");

        Postulacion postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.PENDIENTE);

        when(postulacionRepository.findById(1L)).thenReturn(Optional.of(postulacion));



        useCase.execute(1L);



        assertEquals(EstadoPostulacion.ACEPTADA, postulacion.getEstado());

        verify(postulacionRepository).save(postulacion);

    }



    @Test

    void getScrim_devuelveDto() {

        GetScrimUseCase useCase = new GetScrimUseCase(scrimRepository);

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));



        var dto = useCase.execute(scrim.getId());



        assertEquals(scrim.getId(), dto.getId());

    }



    @Test

    void cancelScrim_cancelaYLGuarda() {

        ScrimLifecycleService lifecycle = new ScrimLifecycleService(eventBus);

        CancelScrimUseCase useCase = new CancelScrimUseCase(lifecycle, scrimRepository);

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));



        useCase.execute(scrim.getId(), "motivo");



        assertEquals("CANCELADO", scrim.getEstadoNombre());

        verify(scrimRepository).save(scrim);

    }



    @Test

    void finalizeScrim_guardaEstadisticas() {

        ScrimLifecycleService lifecycle = new ScrimLifecycleService(eventBus);

        FinalizeScrimUseCase useCase = new FinalizeScrimUseCase(

                lifecycle, scrimRepository, estadisticaRepository, usuarioRepository);

        Jugador jugador = TestFixtures.jugador("j1");

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));

        when(usuarioRepository.findById(jugador.getId())).thenReturn(Optional.of(jugador));

        scrim.avanzarEstado();

        scrim.avanzarEstado();

        scrim.avanzarEstado();



        EstadisticaDTO stat = new EstadisticaDTO();

        stat.setUsuarioId(jugador.getId());

        stat.setKills(10);

        stat.setDeaths(5);

        stat.setAssists(3);



        useCase.execute(scrim.getId(), List.of(stat));



        assertEquals("FINALIZADO", scrim.getEstadoNombre());

        verify(estadisticaRepository).save(any());

    }



    @Test

    void runMatchmaking_balanceaEquiposYAvanzaEstado() {

        MatchmakingService matchmakingService = new MatchmakingService(new ByMMRStrategy(500));

        RunMatchmakingUseCase useCase = new RunMatchmakingUseCase(

                matchmakingService, scrimRepository, postulacionRepository, eventBus);

        Jugador j1 = TestFixtures.jugador("j1");

        Jugador j2 = TestFixtures.jugador("j2");

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));

        when(scrimRepository.buildMatchmakingContext(scrim)).thenReturn(

                new MatchmakingContext(scrim, 2, List.of(

                        new CandidatoMatchmaking(j1, 1600, 40, "Duelist", 0, 1.5),

                        new CandidatoMatchmaking(j2, 1700, 50, "Initiator", 0, 1.5))));

        when(postulacionRepository.findByScrimId(scrim.getId())).thenReturn(List.of());



        useCase.execute(scrim.getId());



        assertEquals("LOBBY_ARMADO", scrim.getEstadoNombre());

        assertEquals(2, scrim.getParticipantesLobby().size());

        verify(scrimRepository).save(scrim);

    }



    @Test

    void searchScrims_delegaAlRepositorio() {

        SearchScrimsUseCase useCase = new SearchScrimsUseCase(scrimRepository);

        when(scrimRepository.findByCriteria(any())).thenReturn(List.of(scrim));



        var result = useCase.execute("valorant", 5, "LAN", "AR", null, null, 80);



        assertEquals(1, result.size());

        assertEquals(scrim.getId(), result.get(0).getId());

    }



    @Test

    void listPostulaciones_devuelveDtos() {

        ListPostulacionesByScrimUseCase useCase = new ListPostulacionesByScrimUseCase(

                postulacionRepository, scrimRepository);

        Jugador postulante = TestFixtures.jugador("post");

        Postulacion postulacion = new Postulacion(1L, postulante, scrim, new Rol(null, "Duelist"), EstadoPostulacion.PENDIENTE);

        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));

        when(postulacionRepository.findByScrimId(scrim.getId())).thenReturn(List.of(postulacion));



        var result = useCase.execute(scrim.getId());



        assertEquals(1, result.size());

        assertEquals(1L, result.get(0).getId());

    }



    @Test
    void confirmParticipation_avanzaAConfirmadoCuandoTodosConfirman() {
        ConfirmParticipationUseCase useCase = new ConfirmParticipationUseCase(
                usuarioRepository, scrimRepository, eventBus);
        Jugador j1 = TestFixtures.jugador("j1");
        scrim.avanzarEstado();
        scrim.getLobby().getGestorLobby().balancearEquipos(j1);
        scrim.getLobby().inicializarConfirmaciones(scrim);
        when(usuarioRepository.findById(j1.getId())).thenReturn(Optional.of(j1));
        when(scrimRepository.findById(scrim.getId())).thenReturn(Optional.of(scrim));
        when(scrimRepository.save(scrim)).thenReturn(scrim);

        useCase.execute(j1.getId(), scrim.getId());

        assertEquals("CONFIRMADO", scrim.getEstadoNombre());
        verify(scrimRepository).save(scrim);
    }
}



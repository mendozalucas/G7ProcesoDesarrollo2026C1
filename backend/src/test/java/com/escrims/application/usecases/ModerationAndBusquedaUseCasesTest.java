package com.escrims.application.usecases;



import com.escrims.application.dto.SaveBusquedaFavoritaCommand;

import com.escrims.domain.model.busqueda.BusquedaFavorita;

import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;

import com.escrims.domain.model.juego.JuegoFactory;

import com.escrims.domain.model.reporte.ReporteConducta;

import com.escrims.domain.moderation.AutoModerationHandler;

import com.escrims.domain.repository.BusquedaFavoritaRepository;

import com.escrims.domain.repository.ReporteConductaRepository;

import com.escrims.domain.services.ModerationService;

import com.escrims.domain.valueobjects.Region;

import com.escrims.support.TestFixtures;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;



import java.util.List;

import java.util.Optional;

import java.util.UUID;



import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)

class ModerationAndBusquedaUseCasesTest {



    @Mock private ReporteConductaRepository reporteRepository;

    @Mock private BusquedaFavoritaRepository busquedaRepository;

    @Mock private com.escrims.domain.repository.UsuarioRepository usuarioRepository;



    @Test

    void moderateReport_procesaYGuarda() {

        ModerationService moderationService = new ModerationService(

                new AutoModerationHandler(), usuarioRepository);

        ModerateReportUseCase useCase = new ModerateReportUseCase(moderationService, reporteRepository);

        when(reporteRepository.save(any())).thenAnswer(inv -> {

            ReporteConducta r = inv.getArgument(0);

            return new ReporteConducta(99L, r.getMotivo());

        });



        Long id = useCase.execute("usuario hace spam");



        assertEquals(99L, id);

        verify(reporteRepository, times(2)).save(any());

    }



    @Test

    void saveBusquedaFavorita_persisteCriterios() {

        SaveBusquedaFavoritaUseCase useCase = new SaveBusquedaFavoritaUseCase(busquedaRepository);

        UUID usuarioId = UUID.randomUUID();

        SaveBusquedaFavoritaCommand cmd = new SaveBusquedaFavoritaCommand();

        cmd.setJuego("valorant");

        cmd.setServidor("LAN");

        cmd.setZona("AR");

        cmd.setActivarAlerta(true);

        when(busquedaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));



        var dto = useCase.execute(usuarioId, cmd);



        assertEquals("valorant", dto.getJuego());

        verify(busquedaRepository).save(any());

    }



    @Test

    void listBusquedasFavoritas_devuelvePorUsuario() {

        ListBusquedasFavoritasUseCase useCase = new ListBusquedasFavoritasUseCase(busquedaRepository);

        UUID usuarioId = UUID.randomUUID();

        BusquedaFavorita criterios = new BusquedaFavorita(

                JuegoFactory.para("valorant"), null, null, new Region(null, "LAN"), null, null);

        BusquedaFavoritaAlmacenada almacenada = new BusquedaFavoritaAlmacenada(

                UUID.randomUUID(), usuarioId, true, criterios);

        when(busquedaRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(almacenada));



        var result = useCase.execute(usuarioId);



        assertEquals(1, result.size());

    }



    @Test

    void toggleAlerta_activaYDesactiva() {

        ToggleAlertaBusquedaUseCase useCase = new ToggleAlertaBusquedaUseCase(busquedaRepository);

        UUID busquedaId = UUID.randomUUID();

        UUID usuarioId = UUID.randomUUID();

        BusquedaFavorita criterios = new BusquedaFavorita(

                JuegoFactory.para("valorant"), null, null, new Region(null, "LAN"), null, null);

        BusquedaFavoritaAlmacenada original = new BusquedaFavoritaAlmacenada(

                busquedaId, usuarioId, false, criterios);

        when(busquedaRepository.findById(busquedaId)).thenReturn(Optional.of(original));

        when(busquedaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));



        var activada = useCase.activar(busquedaId);

        assertTrue(activada.isAlertaActiva());



        var desactivada = useCase.desactivar(busquedaId);

        assertFalse(desactivada.isAlertaActiva());

    }

}



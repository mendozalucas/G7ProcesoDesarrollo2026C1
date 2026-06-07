package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.application.dto.SaveBusquedaFavoritaCommand;
import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.services.SearchAlertService;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveBusquedaFavoritaUseCase {

    private final SearchAlertService searchAlertService;
    private final UsuarioRepository usuarioRepository;

    public SaveBusquedaFavoritaUseCase(SearchAlertService searchAlertService,
                                        UsuarioRepository usuarioRepository) {
        this.searchAlertService = searchAlertService;
        this.usuarioRepository = usuarioRepository;
    }

    public BusquedaFavoritaDTO execute(UUID usuarioId, SaveBusquedaFavoritaCommand command) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        Rango rangoMin = command.getRangoMin() != null
                ? new Rango(command.getJuego(), "Min", command.getRangoMin()) : null;
        Rango rangoMax = command.getRangoMax() != null
                ? new Rango(command.getJuego(), "Max", command.getRangoMax()) : null;
        Region region = (command.getServidor() != null || command.getZona() != null)
                ? new Region(command.getServidor(), command.getZona()) : null;
        RolJuego rol = command.getRolBuscado() != null
                ? new RolJuego(command.getJuego(), command.getRolBuscado()) : null;

        BusquedaFavorita busqueda = new BusquedaFavorita(
                usuarioId,
                JuegoFactory.para(command.getJuego()),
                rangoMin,
                rangoMax,
                region,
                null,
                rol);

        if (command.isActivarAlerta()) {
            busqueda.activarAlerta();
        }

        BusquedaFavorita guardada = searchAlertService.guardarBusqueda(busqueda);
        usuario.agregarBusquedaFavorita(guardada);
        usuarioRepository.save(usuario);
        return BusquedaFavoritaDTO.from(guardada);
    }
}

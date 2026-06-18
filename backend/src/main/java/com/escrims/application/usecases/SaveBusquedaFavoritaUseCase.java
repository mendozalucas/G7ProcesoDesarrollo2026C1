package com.escrims.application.usecases;

import com.escrims.application.dto.BusquedaFavoritaDTO;
import com.escrims.application.dto.SaveBusquedaFavoritaCommand;
import com.escrims.domain.model.busqueda.BusquedaFavorita;
import com.escrims.domain.model.busqueda.BusquedaFavoritaAlmacenada;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.rol.Rol;
import com.escrims.domain.repository.BusquedaFavoritaRepository;
import com.escrims.domain.valueobjects.Rango;
import com.escrims.domain.valueobjects.Region;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaveBusquedaFavoritaUseCase {

    private final BusquedaFavoritaRepository busquedaRepository;

    public SaveBusquedaFavoritaUseCase(BusquedaFavoritaRepository busquedaRepository) {
        this.busquedaRepository = busquedaRepository;
    }

    public BusquedaFavoritaDTO execute(UUID usuarioId, SaveBusquedaFavoritaCommand command) {
        Rango rangoMin = command.getRangoMin() != null
                ? new Rango(null, "Min", command.getRangoMin()) : null;
        Rango rangoMax = command.getRangoMax() != null
                ? new Rango(null, "Max", command.getRangoMax()) : null;
        String regionNombre = command.getServidor();
        if (command.getZona() != null && !command.getZona().isBlank()) {
            regionNombre = command.getServidor() + "/" + command.getZona();
        }
        Region region = command.getServidor() != null ? new Region(null, regionNombre) : null;
        Rol rol = command.getRolBuscado() != null ? new Rol(null, command.getRolBuscado()) : null;

        BusquedaFavorita criterios = new BusquedaFavorita(
                JuegoFactory.para(command.getJuego()),
                rangoMin,
                rangoMax,
                region,
                null,
                rol);

        BusquedaFavoritaAlmacenada almacenada = new BusquedaFavoritaAlmacenada(
                UUID.randomUUID(),
                usuarioId,
                command.isActivarAlerta(),
                criterios);

        return BusquedaFavoritaDTO.from(busquedaRepository.save(almacenada));
    }
}

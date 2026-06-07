package com.escrims.presentation.api.mapper;

import com.escrims.application.dto.SaveBusquedaFavoritaCommand;
import com.escrims.application.dto.UpdateProfileCommand;
import com.escrims.presentation.api.dto.BusquedaFavoritaRequest;
import com.escrims.presentation.api.dto.UpdateProfileRequest;

import java.util.stream.Collectors;

public final class ProfileRequestMapper {

    private ProfileRequestMapper() {}

    public static UpdateProfileCommand toCommand(UpdateProfileRequest request) {
        UpdateProfileCommand command = new UpdateProfileCommand();
        command.setUsername(request.getUsername());

        if (request.getPerfilesJuego() != null) {
            command.setPerfilesJuego(request.getPerfilesJuego().stream().map(p -> {
                UpdateProfileCommand.PerfilJuegoCommand c = new UpdateProfileCommand.PerfilJuegoCommand();
                c.setJuego(p.getJuego());
                c.setServidor(p.getServidor());
                c.setZona(p.getZona());
                c.setMmr(p.getMmr());
                c.setRolesPreferidos(p.getRolesPreferidos());
                return c;
            }).collect(Collectors.toList()));
        }

        if (request.getDisponibilidad() != null) {
            command.setDisponibilidad(request.getDisponibilidad().stream().map(d -> {
                UpdateProfileCommand.DisponibilidadCommand c = new UpdateProfileCommand.DisponibilidadCommand();
                c.setDia(d.getDia());
                c.setInicio(d.getInicio());
                c.setFin(d.getFin());
                return c;
            }).collect(Collectors.toList()));
        }

        if (request.getPreferencias() != null) {
            UpdateProfileCommand.PreferenciasCommand pref = new UpdateProfileCommand.PreferenciasCommand();
            pref.setJuegosPreferidos(request.getPreferencias().getJuegosPreferidos());
            pref.setRecibirAlertas(request.getPreferencias().isRecibirAlertas());
            command.setPreferencias(pref);
        }

        return command;
    }

    public static SaveBusquedaFavoritaCommand toCommand(BusquedaFavoritaRequest request) {
        SaveBusquedaFavoritaCommand command = new SaveBusquedaFavoritaCommand();
        command.setJuego(request.getJuego());
        command.setRangoMin(request.getRangoMin());
        command.setRangoMax(request.getRangoMax());
        command.setServidor(request.getServidor());
        command.setZona(request.getZona());
        command.setRolBuscado(request.getRolBuscado());
        command.setActivarAlerta(request.isActivarAlerta());
        return command;
    }
}

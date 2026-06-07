package com.escrims.application.usecases;

import com.escrims.application.dto.UpdateProfileCommand;
import com.escrims.application.dto.UsuarioProfileDTO;
import com.escrims.domain.model.juego.JuegoFactory;
import com.escrims.domain.model.usuario.PerfilJuego;
import com.escrims.domain.model.usuario.Usuario;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.domain.valueobjects.DisponibilidadHoraria;
import com.escrims.domain.valueobjects.PreferenciasUsuario;
import com.escrims.domain.valueobjects.Region;
import com.escrims.domain.valueobjects.RolJuego;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UpdateUserProfileUseCase {

    private final UsuarioRepository usuarioRepository;

    public UpdateUserProfileUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioProfileDTO execute(UUID usuarioId, UpdateProfileCommand command) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + usuarioId));

        if (command.getUsername() != null) {
            usuarioRepository.findByUsername(command.getUsername())
                    .filter(u -> !u.getId().equals(usuarioId))
                    .ifPresent(u -> {
                        throw new IllegalArgumentException("El username ya está en uso");
                    });
            usuario.actualizarUsername(command.getUsername());
        }

        if (command.getPerfilesJuego() != null) {
            for (UpdateProfileCommand.PerfilJuegoCommand perfilReq : command.getPerfilesJuego()) {
                Region region = new Region(perfilReq.getServidor(), perfilReq.getZona());
                PerfilJuego perfil = new PerfilJuego(
                        JuegoFactory.para(perfilReq.getJuego()),
                        region,
                        perfilReq.getMmr());
                if (perfilReq.getRolesPreferidos() != null) {
                    for (String rol : perfilReq.getRolesPreferidos()) {
                        perfil.agregarRolPreferido(new RolJuego(perfilReq.getJuego(), rol));
                    }
                }
                usuario.agregarPerfilJuego(perfil);
            }
        }

        if (command.getDisponibilidad() != null) {
            List<DisponibilidadHoraria> horarios = new ArrayList<>();
            for (UpdateProfileCommand.DisponibilidadCommand d : command.getDisponibilidad()) {
                horarios.add(new DisponibilidadHoraria(
                        DayOfWeek.valueOf(d.getDia().toUpperCase()),
                        LocalTime.parse(d.getInicio()),
                        LocalTime.parse(d.getFin())));
            }
            usuario.reemplazarDisponibilidad(horarios);
        }

        if (command.getPreferencias() != null) {
            UpdateProfileCommand.PreferenciasCommand pref = command.getPreferencias();
            usuario.actualizarPreferencias(new PreferenciasUsuario(
                    pref.getJuegosPreferidos() != null ? pref.getJuegosPreferidos() : List.of(),
                    pref.isRecibirAlertas()));
        }

        usuarioRepository.save(usuario);
        return UsuarioProfileDTO.from(usuario);
    }
}

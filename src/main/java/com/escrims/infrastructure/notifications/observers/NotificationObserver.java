package com.escrims.infrastructure.notifications.observers;

import com.escrims.domain.events.*;
import com.escrims.domain.observer.IObserver;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.notifications.factory.NotifierFactory;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

public class NotificationObserver implements IObserver {

    private final NotifierFactory notifierFactory;
    private final UsuarioRepository usuarioRepository;

    public NotificationObserver(NotifierFactory notifierFactory, UsuarioRepository usuarioRepository) {
        this.notifierFactory = notifierFactory;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onEvent(DomainEvent event) {
        if (event instanceof LobbyArmadoEvent) {
            handleLobbyArmado((LobbyArmadoEvent) event);
        } else if (event instanceof ConfirmadoEvent) {
            handleConfirmado((ConfirmadoEvent) event);
        } else if (event instanceof EnJuegoEvent) {
            handleEnJuego((EnJuegoEvent) event);
        } else if (event instanceof FinalizadoEvent) {
            handleFinalizado((FinalizadoEvent) event);
        } else if (event instanceof CanceladoEvent) {
            handleCancelado((CanceladoEvent) event);
        } else if (event instanceof PostulacionAceptadaEvent) {
            handlePostulacionAceptada((PostulacionAceptadaEvent) event);
        } else if (event instanceof NuevoScrimDisponibleEvent) {
            handleNuevoScrimDisponible((NuevoScrimDisponibleEvent) event);
        }
    }

    private void handleLobbyArmado(LobbyArmadoEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviarPush(uid, "Lobby armado", "Tu scrim esta completo. Confirma tu asistencia!"));
    }

    private void handleConfirmado(ConfirmadoEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviarPush(uid, "Scrim confirmado", "Todos confirmaron. Nos vemos en la partida!"));
    }

    private void handleEnJuego(EnJuegoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s inicio%n", e.getScrimId());
    }

    private void handleFinalizado(FinalizadoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s finalizado%n", e.getScrimId());
    }

    private void handleCancelado(CanceladoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s cancelado: %s%n", e.getScrimId(), e.getMotivo());
    }

    private void handlePostulacionAceptada(PostulacionAceptadaEvent e) {
        notifierFactory.crearPushNotifier()
                .enviarPush(e.getUsuarioId(), "Postulacion aceptada!", "Fuiste aceptado en el scrim.");
    }

    private void handleNuevoScrimDisponible(NuevoScrimDisponibleEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getUsuariosANotificar().forEach(uid ->
                push.enviarPush(uid, "Nuevo scrim disponible", "Hay un scrim que coincide con tu busqueda."));
    }
}

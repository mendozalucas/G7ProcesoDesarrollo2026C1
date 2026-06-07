package com.escrims.infrastructure.notifications.observers;

import com.escrims.domain.events.*;
import com.escrims.domain.observer.IObserver;
import com.escrims.domain.repository.UsuarioRepository;
import com.escrims.infrastructure.notifications.factory.NotifierFactory;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

public class NotificationObserver implements IObserver, DomainEventVisitor {

    private final NotifierFactory notifierFactory;
    private final UsuarioRepository usuarioRepository;

    public NotificationObserver(NotifierFactory notifierFactory, UsuarioRepository usuarioRepository) {
        this.notifierFactory = notifierFactory;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onEvent(DomainEvent event) {
        event.aceptar(this);
    }

    @Override
    public void visit(LobbyArmadoEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviarPush(uid, "Lobby armado", "Tu scrim esta completo. Confirma tu asistencia!"));
    }

    @Override
    public void visit(ConfirmadoEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviarPush(uid, "Scrim confirmado", "Todos confirmaron. Nos vemos en la partida!"));
    }

    @Override
    public void visit(EnJuegoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s inicio%n", e.getScrimId());
    }

    @Override
    public void visit(FinalizadoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s finalizado%n", e.getScrimId());
    }

    @Override
    public void visit(CanceladoEvent e) {
        System.out.printf("[NotificationObserver] Scrim %s cancelado: %s%n", e.getScrimId(), e.getMotivo());
    }

    @Override
    public void visit(PostulacionAceptadaEvent e) {
        notifierFactory.crearPushNotifier()
                .enviarPush(e.getUsuarioId(), "Postulacion aceptada!", "Fuiste aceptado en el scrim.");
    }

    @Override
    public void visit(NuevoScrimDisponibleEvent e) {
        PushNotifier push = notifierFactory.crearPushNotifier();
        e.getUsuariosANotificar().forEach(uid ->
                push.enviarPush(uid, "Nuevo scrim disponible", "Hay un scrim que coincide con tu busqueda."));
    }
}

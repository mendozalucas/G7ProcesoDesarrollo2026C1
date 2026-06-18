package com.escrims.infrastructure.notifications.observers;

import com.escrims.domain.events.*;
import com.escrims.domain.observer.IObserver;
import com.escrims.infrastructure.notifications.factory.INotifierFactory;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

public class NotificationObserver implements IObserver, DomainEventVisitor {

    private final INotifierFactory factory;

    public NotificationObserver(INotifierFactory factory) {
        this.factory = factory;
    }

    @Override
    public void onEvent(DomainEvent event) {
        event.aceptar(this);
    }

    @Override
    public void visit(LobbyArmadoEvent e) {
        Notifier push = factory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviar("Tu scrim esta completo. Confirma tu asistencia!", uid.toString()));
    }

    @Override
    public void visit(ConfirmadoEvent e) {
        Notifier push = factory.crearPushNotifier();
        e.getParticipantesIds().forEach(uid ->
                push.enviar("Todos confirmaron. Nos vemos en la partida!", uid.toString()));
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
        factory.crearPushNotifier()
                .enviar("Fuiste aceptado en el scrim.", e.getUsuarioId().toString());
    }

    @Override
    public void visit(NuevoScrimDisponibleEvent e) {
        Notifier push = factory.crearPushNotifier();
        e.getUsuariosANotificar().forEach(uid ->
                push.enviar("Hay un scrim que coincide con tu busqueda.", uid.toString()));
    }
}

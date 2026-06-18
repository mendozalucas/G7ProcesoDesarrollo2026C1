package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.FirebaseMessagingClient;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: Notifier
 * Adaptee: FirebaseMessagingClient
 */
public class FirebaseAdapter implements Notifier {

    private final FirebaseMessagingClient client;

    public FirebaseAdapter(FirebaseMessagingClient client) {
        this.client = client;
    }

    @Override
    public void enviar(String mensaje, String destino) {
        client.send(destino, "Notificación", mensaje);
    }
}

package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.FirebaseMessagingClient;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

import java.util.UUID;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: PushNotifier
 * Adaptee: FirebaseMessagingClient
 */
public class FirebaseAdapter implements PushNotifier {

    private final FirebaseMessagingClient client;

    public FirebaseAdapter(FirebaseMessagingClient client) {
        this.client = client;
    }

    @Override
    public void enviarPush(UUID destinatarioId, String titulo, String cuerpo) {
        String token = client.getTokenForUser(destinatarioId);
        client.send(token, titulo, cuerpo);
    }
}

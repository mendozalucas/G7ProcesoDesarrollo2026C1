package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.SendGridClient;
import com.escrims.infrastructure.notifications.notifiers.INotificadorEmail;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: Notifier
 * Adaptee: SendGridClient
 */
public class SendGridAdapter implements Notifier, INotificadorEmail {

    private final SendGridClient client;

    public SendGridAdapter(SendGridClient client) {
        this.client = client;
    }

    @Override
    public void enviar(String mensaje, String destino) {
        client.sendMessage(destino, "Notificación", mensaje);
    }
}

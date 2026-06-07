package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.SendGridClient;
import com.escrims.infrastructure.notifications.notifiers.EmailNotifier;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: EmailNotifier
 * Adaptee: SendGridClient
 */
public class SendGridAdapter implements EmailNotifier {

    private final SendGridClient client;

    public SendGridAdapter(SendGridClient client) {
        this.client = client;
    }

    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        client.sendMessage(destinatario, asunto, cuerpo);
    }
}

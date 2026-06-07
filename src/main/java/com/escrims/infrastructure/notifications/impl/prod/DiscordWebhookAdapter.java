package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.DiscordWebhookClient;
import com.escrims.infrastructure.notifications.notifiers.DiscordNotifier;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: DiscordNotifier
 * Adaptee: DiscordWebhookClient
 */
public class DiscordWebhookAdapter implements DiscordNotifier {

    private final DiscordWebhookClient client;

    public DiscordWebhookAdapter(DiscordWebhookClient client) {
        this.client = client;
    }

    @Override
    public void enviarMensaje(String webhookUrl, String mensaje) {
        client.postWebhook(webhookUrl, mensaje);
    }
}

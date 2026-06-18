package com.escrims.infrastructure.notifications.impl.prod;

import com.escrims.infrastructure.adapters.DiscordWebhookClient;
import com.escrims.infrastructure.notifications.notifiers.INotificadorDiscord;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

/**
 * Adapter (GOF) + Concrete Product (Abstract Factory).
 * Target: Notifier
 * Adaptee: DiscordWebhookClient
 */
public class DiscordWebhookAdapter implements Notifier, INotificadorDiscord {

    private final DiscordWebhookClient client;

    public DiscordWebhookAdapter(DiscordWebhookClient client) {
        this.client = client;
    }

    @Override
    public void enviar(String mensaje, String destino) {
        client.postWebhook(destino, mensaje);
    }
}

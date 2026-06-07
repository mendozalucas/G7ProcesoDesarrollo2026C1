package com.escrims.infrastructure.notifications.impl.dev;

import com.escrims.infrastructure.notifications.notifiers.DiscordNotifier;

public class LogDiscordNotifier implements DiscordNotifier {

    @Override
    public void enviarMensaje(String webhookUrl, String mensaje) {
        System.out.printf("[DEV-DISCORD] webhook=%s | %s%n", webhookUrl, mensaje);
    }
}

package com.escrims.infrastructure.notifications.notifiers;

public interface DiscordNotifier {

    void enviarMensaje(String webhookUrl, String mensaje);
}

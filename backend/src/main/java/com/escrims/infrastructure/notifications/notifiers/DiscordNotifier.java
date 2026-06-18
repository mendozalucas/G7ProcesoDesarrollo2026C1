package com.escrims.infrastructure.notifications.notifiers;

public class DiscordNotifier implements Notifier {

    private INotificadorDiscord adapter;

    public DiscordNotifier(INotificadorDiscord adapter) {
        this.adapter = adapter;
    }

    @Override
    public void enviar(String mensaje, String destino) {
        adapter.enviar(mensaje, destino);
    }
}

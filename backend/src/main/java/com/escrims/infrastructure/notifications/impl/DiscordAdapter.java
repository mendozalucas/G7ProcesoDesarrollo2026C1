package com.escrims.infrastructure.notifications.impl;

import com.escrims.infrastructure.notifications.notifiers.INotificadorDiscord;

public class DiscordAdapter implements INotificadorDiscord {

    private Webhook adaptee;

    public DiscordAdapter(Webhook adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void enviar(String mensaje, String destino) {}
}

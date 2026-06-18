package com.escrims.infrastructure.notifications.impl.dev;

import com.escrims.infrastructure.notifications.notifiers.Notifier;

public class LogDiscordNotifier implements Notifier {

    @Override
    public void enviar(String mensaje, String destino) {
        System.out.printf("[DEV-DISCORD] webhook=%s | %s%n", destino, mensaje);
    }
}

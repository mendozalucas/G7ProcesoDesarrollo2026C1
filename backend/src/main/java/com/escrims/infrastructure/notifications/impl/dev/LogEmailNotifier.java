package com.escrims.infrastructure.notifications.impl.dev;

import com.escrims.infrastructure.notifications.notifiers.Notifier;

public class LogEmailNotifier implements Notifier {

    @Override
    public void enviar(String mensaje, String destino) {
        System.out.printf("[DEV-EMAIL] to=%s | %s%n", destino, mensaje);
    }
}

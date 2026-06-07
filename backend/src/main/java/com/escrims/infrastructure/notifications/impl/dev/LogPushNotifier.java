package com.escrims.infrastructure.notifications.impl.dev;

import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

import java.util.UUID;

public class LogPushNotifier implements PushNotifier {

    @Override
    public void enviarPush(UUID destinatarioId, String titulo, String cuerpo) {
        System.out.printf("[DEV-PUSH] to=%s | %s: %s%n", destinatarioId, titulo, cuerpo);
    }
}

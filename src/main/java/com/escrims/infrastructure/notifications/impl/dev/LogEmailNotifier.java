package com.escrims.infrastructure.notifications.impl.dev;

import com.escrims.infrastructure.notifications.notifiers.EmailNotifier;

public class LogEmailNotifier implements EmailNotifier {

    @Override
    public void enviarEmail(String destinatario, String asunto, String cuerpo) {
        System.out.printf("[DEV-EMAIL] to=%s | %s: %s%n", destinatario, asunto, cuerpo);
    }
}

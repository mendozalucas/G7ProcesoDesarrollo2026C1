package com.escrims.infrastructure.notifications.notifiers;

public interface EmailNotifier {

    void enviarEmail(String destinatario, String asunto, String cuerpo);
}

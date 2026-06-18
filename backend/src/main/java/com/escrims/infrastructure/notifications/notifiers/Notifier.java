package com.escrims.infrastructure.notifications.notifiers;

public interface Notifier {

    void enviar(String mensaje, String destino);
}

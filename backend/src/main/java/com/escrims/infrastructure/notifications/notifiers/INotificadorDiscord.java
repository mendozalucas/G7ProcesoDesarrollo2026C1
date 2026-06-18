package com.escrims.infrastructure.notifications.notifiers;

public interface INotificadorDiscord {

    void enviar(String mensaje, String destino);
}

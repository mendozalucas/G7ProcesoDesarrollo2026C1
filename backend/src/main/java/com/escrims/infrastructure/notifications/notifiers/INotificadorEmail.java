package com.escrims.infrastructure.notifications.notifiers;

public interface INotificadorEmail {

    void enviar(String mensaje, String destino);
}

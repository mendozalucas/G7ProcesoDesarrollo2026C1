package com.escrims.infrastructure.notifications.notifiers;

public class EmailNotifier implements Notifier {

    private INotificadorEmail adapter;

    public EmailNotifier(INotificadorEmail adapter) {
        this.adapter = adapter;
    }

    @Override
    public void enviar(String mensaje, String destino) {
        adapter.enviar(mensaje, destino);
    }
}

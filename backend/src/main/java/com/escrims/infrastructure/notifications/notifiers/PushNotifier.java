package com.escrims.infrastructure.notifications.notifiers;

public class PushNotifier implements Notifier {

    private String firebaseToken;

    public PushNotifier(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    @Override
    public void enviar(String mensaje, String destino) {}
}

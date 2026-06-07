package com.escrims.infrastructure.notifications.notifiers;

import java.util.UUID;

public interface PushNotifier {

    void enviarPush(UUID destinatarioId, String titulo, String cuerpo);
}

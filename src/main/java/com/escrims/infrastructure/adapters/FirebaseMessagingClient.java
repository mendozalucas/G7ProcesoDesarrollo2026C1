package com.escrims.infrastructure.adapters;

import java.util.UUID;

/**
 * Adaptee: representa el SDK de Firebase Cloud Messaging.
 */
public class FirebaseMessagingClient {

    public void send(String deviceToken, String title, String body) {
        System.out.printf("[Firebase] token=%s | %s: %s%n", deviceToken, title, body);
    }

    public String getTokenForUser(UUID userId) {
        return "fcm-token-" + userId;
    }
}

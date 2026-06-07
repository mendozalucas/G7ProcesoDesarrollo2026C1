package com.escrims.infrastructure.adapters;

/**
 * Adaptee: representa la librería externa SendGrid.
 * Su interfaz es incompatible con EmailNotifier del sistema.
 */
public class SendGridClient {

    private final String apiKey;

    public SendGridClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public void sendMessage(String to, String subject, String body) {
        System.out.printf("[SendGrid] apiKey=%s | to=%s | subject=%s%n", apiKey, to, subject);
    }
}

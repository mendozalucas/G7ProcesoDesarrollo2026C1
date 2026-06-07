package com.escrims.infrastructure.adapters;

/**
 * Adaptee: cliente HTTP de Discord Webhook.
 */
public class DiscordWebhookClient {

    public void postWebhook(String url, String content) {
        System.out.printf("[DiscordWebhook] url=%s | content=%s%n", url, content);
    }
}

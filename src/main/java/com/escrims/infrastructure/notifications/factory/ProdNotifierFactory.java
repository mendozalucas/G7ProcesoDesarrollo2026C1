package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.adapters.DiscordWebhookClient;
import com.escrims.infrastructure.adapters.FirebaseMessagingClient;
import com.escrims.infrastructure.adapters.SendGridClient;
import com.escrims.infrastructure.notifications.impl.prod.DiscordWebhookAdapter;
import com.escrims.infrastructure.notifications.impl.prod.FirebaseAdapter;
import com.escrims.infrastructure.notifications.impl.prod.SendGridAdapter;
import com.escrims.infrastructure.notifications.notifiers.DiscordNotifier;
import com.escrims.infrastructure.notifications.notifiers.EmailNotifier;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

public class ProdNotifierFactory implements NotifierFactory {

    private final FirebaseMessagingClient firebaseClient;
    private final SendGridClient sendGridClient;
    private final DiscordWebhookClient discordClient;

    public ProdNotifierFactory(FirebaseMessagingClient firebaseClient,
                                SendGridClient sendGridClient,
                                DiscordWebhookClient discordClient) {
        this.firebaseClient = firebaseClient;
        this.sendGridClient = sendGridClient;
        this.discordClient = discordClient;
    }

    @Override
    public PushNotifier crearPushNotifier() {
        return new FirebaseAdapter(firebaseClient);
    }

    @Override
    public EmailNotifier crearEmailNotifier() {
        return new SendGridAdapter(sendGridClient);
    }

    @Override
    public DiscordNotifier crearDiscordNotifier() {
        return new DiscordWebhookAdapter(discordClient);
    }
}

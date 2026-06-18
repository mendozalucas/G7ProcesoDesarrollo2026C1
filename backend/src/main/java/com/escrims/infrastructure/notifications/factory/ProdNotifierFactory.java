package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.adapters.DiscordWebhookClient;
import com.escrims.infrastructure.adapters.FirebaseMessagingClient;
import com.escrims.infrastructure.adapters.SendGridClient;
import com.escrims.infrastructure.notifications.impl.prod.DiscordWebhookAdapter;
import com.escrims.infrastructure.notifications.impl.prod.FirebaseAdapter;
import com.escrims.infrastructure.notifications.impl.prod.SendGridAdapter;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

public class ProdNotifierFactory implements INotifierFactory {

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
    public Notifier crearPushNotifier() {
        return new FirebaseAdapter(firebaseClient);
    }

    @Override
    public Notifier crearEmailNotifier() {
        return new SendGridAdapter(sendGridClient);
    }

    @Override
    public Notifier crearDiscordNotifier() {
        return new DiscordWebhookAdapter(discordClient);
    }
}

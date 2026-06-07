package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.notifications.impl.dev.LogDiscordNotifier;
import com.escrims.infrastructure.notifications.impl.dev.LogEmailNotifier;
import com.escrims.infrastructure.notifications.impl.dev.LogPushNotifier;
import com.escrims.infrastructure.notifications.notifiers.DiscordNotifier;
import com.escrims.infrastructure.notifications.notifiers.EmailNotifier;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

public class DevNotifierFactory implements NotifierFactory {

    @Override
    public PushNotifier crearPushNotifier() {
        return new LogPushNotifier();
    }

    @Override
    public EmailNotifier crearEmailNotifier() {
        return new LogEmailNotifier();
    }

    @Override
    public DiscordNotifier crearDiscordNotifier() {
        return new LogDiscordNotifier();
    }
}

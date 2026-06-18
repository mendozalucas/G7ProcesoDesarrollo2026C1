package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.notifications.impl.dev.LogDiscordNotifier;
import com.escrims.infrastructure.notifications.impl.dev.LogEmailNotifier;
import com.escrims.infrastructure.notifications.impl.dev.LogPushNotifier;
import com.escrims.infrastructure.notifications.notifiers.Notifier;

public class DevNotifierFactory implements INotifierFactory {

    @Override
    public Notifier crearPushNotifier() {
        return new LogPushNotifier();
    }

    @Override
    public Notifier crearEmailNotifier() {
        return new LogEmailNotifier();
    }

    @Override
    public Notifier crearDiscordNotifier() {
        return new LogDiscordNotifier();
    }
}

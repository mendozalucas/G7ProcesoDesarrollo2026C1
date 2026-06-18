package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.notifications.notifiers.Notifier;

public interface INotifierFactory {

    Notifier crearPushNotifier();

    Notifier crearEmailNotifier();

    Notifier crearDiscordNotifier();
}

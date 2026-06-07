package com.escrims.infrastructure.notifications.factory;

import com.escrims.infrastructure.notifications.notifiers.DiscordNotifier;
import com.escrims.infrastructure.notifications.notifiers.EmailNotifier;
import com.escrims.infrastructure.notifications.notifiers.PushNotifier;

public interface NotifierFactory {

    PushNotifier    crearPushNotifier();

    EmailNotifier   crearEmailNotifier();

    DiscordNotifier crearDiscordNotifier();
}

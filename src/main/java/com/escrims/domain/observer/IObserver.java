package com.escrims.domain.observer;

import com.escrims.domain.events.DomainEvent;

public interface IObserver {

    void onEvent(DomainEvent event);
}

package com.escrims.domain.observer;

import com.escrims.domain.events.DomainEvent;

public interface IObservable {

    void suscribe(IObserver subject);

    void unsuscribe(IObserver subject);

    void publish(DomainEvent event);
}

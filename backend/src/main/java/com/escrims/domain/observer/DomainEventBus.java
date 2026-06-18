package com.escrims.domain.observer;

import com.escrims.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus implements IObservable {

    private final List<IObserver> subscribers = new ArrayList<>();

    @Override
    public void suscribe(IObserver subject) {
        subscribers.add(subject);
    }

    @Override
    public void unsuscribe(IObserver subject) {
        subscribers.remove(subject);
    }

    @Override
    public void publish(DomainEvent event) {
        subscribers.forEach(o -> o.onEvent(event));
    }
}

package com.escrims.domain.observer;

import com.escrims.domain.events.DomainEvent;

public interface IObservable {

    void suscribir(IObserver observer);

    void desuscribir(IObserver observer);

    void notificar(DomainEvent event);

    default void publish(DomainEvent event) {
        notificar(event);
    }
}

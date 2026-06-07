package com.escrims.domain.observer;

import com.escrims.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class DomainEventBus implements IObservable {

    private final List<IObserver> observers = new ArrayList<>();

    @Override
    public void suscribir(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void desuscribir(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notificar(DomainEvent event) {
        observers.forEach(o -> o.onEvent(event));
    }

    public void publicar(DomainEvent event) {
        notificar(event);
    }

    public void publish(DomainEvent event) {
        publicar(event);
    }

    public void publicarTodos(List<DomainEvent> events) {
        events.forEach(this::publicar);
    }
}

package com.escrims.infrastructure.adapters;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Adapter (GOF).
 * Target: CalendarExporter
 * Adaptee: ICalLibrary
 */
public class ICalAdapter implements CalendarExporter {

    private final ICalLibrary library;

    public ICalAdapter(ICalLibrary library) {
        this.library = library;
    }

    @Override
    public String exportar(String titulo, LocalDateTime inicio, Duration duracion) {
        return library.generateICalString(titulo, inicio, duracion);
    }
}

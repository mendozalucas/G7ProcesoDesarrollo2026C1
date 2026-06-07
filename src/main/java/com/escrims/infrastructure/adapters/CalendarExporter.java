package com.escrims.infrastructure.adapters;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Target: interfaz que el sistema espera para exportar calendarios.
 */
public interface CalendarExporter {

    String exportar(String titulo, LocalDateTime inicio, Duration duracion);
}

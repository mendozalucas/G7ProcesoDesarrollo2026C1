package com.escrims.infrastructure.adapters;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Adaptee: librería iCal externa con interfaz incompatible.
 */
public class ICalLibrary {

    public String generateICalString(String title, LocalDateTime start, Duration duration) {
        return "BEGIN:VCALENDAR\nSUMMARY:" + title + "\nDTSTART:" + start + "\nDURATION:" + duration + "\nEND:VCALENDAR";
    }
}

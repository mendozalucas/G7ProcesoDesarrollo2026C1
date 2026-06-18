package com.escrims.infrastructure.notifications.impl;

import com.escrims.infrastructure.notifications.notifiers.INotificadorEmail;

public class JavaMailAdapter implements INotificadorEmail {

    private JavaMail adaptee;

    public JavaMailAdapter(JavaMail adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void enviar(String mensaje, String destino) {}
}

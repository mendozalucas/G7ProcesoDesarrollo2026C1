package com.escrims.domain.events;

public interface DomainEventVisitor {

    default void visit(LobbyArmadoEvent event) {}

    default void visit(ConfirmadoEvent event) {}

    default void visit(EnJuegoEvent event) {}

    default void visit(FinalizadoEvent event) {}

    default void visit(CanceladoEvent event) {}

    default void visit(PostulacionAceptadaEvent event) {}

    default void visit(PostulacionRechazadaEvent event) {}

    default void visit(NuevoScrimDisponibleEvent event) {}

    default void visit(ScrimCreadoEvent event) {}

    default void visit(ReporteConductaRegistradoEvent event) {}
}

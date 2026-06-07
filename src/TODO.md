# TODOs — Diagrama vs Código

Pendientes detectados al alinear `TPO Proceso.drawio` con `src/`.

## Diagrama (corregir en draw.io)

- [ ] **Scrim**: eliminar atributo basura `- hhuhiun`.
- [ ] **Scrim**: unificar tipos de ID (`Long` en diagrama vs `UUID` en código).
- [ ] **Scrim**: el diagrama muestra `organizador: Usuario` y `mvp: Usuario`; el código usa `organizadorId` (UUID) y no modela MVP en Scrim.
- [ ] **Scrim**: `matchmakingService` y `lifecycleService` como atributos del aggregate; en código viven como servicios inyectados (decidir dónde va la responsabilidad).
- [ ] **ScrimState / ScrimLifecycleService**: doble ubicación del estado (`Scrim.currentState` y `ScrimLifecycleService.estado`); definir una sola fuente de verdad.
- [ ] **IObservable / DomainEventBus**: métodos `suscribe`/`unsuscribe` tienen parámetro mal tipado (`IObserver` vs nombre `subject`); unificar con `publish(DomainEvent)`.
- [ ] **Juego abstracto**: atributos `- rango: Rango` y `- rol: Rol` no aplican a nivel de clase; pertenecen a `PerfilJuego` o participante.
- [ ] **Equipo**: diagrama usa `List<Usuario>`; código usa Composite (`JugadorComponent` / `Participante`) para soportar roles — actualizar diagrama.
- [ ] **Postulacion**: diagrama muestra `- estado: String`; código usa State pattern (`PostulacionState`).
- [ ] **Confirmacion**: diagrama referencia `Usuario` y `Scrim`; código guarda solo `usuarioId` + estado booleano.
- [ ] **Rol**: entidad separada en diagrama; código usa `RolJuego` como value object.
- [ ] **MatchmakingService**: diagrama mezcla `IMatchmakingStrategy` y `MatchmakingStrategy` en `setEstrategia`.
- [ ] **NotificationService** vs **DomainEventBus**: roles solapados; aclarar si NotificationService es facade o sobra.
- [ ] **Patrones Composite / Decorator / Facade**: no están dibujados explícitamente; considerar agregarlos si son requisito del TPO.

## Código (implementación pendiente)

- [ ] **Usuario.reportarJugador**: crear `ReporteConducta` y conectar con `ModerationService`.
- [ ] **InvitarJugadoresCommand**: integrar en flujo de postulaciones/invitaciones vía `ParticipationService`.
- [ ] **Swap entre equipos**: `SwapJugadoresCommand` solo intercambia dentro del mismo `LadoEquipo`.
- [ ] **Repositorios**: interfaces sin implementación en `src` (persistencia pendiente).
- [ ] **Postulacion (UUID)**: constructor legacy sin referencias a `Usuario`/`Scrim`; resolver al hidratar desde BD.

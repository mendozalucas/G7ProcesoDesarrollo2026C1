
# TPO Final — eScrims

## Plataforma de organización de scrims y partidas amistosas de eSports



### Propósito

Diseñar y desarrollar (ADOO) una app móvil (Regla de negocio) + backend que permita organizar scrims/partidas amistosas para distintos eSports (ej.: Valorant, LoL, CS2), gestionando emparejamientos por nivel/rango, estados del encuentro y notificaciones multi-canal.



---



# Condiciones generales



- Armar grupos de hasta 5 (cinco) personas.

- Se debe entregar un documento PDF con todas las consignas solicitadas. El mismo deberá contener la carátula con el nombre, apellido y LU de los integrantes del equipo.

- **Entrega final:** 18/06

- En los días de entrega se debe exponer la solución propuesta.

- Si bien el trabajo práctico es grupal, la evaluación es individual y lleva nota.



---



# 1) Objetivos del sistema



- Facilitar que jugadores creen, encuentren y se unan a scrims en su región.

- Emparejar jugadores por rango/MMR, rol y latencia, con algoritmos intercambiables.

- Gestionar el ciclo de vida del scrim, desde su creación hasta el registro de estadísticas.

- Enviar notificaciones por múltiples canales (push, email, Discord/Slack) según eventos clave.



---



# 2) Alcance



- App móvil (vista usuario) y panel web (vista organizador/capitán de equipo).

- Un scrim puede ser 1v1, 5v5 u otros formatos definidos por el juego.

- Integraciones externas opcionales (autenticación OAuth de plataformas gaming, bots de Discord, proveedores de email/push).



---



# 3) Requerimientos Funcionales



## 1. Registro y autenticación de usuarios



- Alta mediante usuario, email, contraseña. Opcional OAuth (Steam, Riot, Discord).

- Perfil editable:

  - juego principal

  - rango (ej.: Hierro–Radiante / Iron–Radiant)

  - roles (ej.: Duelist/Support/Jungla)

  - servidor/región

  - disponibilidad horaria

- Verificación de email (estado: Pendiente → Verificado).



---



## 2. Búsqueda de scrims



- Filtros por:

  - juego

  - formato (5v5, 3v3, 1v1)

  - rango mínimo/máximo

  - región

  - fecha/hora

  - latencia máxima

- Guardar búsquedas favoritas y crear alertas (Observer) cuando aparezcan coincidencias.



---



## 3. Creación de scrim



Un usuario creador define:



1. Juego y formato (cantidad de jugadores por lado).

2. Cantidad total de jugadores requeridos y roles por lado (opcional).

3. Región/servidor, límites de rango (min/max) y latencia máxima.

4. Fecha/hora, duración estimada y modalidad:

   - ranked-like

   - casual

   - práctica de estratos

5. Al crear, el scrim inicia en estado **“Buscando jugadores”**.



---



## 4. Estados del scrim (patrón State)



- **Buscando jugadores:** faltan plazas. Cuando se completa el cupo → “Lobby armado”.

- **Lobby armado:** todos los cupos llenos; cada jugador debe confirmar. Si todos confirman → “Confirmado”.

- **Confirmado:** el sistema programa cambio automático a “En juego” al llegar fecha/hora.

- **En juego:** una vez iniciado, puede pasar a “Finalizado” (cierre manual o por cron).

- **Finalizado:** se habilita carga de resultados/estadísticas y feedback.

- **Cancelado:** el organizador cancela antes del inicio. (Reglas para reembolsos/penalidades opcionales.)



Transiciones automáticas por tiempo (scheduler) y reglas de negocio.



---



## 5. Estrategias de emparejamiento (patrón Strategy)



Soportar estrategias conmutables:



- Por rango/MMR (diferencia máxima configurable).

- Por cercanía/latencia (ping promedio dentro de umbral).

- Por historial/compatibilidad:

  - sinergia de roles

  - abandono previo

  - fair play



Scrims configurables para cualquier nivel o con mínimo/máximo y roles obligatorios.



---



## 6. Gestión de equipos y roles



- El organizador puede asignar roles y swap entre jugadores antes del inicio (Command).

- Sistemas de suplentes: si un jugador se baja, se notifica a lista de espera.



---



## 7. Notificaciones (Observer + Abstract Factory/Adapter)



### Eventos que disparan notificaciones



1. Scrim creado que coincide con preferencias del usuario.

2. Cambio a Lobby armado (cupo completo).

3. Confirmado por todos.

4. Cambio a En juego, Finalizado o Cancelado.



### Canales



- Push (Firebase)

- Email (JavaMail/SendGrid)

- Discord/Slack (webhook/bot)



---



## 8. Estadísticas y feedback



- Al finalizar, el sistema permite cargar:

  - resultado

  - MVP

  - kills/assists

  - etc.

- Rating de jugadores y comentarios (con moderación básica):

  - pendiente

  - aprobado

  - rechazado



---



## 9. Moderación y penalidades



- Registro de abandono o no-show.

- Sistema de strikes y cooldown para reincidentes.

- Reportes de conducta (Chain of Responsibility para resolución/derivación):

  - auto-resolver

  - bot

  - moderador humano



---



## 10. Calendario y recordatorios



- Sincronización iCal (Adapter).

- Recordatorios automáticos N horas antes.



---



## 11. Multijuego y multirregión



- Un scrim siempre pertenece a un juego y región/servidor.

- Las reglas de emparejamiento pueden variar por juego.



---



# 4) Requerimientos No Funcionales



- Arquitectura: seguir MVC. Capa de Dominio separada.

- Patrones: usar al menos cuatro; se sugiere:

  - State

  - Strategy

  - Observer

  - Abstract Factory



Extras opcionales:

- Builder

- Command

- Adapter

- Chain of Responsibility

- Template Method

- Repository



- Persistencia: ORM/JPA o equivalente.

- Escalabilidad: colas para notificaciones (ej.: RabbitMQ/Kafka, simulado si no se implementa).

- Disponibilidad: manejo de fallos de proveedores de notificación con reintentos exponenciales.

- Seguridad:

  - hashing de contraseñas

  - roles (USER, MOD, ADMIN)

  - rate limiting básico

- Rendimiento: emparejamiento debe ejecutarse en < 2s para lotes de 500 candidatos.

- Trazabilidad: logs de auditoría para cambios de estado y acciones de moderación.

- Testing:

  - unit tests

  - tests de integración para estrategias

  - tests de estado



---



# 5) Patrones de Diseño (mapa sugerido)



- **State:** ciclo de vida del Scrim

  - Buscando

  - LobbyArmado

  - Confirmado

  - EnJuego

  - Finalizado

  - Cancelado



- **Strategy:** algoritmos de emparejamiento

  - MMR

  - latencia

  - historial/compatibilidad



- **Observer:** suscriptores a eventos de dominio → despachan notificaciones.



- **Abstract Factory:** creación de Notifiers por canal/entorno (dev/prod) y por región.



## Opcionales



- **Builder:** armado incremental de Scrim con validaciones.

- **Command:** acciones como:

  - AsignarRol

  - InvitarJugador

  - SwapJugadores

  - undo antes de confirmar

- **Adapter:** integración con Discord/Slack/SendGrid/iCal.

- **Chain of Responsibility:** pipeline de moderación de reportes.

- **Template Method:** validación por juego (distintas reglas de composición de equipos).



**Entrega:** incluir el diagrama con estereotipos marcando cada patrón.



---



# 6) Modelo de Dominio (sugerido)



- `Usuario(id, username, email, passwordHash, rangoPorJuego, rolesPreferidos, region, preferencias)`

- `Scrim(id, juego, formato, region, rangoMin, rangoMax, latenciaMax, fechaHora, duracion, estado, cupos, reglasRoles)`

- `Equipo(id, lado, jugadores[0..n])`

- `Postulacion(id, usuario, scrim, rolDeseado, estado: Pendiente/Aceptada/Rechazada)`

- `Confirmacion(id, usuario, scrim, confirmado: bool)`

- `Notificacion(id, tipo, canal, payload, estado)`

- `Estadistica(id, scrim, usuario, mvp, kda, observaciones)`

- `ReporteConducta(id, scrim, reportado, motivo, estado, sancion)`



---



# 7) Casos de Uso (resumen)



1. CU1 – Registrar usuario

2. CU2 – Autenticar usuario

3. CU3 – Crear scrim

4. CU4 – Postularse a scrim

5. CU5 – Emparejar y armar lobby (auto/manual)

6. CU6 – Confirmar participación

7. CU7 – Iniciar scrim (scheduler)

8. CU8 – Finalizar y cargar estadísticas

9. CU9 – Cancelar scrim

10. CU10 – Notificar eventos

11. CU11 – Moderar reportes



Cada CU debe incluir:

- Actores

- Precondiciones

- Flujo principal

- Flujos alternativos

- Reglas de negocio

- Postcondiciones



---



# 8) Diagrama de Estados (texto)



- Buscando → (cupo completo) → LobbyArmado

- LobbyArmado → (todos confirman) → Confirmado

- Confirmado → (fechaHora alcanzada) → EnJuego

- EnJuego → (fin) → Finalizado

- Cualquier estado antes de EnJuego → (cancelar) → Cancelado



---



# 9) Diagrama de Clases UML (guía de componentes)



## State



- `ScrimContext`

- `ScrimState (interface)`

- `BuscandoState`

- `LobbyArmadoState`

- `ConfirmadoState`

- `EnJuegoState`

- `FinalizadoState`

- `CanceladoState`



## Strategy



- `MatchmakingStrategy (interface)`

- `ByMMRStrategy`

- `ByLatencyStrategy`

- `ByHistoryStrategy`



## Observer



- `DomainEventBus (Subject)`

- `NotificationSubscriber (Observer)`



Implementaciones:

- `PushNotifier`

- `EmailNotifier`

- `DiscordNotifier`



## Abstract Factory



- `NotifierFactory`

  - crea notifiers según canal y entorno



## Command



- `ScrimCommand`

- `AsignarRolCommand`

- `InvitarJugadorCommand`

- `SwapJugadoresCommand`



## Builder



- `ScrimBuilder`

  - validaciones encadenadas



## Adapter



- `DiscordAdapter`

- `SendGridAdapter`

- `ICalAdapter`



**Entrega:** incluir el diagrama con estereotipos marcando cada patrón.



---



# 10) API (sugerida)



```http

POST /api/auth/register

POST /api/auth/login



GET /api/scrims?juego=&region=&rangoMin=&rangoMax=&fecha=&latenciaMax=



POST /api/scrims

POST /api/scrims/{id}/postulaciones

POST /api/scrims/{id}/confirmaciones



POST /api/scrims/{id}/acciones/{command}



POST /api/scrims/{id}/cancelar

POST /api/scrims/{id}/finalizar

POST /api/scrims/{id}/estadisticas
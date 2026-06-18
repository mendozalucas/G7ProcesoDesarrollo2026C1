# G7ProcesoDesarrollo2026C1 — eScrims

Monorepo del TPO: backend Spring Boot + frontend React.

## Estructura

```
G7ProcesoDesarrollo2026C1/
├── backend/     # API REST (Java 21, Spring Boot, JPA, H2)
├── frontend/    # UI (React, Vite, TypeScript)
└── uml/         # Diagramas
```

## Backend

```bash
cd backend
mvn spring-boot:run
```

- API: http://localhost:8080/api
- H2 console: http://localhost:8080/h2-console

### Datos de demo (seed)

Con `escrims.seed.enabled: true` (por defecto en local), al **primer arranque** se crean:

| Rol | Usuario | Email | Password |
|-----|---------|-------|----------|
| Organizador | `organizador_demo` | `org@escrims.local` | `secret123` |
| Valorant | `driver_val_1` … `driver_val_3` | `driver.valN@escrims.local` | `secret123` |
| LoL | `driver_lol_1` … `driver_lol_3` | `driver.lolN@escrims.local` | `secret123` |
| CS2 | `driver_cs2_1` … `driver_cs2_3` | `driver.csN@escrims.local` | `secret123` |

Hay **un scrim por juego** con los 3 drivers ya postulados (estado pendiente). Entrá como organizador y abrí cada scrim para ver la lista.

Para recargar el seed desde cero (desde la **raíz del repo** `G7ProcesoDesarrollo2026C1`):

```powershell
.\scripts\reset-seed.ps1
cd backend
mvn spring-boot:run
```

Si ya estás en `backend`:

```powershell
..\scripts\reset-seed.ps1
mvn spring-boot:run
```

Desactivar seed: `escrims.seed.enabled: false` en `application.yml`.

## Frontend

```bash
cd frontend
npm install
npm run dev
```

- UI: http://localhost:5173
- El proxy de Vite redirige `/api` al backend en `:8080`

## Pantallas

- Landing, registro e login
- Listado y detalle de scrims
- Crear scrim
- Perfil de usuario

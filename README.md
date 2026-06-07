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

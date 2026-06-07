import { useEffect, useState, type ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { ApiError, api } from '../api/client';
import { useAuth } from '../context/AuthContext';

export function ProtectedRoute({ children }: { children: ReactNode }) {
  const { usuarioId, logout } = useAuth();
  const [sessionInvalid, setSessionInvalid] = useState(false);
  const [checking, setChecking] = useState(!!usuarioId);

  useEffect(() => {
    if (!usuarioId) {
      setChecking(false);
      return;
    }
    void api.getProfile(usuarioId)
      .catch((err: unknown) => {
        if (err instanceof ApiError && err.isUsuarioNoEncontrado()) {
          logout();
          setSessionInvalid(true);
        }
      })
      .finally(() => setChecking(false));
  }, [usuarioId, logout]);

  if (!usuarioId) {
    return <Navigate to="/login" replace />;
  }

  if (checking) {
    return <p className="muted">Verificando sesión…</p>;
  }

  if (sessionInvalid) {
    return (
      <Navigate
        to="/login"
        replace
        state={{ message: 'Tu sesión ya no es válida (usuario no encontrado en la base). Volvé a ingresar o registrate.' }}
      />
    );
  }

  return children;
}

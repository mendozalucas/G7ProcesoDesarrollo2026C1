import { createContext, useContext, useMemo, useState, type ReactNode } from 'react';

interface AuthContextValue {
  usuarioId: string | null;
  login: (id: string) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextValue | null>(null);
const STORAGE_KEY = 'escrims_usuario_id';

export function AuthProvider({ children }: { children: ReactNode }) {
  const [usuarioId, setUsuarioId] = useState<string | null>(
    () => localStorage.getItem(STORAGE_KEY),
  );

  const value = useMemo(
    () => ({
      usuarioId,
      login: (id: string) => {
        localStorage.setItem(STORAGE_KEY, id);
        setUsuarioId(id);
      },
      logout: () => {
        localStorage.removeItem(STORAGE_KEY);
        setUsuarioId(null);
      },
    }),
    [usuarioId],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth debe usarse dentro de AuthProvider');
  return ctx;
}

import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ApiError, api } from '../api/client';
import { useAuth } from '../context/AuthContext';
import type { UsuarioProfile } from '../types';

export function ProfilePage() {
  const { usuarioId, logout } = useAuth();
  const navigate = useNavigate();
  const [profile, setProfile] = useState<UsuarioProfile | null>(null);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!usuarioId) return;
    void api.getProfile(usuarioId)
      .then(setProfile)
      .catch((err: unknown) => {
        if (err instanceof ApiError && err.isUsuarioNoEncontrado()) {
          logout();
          navigate('/login', {
            replace: true,
            state: { message: 'Tu usuario no existe en la base actual. Registrate de nuevo o ingresá con otra cuenta.' },
          });
          return;
        }
        setError(err instanceof ApiError ? err.message : 'No se pudo cargar el perfil');
      });
  }, [usuarioId, logout, navigate]);

  if (error) return <p className="error">{error}</p>;
  if (!profile) return <p className="muted">Cargando perfil…</p>;

  return (
    <div className="page narrow">
      <h1>{profile.username}</h1>
      <p className="muted">{profile.email}</p>

      <div className="profile-badges">
        <span className="badge">{profile.rol}</span>
        {profile.verificado && <span className="badge badge-ok">Email verificado</span>}
        {profile.strikes > 0 && <span className="badge badge-warn">{profile.strikes} strikes</span>}
      </div>

      {profile.perfilesJuego.length > 0 && (
        <section className="detail-section">
          <h2>Perfiles de juego</h2>
          {profile.perfilesJuego.map((p) => (
            <div key={p.juego} className="profile-game">
              <strong>{p.juego}</strong>
              {p.mmr != null && <span> · MMR {p.mmr}</span>}
              {p.servidor && <span> · {p.servidor}/{p.zona}</span>}
            </div>
          ))}
        </section>
      )}

      {profile.proveedoresOAuth.length > 0 && (
        <section className="detail-section">
          <h2>Cuentas vinculadas</h2>
          <p>{profile.proveedoresOAuth.join(', ')}</p>
        </section>
      )}
    </div>
  );
}

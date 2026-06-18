import { type FormEvent, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ApiError, api } from '../api/client';
import { useAuth } from '../context/AuthContext';
import type { BusquedaFavorita, UsuarioProfile } from '../types';
import { formatRegion } from '../utils/scrim';
import { isSessionInvalid, redirectToLogin } from '../utils/session';

export function ProfilePage() {
  const { usuarioId, logout } = useAuth();
  const navigate = useNavigate();
  const [profile, setProfile] = useState<UsuarioProfile | null>(null);
  const [busquedas, setBusquedas] = useState<BusquedaFavorita[]>([]);
  const [username, setUsername] = useState('');
  const [error, setError] = useState('');
  const [info, setInfo] = useState('');
  const [loading, setLoading] = useState(false);

  const load = async () => {
    if (!usuarioId) return;
    const [p, b] = await Promise.all([
      api.getProfile(usuarioId),
      api.listBusquedasFavoritas(usuarioId),
    ]);
    setProfile(p);
    setUsername(p.username);
    setBusquedas(b);
  };

  useEffect(() => {
    if (!usuarioId) return;
    void load().catch((err: unknown) => {
      if (err instanceof ApiError && err.isUsuarioNoEncontrado()) {
        redirectToLogin(
          logout,
          navigate,
          'Tu usuario no existe en la base actual. Registrate de nuevo o ingresá con otra cuenta.',
        );
        return;
      }
      setError(err instanceof ApiError ? err.message : 'No se pudo cargar el perfil');
    });
  }, [usuarioId, logout, navigate]);

  const handleError = (err: unknown) => {
    if (isSessionInvalid(err)) {
      redirectToLogin(logout, navigate, 'Tu sesión expiró. Volvé a iniciar sesión.');
      return;
    }
    setError(err instanceof ApiError ? err.message : 'Ocurrió un error');
  };

  const handleUpdateProfile = async (e: FormEvent) => {
    e.preventDefault();
    if (!usuarioId) return;
    setLoading(true);
    setError('');
    setInfo('');
    try {
      const updated = await api.updateProfile(usuarioId, { username });
      setProfile(updated);
      setInfo('Perfil actualizado');
    } catch (err) {
      handleError(err);
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyEmail = async () => {
    if (!usuarioId) return;
    setLoading(true);
    setError('');
    setInfo('');
    try {
      await api.verifyEmail(usuarioId);
      await load();
      setInfo('Email verificado');
    } catch (err) {
      handleError(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSaveBusqueda = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!usuarioId) return;
    const fd = new FormData(e.currentTarget);
    setLoading(true);
    setError('');
    setInfo('');
    try {
      await api.saveBusquedaFavorita(usuarioId, {
        juego: String(fd.get('juego')),
        servidor: String(fd.get('servidor')),
        zona: String(fd.get('zona')),
        rangoMin: Number(fd.get('rangoMin')),
        rangoMax: Number(fd.get('rangoMax')),
        rolBuscado: String(fd.get('rolBuscado')),
        activarAlerta: fd.get('activarAlerta') === 'on',
      });
      await load();
      setInfo('Búsqueda guardada');
      e.currentTarget.reset();
    } catch (err) {
      handleError(err);
    } finally {
      setLoading(false);
    }
  };

  const toggleAlerta = async (busquedaId: string, activar: boolean) => {
    if (!usuarioId) return;
    setLoading(true);
    setError('');
    try {
      if (activar) {
        await api.activarAlertaBusqueda(usuarioId, busquedaId);
      } else {
        await api.desactivarAlertaBusqueda(usuarioId, busquedaId);
      }
      await load();
    } catch (err) {
      handleError(err);
    } finally {
      setLoading(false);
    }
  };

  if (error && !profile) return <p className="error">{error}</p>;
  if (!profile) return <p className="muted">Cargando perfil…</p>;

  return (
    <div className="page narrow">
      <h1>{profile.username}</h1>
      <p className="muted">{profile.email}</p>

      <div className="profile-badges">
        {profile.verificado
          ? <span className="badge badge-ok">Email verificado</span>
          : <span className="badge badge-warn">Email sin verificar</span>}
      </div>

      {info && <p className="success">{info}</p>}
      {error && <p className="error">{error}</p>}

      <section className="detail-section">
        <h2>Editar perfil</h2>
        <form onSubmit={handleUpdateProfile} className="form">
          <label>
            Usuario
            <input value={username} onChange={(e) => setUsername(e.target.value)} required />
          </label>
          <button type="submit" className="btn-primary" disabled={loading}>
            Guardar cambios
          </button>
        </form>
        {!profile.verificado && (
          <button
            type="button"
            className="btn-secondary"
            disabled={loading}
            onClick={() => void handleVerifyEmail()}
          >
            Verificar email
          </button>
        )}
      </section>

      <section className="detail-section">
        <h2>Búsquedas favoritas</h2>
        {busquedas.length === 0 && <p className="muted">No tenés búsquedas guardadas.</p>}
        <ul className="busqueda-list">
          {busquedas.map((b) => (
            <li key={b.id} className="busqueda-item">
              <div>
                <strong>{b.juego.toUpperCase()}</strong>
                {b.region && <span> · {formatRegion(b.region)}</span>}
                {b.rangoMin != null && b.rangoMax != null && (
                  <span> · {b.rangoMin}–{b.rangoMax} MMR</span>
                )}
                {b.rolBuscado && <span> · {b.rolBuscado}</span>}
              </div>
              <button
                type="button"
                className="btn-ghost btn-sm"
                disabled={loading}
                onClick={() => void toggleAlerta(b.id, !b.alertaActiva)}
              >
                {b.alertaActiva ? 'Desactivar alerta' : 'Activar alerta'}
              </button>
            </li>
          ))}
        </ul>

        <h3>Nueva búsqueda</h3>
        <form onSubmit={handleSaveBusqueda} className="form form-grid">
          <label>
            Juego
            <select name="juego" defaultValue="valorant" required>
              <option value="valorant">Valorant</option>
              <option value="lol">LoL</option>
              <option value="cs2">CS2</option>
            </select>
          </label>
          <label>
            Servidor
            <input name="servidor" defaultValue="LAN" required />
          </label>
          <label>
            Zona
            <input name="zona" defaultValue="AR" required />
          </label>
          <label>
            Rango mín.
            <input name="rangoMin" type="number" defaultValue={1000} />
          </label>
          <label>
            Rango máx.
            <input name="rangoMax" type="number" defaultValue={2000} />
          </label>
          <label>
            Rol buscado
            <input name="rolBuscado" defaultValue="Duelist" />
          </label>
          <label className="checkbox-label full-width">
            <input name="activarAlerta" type="checkbox" defaultChecked />
            Activar alerta al guardar
          </label>
          <button type="submit" className="btn-primary full-width" disabled={loading}>
            Guardar búsqueda
          </button>
        </form>
      </section>
    </div>
  );
}

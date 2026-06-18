import { type FormEvent, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { ApiError, api } from '../api/client';
import { useAuth } from '../context/AuthContext';

export function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const sessionMessage = (location.state as { message?: string } | null)?.message;
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [oauthLoading, setOauthLoading] = useState(false);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const res = await api.login({ email, password });
      login(res.usuarioId);
      navigate('/scrims');
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Error al ingresar');
    } finally {
      setLoading(false);
    }
  };

  const handleOAuth = async (proveedor: string) => {
    setError('');
    setOauthLoading(true);
    try {
      await api.getOAuthUrl(proveedor);
      const res = await api.loginOAuth({
        proveedor,
        externalId: `${proveedor}-demo-001`,
        email: `oauth-${proveedor}@escrims.local`,
        username: `user_${proveedor}`,
      });
      login(res.usuarioId);
      navigate('/scrims');
    } catch (err) {
      setError(err instanceof ApiError ? err.message : 'Error con OAuth');
    } finally {
      setOauthLoading(false);
    }
  };

  return (
    <div className="auth-card">
      <h1>Ingresar</h1>
      <p className="muted">Accedé a tu cuenta para buscar y organizar scrims.</p>
      {sessionMessage && <p className="error">{sessionMessage}</p>}
      <form onSubmit={handleSubmit} className="form">
        <label>
          Email
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </label>
        <label>
          Contraseña
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </label>
        {error && <p className="error">{error}</p>}
        <button type="submit" className="btn-primary" disabled={loading || oauthLoading}>
          {loading ? 'Ingresando…' : 'Ingresar'}
        </button>
      </form>

      <div className="oauth-divider">
        <span>o continuar con</span>
      </div>
      <div className="oauth-buttons">
        <button
          type="button"
          className="btn-secondary"
          disabled={loading || oauthLoading}
          onClick={() => void handleOAuth('discord')}
        >
          Discord
        </button>
        <button
          type="button"
          className="btn-secondary"
          disabled={loading || oauthLoading}
          onClick={() => void handleOAuth('google')}
        >
          Google
        </button>
      </div>

      <p className="muted center">
        ¿No tenés cuenta? <Link to="/register">Registrate</Link>
      </p>
    </div>
  );
}

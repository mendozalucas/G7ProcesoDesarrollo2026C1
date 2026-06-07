import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export function Layout() {
  const { usuarioId, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="app-shell">
      <header className="topbar">
        <Link to="/" className="brand">
          <span className="brand-mark">e</span>
          <span>eScrims</span>
        </Link>
        <nav className="nav">
          {usuarioId ? (
            <>
              <Link to="/scrims">Scrims</Link>
              <Link to="/scrims/nuevo">Crear</Link>
              <Link to="/perfil">Perfil</Link>
              <button type="button" className="btn-ghost" onClick={handleLogout}>
                Salir
              </button>
            </>
          ) : (
            <>
              <Link to="/login">Ingresar</Link>
              <Link to="/register" className="btn-primary btn-sm">
                Registrarse
              </Link>
            </>
          )}
        </nav>
      </header>
      <main className="main">
        <Outlet />
      </main>
    </div>
  );
}

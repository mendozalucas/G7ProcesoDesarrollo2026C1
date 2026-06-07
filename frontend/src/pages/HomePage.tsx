import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

export function HomePage() {
  const { usuarioId } = useAuth();

  return (
    <section className="hero">
      <p className="hero-kicker">Plataforma de scrims</p>
      <h1>Encontrá partidas de práctica con tu nivel</h1>
      <p className="hero-sub">
        Organizá scrims, postulate a lobbies y recibí alertas cuando haya partidas que coincidan con tu búsqueda.
      </p>
      <div className="hero-actions">
        {usuarioId ? (
          <Link to="/scrims" className="btn-primary btn-lg">Ver scrims</Link>
        ) : (
          <>
            <Link to="/register" className="btn-primary btn-lg">Empezar gratis</Link>
            <Link to="/login" className="btn-secondary btn-lg">Ingresar</Link>
          </>
        )}
      </div>
      <div className="hero-stats">
        <div><strong>Valorant · LoL · CS2</strong><span>Juegos soportados</span></div>
        <div><strong>Matchmaking</strong><span>Por rango y región</span></div>
        <div><strong>Alertas</strong><span>Búsquedas favoritas</span></div>
      </div>
    </section>
  );
}

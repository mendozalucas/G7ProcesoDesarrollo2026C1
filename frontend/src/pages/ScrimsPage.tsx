import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api/client';
import type { Scrim } from '../types';

const ESTADO_LABEL: Record<string, string> = {
  BUSCANDO: 'Buscando jugadores',
  LOBBY_ARMADO: 'Lobby armado',
  CONFIRMADO: 'Confirmado',
  EN_JUEGO: 'En juego',
  FINALIZADO: 'Finalizado',
  CANCELADO: 'Cancelado',
};

export function ScrimsPage() {
  const [scrims, setScrims] = useState<Scrim[]>([]);
  const [juego, setJuego] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const load = async () => {
    setLoading(true);
    setError('');
    try {
      const params: Record<string, string> = {};
      if (juego) params.juego = juego;
      setScrims(await api.listScrims(params));
    } catch (err) {
      setError(err instanceof Error ? err.message : 'No se pudieron cargar los scrims');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void load();
  }, []);

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <h1>Scrims disponibles</h1>
          <p className="muted">Partidas de práctica organizadas por la comunidad.</p>
        </div>
        <Link to="/scrims/nuevo" className="btn-primary">
          + Nuevo scrim
        </Link>
      </div>

      <div className="filters">
        <select value={juego} onChange={(e) => setJuego(e.target.value)}>
          <option value="">Todos los juegos</option>
          <option value="valorant">Valorant</option>
          <option value="lol">LoL</option>
          <option value="cs2">CS2</option>
        </select>
        <button type="button" className="btn-secondary" onClick={() => void load()}>
          Filtrar
        </button>
      </div>

      {error && <p className="error">{error}</p>}
      {loading && <p className="muted">Cargando…</p>}

      {!loading && scrims.length === 0 && (
        <div className="empty-state">
          <p>No hay scrims todavía.</p>
          <Link to="/scrims/nuevo">Creá el primero</Link>
        </div>
      )}

      <div className="scrim-grid">
        {scrims.map((s) => (
          <article key={s.id} className="scrim-card">
            <div className="scrim-card-top">
              <span className="game-tag">{s.juego.toUpperCase()}</span>
              <span className={`estado estado-${s.estado.toLowerCase()}`}>
                {ESTADO_LABEL[s.estado] ?? s.estado}
              </span>
            </div>
            <h3>
              {s.jugadoresPorLado}v{s.jugadoresPorLado} · {s.servidor}/{s.zona}
            </h3>
            <ul className="meta-list">
              <li>Rango {s.rangoMin}–{s.rangoMax}</li>
              <li>≤ {s.latenciaMaxMs} ms</li>
              <li>{s.modalidad}</li>
              <li>{new Date(s.fechaHora).toLocaleString('es-AR')}</li>
            </ul>
            <div className="scrim-card-footer">
              <span>{s.cuposDisponibles} cupos libres</span>
              <Link to={`/scrims/${s.id}`}>Ver detalle</Link>
            </div>
          </article>
        ))}
      </div>
    </div>
  );
}

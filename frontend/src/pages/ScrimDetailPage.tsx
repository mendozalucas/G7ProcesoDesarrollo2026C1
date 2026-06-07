import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { api } from '../api/client';
import type { Scrim } from '../types';

export function ScrimDetailPage() {
  const { id } = useParams<{ id: string }>();
  const [scrim, setScrim] = useState<Scrim | null>(null);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!id) return;
    void api.getScrim(id).then(setScrim).catch((err: Error) => setError(err.message));
  }, [id]);

  if (error) return <p className="error">{error}</p>;
  if (!scrim) return <p className="muted">Cargando scrim…</p>;

  return (
    <div className="page narrow">
      <Link to="/scrims" className="back-link">← Volver</Link>
      <div className="detail-header">
        <span className="game-tag">{scrim.juego.toUpperCase()}</span>
        <h1>{scrim.jugadoresPorLado}v{scrim.jugadoresPorLado} · {scrim.servidor}/{scrim.zona}</h1>
        <p className="estado-badge">{scrim.estado}</p>
      </div>

      <section className="detail-section">
        <h2>Información</h2>
        <dl className="detail-grid">
          <dt>Modalidad</dt><dd>{scrim.modalidad}</dd>
          <dt>Rango</dt><dd>{scrim.rangoMin} – {scrim.rangoMax}</dd>
          <dt>Latencia máx.</dt><dd>{scrim.latenciaMaxMs} ms</dd>
          <dt>Fecha</dt><dd>{new Date(scrim.fechaHora).toLocaleString('es-AR')}</dd>
          <dt>Duración</dt><dd>{scrim.duracionMinutos} min</dd>
          <dt>Cupos libres</dt><dd>{scrim.cuposDisponibles}</dd>
          <dt>Participantes</dt><dd>{scrim.participantes.length}</dd>
        </dl>
      </section>
    </div>
  );
}

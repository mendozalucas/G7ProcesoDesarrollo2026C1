import { type FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api/client';
import { useAuth } from '../context/AuthContext';

export function CreateScrimPage() {
  const { usuarioId } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const defaultDate = new Date(Date.now() + 86400000);
  defaultDate.setMinutes(0, 0, 0);

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!usuarioId) return;
    const fd = new FormData(e.currentTarget);
    const juego = String(fd.get('juego'));
    setLoading(true);
    setError('');
    try {
      const res = await api.createScrim({
        juego,
        jugadoresPorLado: Number(fd.get('jugadoresPorLado')),
        servidor: String(fd.get('servidor')),
        zona: String(fd.get('zona')),
        rangoMin: { juego, tier: 'Min', numerico: Number(fd.get('rangoMin')) },
        rangoMax: { juego, tier: 'Max', numerico: Number(fd.get('rangoMax')) },
        latenciaMaxMs: Number(fd.get('latenciaMaxMs')),
        fechaHora: String(fd.get('fechaHora')),
        duracionMinutos: Number(fd.get('duracionMinutos')),
        modalidadNombre: String(fd.get('modalidad')),
        organizadorId: usuarioId,
      });
      navigate(`/scrims/${res.id}`);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'No se pudo crear el scrim');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page narrow">
      <h1>Crear scrim</h1>
      <p className="muted">Publicá una partida de práctica para tu equipo.</p>

      <form onSubmit={handleSubmit} className="form form-grid">
        <label>
          Juego
          <select name="juego" defaultValue="valorant" required>
            <option value="valorant">Valorant</option>
            <option value="lol">LoL</option>
            <option value="cs2">CS2</option>
          </select>
        </label>
        <label>
          Formato
          <select name="jugadoresPorLado" defaultValue="5" required>
            <option value="5">5v5</option>
            <option value="3">3v3</option>
            <option value="2">2v2</option>
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
          Rango mínimo (MMR)
          <input name="rangoMin" type="number" defaultValue={1000} required />
        </label>
        <label>
          Rango máximo (MMR)
          <input name="rangoMax" type="number" defaultValue={2000} required />
        </label>
        <label>
          Latencia máx. (ms)
          <input name="latenciaMaxMs" type="number" defaultValue={80} required />
        </label>
        <label>
          Duración (min)
          <input name="duracionMinutos" type="number" defaultValue={60} required />
        </label>
        <label>
          Fecha y hora
          <input
            name="fechaHora"
            type="datetime-local"
            defaultValue={defaultDate.toISOString().slice(0, 16)}
            required
          />
        </label>
        <label>
          Modalidad
          <select name="modalidad" defaultValue="CASUAL">
            <option value="CASUAL">Casual</option>
            <option value="RANKED_LIKE">Ranked-like</option>
            <option value="PRACTICA_ESTRATOS">Práctica estratos</option>
          </select>
        </label>

        {error && <p className="error full-width">{error}</p>}
        <div className="form-actions full-width">
          <button type="button" className="btn-secondary" onClick={() => navigate(-1)}>
            Cancelar
          </button>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Publicando…' : 'Publicar scrim'}
          </button>
        </div>
      </form>
    </div>
  );
}

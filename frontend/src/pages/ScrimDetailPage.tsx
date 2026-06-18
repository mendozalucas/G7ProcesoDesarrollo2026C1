import { type FormEvent, type ReactNode, useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { ApiError, api } from '../api/client';
import { useAuth } from '../context/AuthContext';
import type { Postulacion, Scrim } from '../types';
import { formatRegion } from '../utils/scrim';
import { isSessionInvalid, redirectToLogin } from '../utils/session';

const ESTADO_LABEL: Record<string, string> = {
  BUSCANDO: 'Buscando jugadores',
  LOBBY_ARMADO: 'Lobby armado',
  CONFIRMADO: 'Confirmado',
  EN_JUEGO: 'En juego',
  FINALIZADO: 'Finalizado',
  CANCELADO: 'Cancelado',
};

const ESTADO_ACTIVO = new Set(['BUSCANDO', 'LOBBY_ARMADO', 'CONFIRMADO', 'EN_JUEGO']);

const POSTULACION_ESTADO: Record<string, string> = {
  PENDIENTE: 'Pendiente',
  ACEPTADA: 'Aceptada',
  RECHAZADA: 'Rechazada',
};

function ActionCard({
  step,
  title,
  description,
  children,
  variant = 'default',
}: {
  step?: number;
  title: string;
  description: string;
  children: ReactNode;
  variant?: 'default' | 'organizer' | 'danger';
}) {
  return (
    <article className={`action-card action-card--${variant}`}>
      <div className="action-card-header">
        {step != null && <span className="step-badge">{step}</span>}
        <div>
          <h3>{title}</h3>
          <p className="action-card-desc">{description}</p>
        </div>
      </div>
      <div className="action-card-body">{children}</div>
    </article>
  );
}

export function ScrimDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { usuarioId, logout } = useAuth();
  const navigate = useNavigate();
  const [scrim, setScrim] = useState<Scrim | null>(null);
  const [error, setError] = useState('');
  const [info, setInfo] = useState('');
  const [loading, setLoading] = useState(false);
  const [rol, setRol] = useState('Duelist');
  const [postulacionId, setPostulacionId] = useState('');
  const [postulaciones, setPostulaciones] = useState<Postulacion[]>([]);
  const [motivoCancelar, setMotivoCancelar] = useState('');
  const [motivoReporte, setMotivoReporte] = useState('');

  const load = async () => {
    if (!id) return;
    setError('');
    try {
      setScrim(await api.getScrim(id));
    } catch (err) {
      setError(err instanceof Error ? err.message : 'No se pudo cargar el scrim');
    }
  };

  useEffect(() => {
    void load();
  }, [id]);

  const reloadPostulaciones = async () => {
    if (!id) return;
    try {
      setPostulaciones(await api.listPostulaciones(id));
    } catch {
      setPostulaciones([]);
    }
  };

  useEffect(() => {
    if (!id || !scrim || !usuarioId || usuarioId !== scrim.organizadorId) return;
    void reloadPostulaciones();
  }, [id, scrim?.id, scrim?.organizadorId, usuarioId]);

  const runAction = async (action: () => Promise<void>, successMsg: string) => {
    if (!id) return;
    setLoading(true);
    setError('');
    setInfo('');
    try {
      await action();
      setInfo(successMsg);
      const updated = await api.getScrim(id);
      setScrim(updated);
      if (usuarioId === updated.organizadorId) {
        await reloadPostulaciones();
      }
    } catch (err) {
      if (isSessionInvalid(err)) {
        redirectToLogin(logout, navigate, 'Tu sesión expiró. Volvé a iniciar sesión.');
        return;
      }
      setError(err instanceof ApiError ? err.message : 'Ocurrió un error');
    } finally {
      setLoading(false);
    }
  };

  const handlePostular = (e: FormEvent) => {
    e.preventDefault();
    if (!id || !usuarioId || !scrim) return;
    setLoading(true);
    setError('');
    setInfo('');
    void api.postular(id, { usuarioId, juego: scrim.juego, rol })
      .then((res) => {
        setPostulacionId(String(res.postulacionId));
        setInfo(`Te postulaste correctamente. Tu número de postulación es ${res.postulacionId}.`);
      })
      .catch((err) => {
        if (isSessionInvalid(err)) {
          redirectToLogin(logout, navigate, 'Tu sesión expiró. Volvé a iniciar sesión.');
          return;
        }
        setError(err instanceof ApiError ? err.message : 'No se pudo postular');
      })
      .finally(() => setLoading(false));
  };

  const aceptarJugador = (p: Postulacion) => {
    if (!id) return;
    void runAction(
      () => api.aceptarPostulacion(id, p.id),
      `${p.username} fue aceptado/a.`,
    );
  };

  if (error && !scrim) return <p className="error">{error}</p>;
  if (!scrim) return <p className="muted">Cargando scrim…</p>;

  const esOrganizador = usuarioId === scrim.organizadorId;
  const scrimActivo = ESTADO_ACTIVO.has(scrim.estado);
  const estadoLabel = ESTADO_LABEL[scrim.estado] ?? scrim.estado;
  const postulantesAceptados = postulaciones.filter((p) => p.estado === 'ACEPTADA').length;
  const postulantesPendientes = postulaciones.filter((p) => p.estado === 'PENDIENTE').length;
  const puedeArmarLobby = scrim.estado === 'BUSCANDO' && postulantesAceptados > 0;
  const lobbyYaArmado = scrim.estado === 'LOBBY_ARMADO' || scrim.estado === 'CONFIRMADO';

  return (
    <div className="page scrim-detail-page">
      <Link to="/scrims" className="back-link">← Volver al listado</Link>

      <header className="detail-header">
        <div className="detail-header-top">
          <span className="game-tag">{scrim.juego.toUpperCase()}</span>
          <span className={`estado estado-${scrim.estado.toLowerCase()}`}>{estadoLabel}</span>
          {esOrganizador && <span className="badge badge-ok">Sos el organizador</span>}
        </div>
        <h1>{formatRegion(scrim.region)}</h1>
        <p className="muted">
          {new Date(scrim.fechaHora).toLocaleString('es-AR')}
          {' · '}
          {scrim.rangoMinMmr}–{scrim.rangoMaxMmr} MMR
          {' · '}
          ≤ {scrim.latenciaMaxMs} ms
        </p>
      </header>

      {info && <div className="alert alert-success" role="status">{info}</div>}
      {error && <div className="alert alert-error" role="alert">{error}</div>}

      {!scrimActivo && (
        <div className="alert alert-muted">
          Este scrim está <strong>{estadoLabel.toLowerCase()}</strong>. Ya no podés unirte ni gestionarlo.
        </div>
      )}

      {scrimActivo && (
        <div className="scrim-flow-guide">
          <h2 className="section-title">¿Cómo funciona?</h2>
          <ol className="flow-steps">
            {esOrganizador ? (
              <>
                <li>Revisá la lista de postulantes y aceptá a quienes quieras en el equipo.</li>
                <li>Cuando tengas jugadores aceptados, usá <strong>Armar lobby</strong> (matchmaking).</li>
                <li>Los aceptados confirman asistencia y después podés finalizar la partida.</li>
              </>
            ) : (
              <>
                <li>Postulate indicando el rol que querés jugar.</li>
                <li>Esperá a que el organizador te acepte desde su panel.</li>
                <li>Confirmá tu asistencia cuando te acepten.</li>
              </>
            )}
          </ol>
        </div>
      )}

      {scrimActivo && !esOrganizador && (
        <section className="detail-actions">
          <h2 className="section-title">Quiero jugar</h2>

          <ActionCard
            step={1}
            title="Postularme al scrim"
            description="Elegí el rol que querés jugar. El organizador verá tu solicitud en su lista de postulantes."
          >
            <form onSubmit={handlePostular} className="form action-form">
              <label>
                Rol deseado
                <input value={rol} onChange={(e) => setRol(e.target.value)} required />
              </label>
              <button type="submit" className="btn-primary" disabled={loading}>
                {loading ? 'Enviando…' : 'Postularme'}
              </button>
            </form>
            {postulacionId && (
              <p className="postulacion-id-hint">
                Tu postulación: <strong>#{postulacionId}</strong>
              </p>
            )}
          </ActionCard>

          <ActionCard
            step={2}
            title="Confirmar asistencia"
            description="Usá este paso solo después de que el organizador haya aceptado tu postulación."
          >
            <button
              type="button"
              className="btn-secondary"
              disabled={loading}
              onClick={() => void runAction(
                () => api.confirmarParticipacion(id!, usuarioId!),
                'Confirmaste tu asistencia.',
              )}
            >
              Confirmar que voy a jugar
            </button>
          </ActionCard>
        </section>
      )}

      {scrimActivo && esOrganizador && (
        <section className="detail-actions">
          <h2 className="section-title">Panel del organizador</h2>

          <ActionCard
            step={1}
            variant="organizer"
            title="Postulantes"
            description="Jugadores que quieren unirse. Aceptá a quienes formarán parte del equipo."
          >
            {postulaciones.length === 0 ? (
              <p className="muted">Todavía no hay postulantes.</p>
            ) : (
              <ul className="postulantes-list">
                {postulaciones.map((p) => (
                  <li key={p.id} className="postulante-row">
                    <div className="postulante-info">
                      <strong>{p.username}</strong>
                      <span className="muted">
                        {p.rol ? ` · ${p.rol}` : ''}
                        {' · '}
                        {POSTULACION_ESTADO[p.estado] ?? p.estado}
                      </span>
                    </div>
                    {p.estado === 'PENDIENTE' && (
                      <button
                        type="button"
                        className="btn-primary btn-sm"
                        disabled={loading}
                        onClick={() => aceptarJugador(p)}
                      >
                        Aceptar
                      </button>
                    )}
                  </li>
                ))}
              </ul>
            )}
          </ActionCard>

          <ActionCard
            step={2}
            variant="organizer"
            title="Armar lobby (matchmaking)"
            description="El matchmaking toma a los jugadores ya aceptados y arma el lobby (patrón Strategy: MMR, latencia, etc.). El estado del scrim pasa a «Lobby armado»."
          >
            <p className="lobby-stats muted">
              {postulantesAceptados} aceptado(s) · {postulantesPendientes} pendiente(s)
            </p>

            {lobbyYaArmado ? (
              <p className="postulacion-id-hint">
                Lobby armado. Estado actual: <strong>{estadoLabel}</strong>.
              </p>
            ) : (
              <>
                <div className="button-row">
                  <button
                    type="button"
                    className="btn-primary"
                    disabled={loading || !puedeArmarLobby}
                    onClick={() => void runAction(
                      () => api.ejecutarMatchmaking(id!),
                      'Lobby armado. El estado del scrim se actualizó.',
                    )}
                  >
                    {loading ? 'Armando…' : 'Armar lobby'}
                  </button>
                  <button
                    type="button"
                    className="btn-secondary"
                    disabled={loading}
                    onClick={() => void runAction(
                      () => api.finalizarScrim(id!, [{
                        usuarioId: usuarioId!,
                        esMvp: true,
                        kills: 20,
                        deaths: 10,
                        assists: 5,
                        observaciones: 'Scrim finalizado',
                      }]),
                      'Scrim finalizado.',
                    )}
                  >
                    Finalizar partida
                  </button>
                </div>
                {!puedeArmarLobby && scrim.estado === 'BUSCANDO' && (
                  <p className="action-hint">
                    {postulantesAceptados === 0
                      ? 'Primero aceptá al menos un postulante de la lista de arriba.'
                      : 'No se puede armar el lobby en este momento.'}
                  </p>
                )}
              </>
            )}
          </ActionCard>

          <ActionCard
            step={3}
            variant="danger"
            title="Cancelar scrim"
            description="Solo si la partida no se va a jugar. Esta acción no se puede deshacer."
          >
            <form
              onSubmit={(e) => {
                e.preventDefault();
                void runAction(
                  () => api.cancelarScrim(id!, motivoCancelar),
                  'Scrim cancelado.',
                );
              }}
              className="form action-form"
            >
              <label>
                Motivo
                <input
                  value={motivoCancelar}
                  onChange={(e) => setMotivoCancelar(e.target.value)}
                  placeholder="Ej. faltan jugadores"
                  required
                />
              </label>
              <button type="submit" className="btn-ghost danger" disabled={loading}>
                Cancelar scrim
              </button>
            </form>
          </ActionCard>
        </section>
      )}

      <details className="report-details">
        <summary>Reportar un problema</summary>
        <p className="muted">
          Usá esto para denunciar conducta tóxica, cheats u otro incidente relacionado con este scrim.
        </p>
        <form
          onSubmit={(e) => {
            e.preventDefault();
            void runAction(async () => {
              await api.crearReporte({
                motivo: motivoReporte,
                scrimId: id,
                reportanteId: usuarioId ?? undefined,
              });
              setMotivoReporte('');
            }, 'Reporte enviado. Gracias.');
          }}
          className="form action-form"
        >
          <label>
            Describí el problema
            <input
              value={motivoReporte}
              onChange={(e) => setMotivoReporte(e.target.value)}
              placeholder="Ej. spam, cheat, no-show…"
              required
            />
          </label>
          <button type="submit" className="btn-ghost" disabled={loading}>
            Enviar reporte
          </button>
        </form>
      </details>
    </div>
  );
}

import type {
  AuthResponse,
  BusquedaFavorita,
  BusquedaFavoritaPayload,
  CreateScrimPayload,
  EstadisticaPayload,
  OAuthLoginPayload,
  OAuthUrlResponse,
  PostulacionPayload,
  ReportePayload,
  Scrim,
  UpdateProfilePayload,
  UsuarioProfile,
} from '../types';

export class ApiError extends Error {
  constructor(
    message: string,
    readonly status: number,
  ) {
    super(message);
    this.name = 'ApiError';
  }

  isUsuarioNoEncontrado(): boolean {
    return this.message.includes('Usuario no encontrado');
  }
}

async function parseError(response: Response): Promise<ApiError> {
  const text = await response.text();
  try {
    const json = JSON.parse(text) as { error?: string; message?: string };
    const message = json.error ?? json.message ?? text;
    return new ApiError(message, response.status);
  } catch {
    return new ApiError(text || `Error ${response.status}`, response.status);
  }
}

const BASE = '/api';

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const response = await fetch(`${BASE}${path}`, {
    headers: { 'Content-Type': 'application/json', ...options?.headers },
    ...options,
  });
  if (!response.ok) {
    throw await parseError(response);
  }
  if (response.status === 204) {
    return undefined as T;
  }
  const text = await response.text();
  if (!text) {
    return undefined as T;
  }
  return JSON.parse(text) as T;
}

export const api = {
  register: (body: { username: string; email: string; password: string }) =>
    request<AuthResponse>('/auth/register', { method: 'POST', body: JSON.stringify(body) }),

  login: (body: { email: string; password: string }) =>
    request<AuthResponse>('/auth/login', { method: 'POST', body: JSON.stringify(body) }),

  getOAuthUrl: (proveedor: string) =>
    request<OAuthUrlResponse>(`/auth/oauth/${proveedor}/url`),

  loginOAuth: (body: OAuthLoginPayload) =>
    request<AuthResponse>('/auth/oauth/login', { method: 'POST', body: JSON.stringify(body) }),

  getProfile: (id: string) =>
    request<UsuarioProfile>(`/usuarios/${id}`),

  updateProfile: (id: string, body: UpdateProfilePayload) =>
    request<UsuarioProfile>(`/usuarios/${id}/perfil`, {
      method: 'PUT',
      body: JSON.stringify(body),
    }),

  verifyEmail: (id: string) =>
    request<void>(`/usuarios/${id}/verificar-email`, { method: 'POST' }),

  listScrims: (params?: Record<string, string>) => {
    const query = params ? `?${new URLSearchParams(params).toString()}` : '';
    return request<Scrim[]>(`/scrims${query}`);
  },

  getScrim: (id: string) =>
    request<Scrim>(`/scrims/${id}`),

  createScrim: (body: CreateScrimPayload) =>
    request<{ id: string }>('/scrims', { method: 'POST', body: JSON.stringify(body) }),

  listPostulaciones: (scrimId: string) =>
    request<import('../types').Postulacion[]>(`/scrims/${scrimId}/postulaciones`),

  postular: (scrimId: string, body: PostulacionPayload) =>
    request<{ postulacionId: number }>(`/scrims/${scrimId}/postulaciones`, {
      method: 'POST',
      body: JSON.stringify(body),
    }),

  aceptarPostulacion: (scrimId: string, postulacionId: number) =>
    request<void>(`/scrims/${scrimId}/postulaciones/aceptar`, {
      method: 'POST',
      body: JSON.stringify({ postulacionId }),
    }),

  confirmarParticipacion: (scrimId: string, usuarioId: string) =>
    request<void>(`/scrims/${scrimId}/confirmaciones`, {
      method: 'POST',
      body: JSON.stringify({ usuarioId }),
    }),

  ejecutarMatchmaking: (scrimId: string) =>
    request<void>(`/scrims/${scrimId}/matchmaking`, { method: 'POST' }),

  cancelarScrim: (scrimId: string, motivo: string) =>
    request<void>(`/scrims/${scrimId}/cancelar`, {
      method: 'POST',
      body: JSON.stringify({ motivo }),
    }),

  finalizarScrim: (scrimId: string, estadisticas: EstadisticaPayload[]) =>
    request<void>(`/scrims/${scrimId}/finalizar`, {
      method: 'POST',
      body: JSON.stringify(estadisticas),
    }),

  listBusquedasFavoritas: (usuarioId: string) =>
    request<BusquedaFavorita[]>(`/usuarios/${usuarioId}/busquedas-favoritas`),

  saveBusquedaFavorita: (usuarioId: string, body: BusquedaFavoritaPayload) =>
    request<BusquedaFavorita>(`/usuarios/${usuarioId}/busquedas-favoritas`, {
      method: 'POST',
      body: JSON.stringify(body),
    }),

  activarAlertaBusqueda: (usuarioId: string, busquedaId: string) =>
    request<BusquedaFavorita>(
      `/usuarios/${usuarioId}/busquedas-favoritas/${busquedaId}/activar-alerta`,
      { method: 'POST' },
    ),

  desactivarAlertaBusqueda: (usuarioId: string, busquedaId: string) =>
    request<BusquedaFavorita>(
      `/usuarios/${usuarioId}/busquedas-favoritas/${busquedaId}/desactivar-alerta`,
      { method: 'POST' },
    ),

  crearReporte: (body: ReportePayload) =>
    request<{ reporteId: number }>('/reportes', { method: 'POST', body: JSON.stringify(body) }),
};

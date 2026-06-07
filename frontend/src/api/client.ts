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
  return response.json() as Promise<T>;
}

export const api = {
  register: (body: { username: string; email: string; password: string }) =>
    request<{ usuarioId: string }>('/auth/register', { method: 'POST', body: JSON.stringify(body) }),

  login: (body: { email: string; password: string }) =>
    request<{ usuarioId: string }>('/auth/login', { method: 'POST', body: JSON.stringify(body) }),

  listScrims: (params?: Record<string, string>) => {
    const query = params ? '?' + new URLSearchParams(params).toString() : '';
    return request<import('../types').Scrim[]>(`/scrims${query}`);
  },

  getScrim: (id: string) => request<import('../types').Scrim>(`/scrims/${id}`),

  createScrim: (body: import('../types').CreateScrimPayload) =>
    request<{ id: string }>('/scrims', { method: 'POST', body: JSON.stringify(body) }),

  getProfile: (id: string) => request<import('../types').UsuarioProfile>(`/usuarios/${id}`),

  cancelScrim: (id: string, motivo: string) =>
    request<void>(`/scrims/${id}/cancelar`, {
      method: 'POST',
      body: JSON.stringify({ motivo }),
    }),
};

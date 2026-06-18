export interface AuthResponse {
  usuarioId: string;
}

export interface OAuthUrlResponse {
  proveedor: string;
  authUrl: string;
}

export interface OAuthLoginPayload {
  proveedor: string;
  externalId: string;
  email: string;
  username: string;
}

export interface Scrim {
  id: string;
  juego: string;
  estado: string;
  region: string;
  rangoMinMmr: number;
  rangoMaxMmr: number;
  latenciaMaxMs: number;
  fechaHora: string;
  organizadorId: string;
}

export interface RangoPayload {
  tier: string;
  numerico: number;
}

export interface Postulacion {
  id: number;
  usuarioId: string;
  username: string;
  rol: string;
  estado: 'PENDIENTE' | 'ACEPTADA' | 'RECHAZADA';
}

export interface CreateScrimPayload {
  juego: string;
  servidor: string;
  zona: string;
  rangoMin: RangoPayload;
  rangoMax: RangoPayload;
  latenciaMaxMs: number;
  fechaHora: string;
  organizadorId: string;
}

export interface UsuarioProfile {
  id: string;
  username: string;
  email: string;
  verificado: boolean;
}

export interface UpdateProfilePayload {
  username: string;
}

export interface PostulacionPayload {
  usuarioId: string;
  juego: string;
  rol: string;
}

export interface EstadisticaPayload {
  usuarioId: string;
  esMvp: boolean;
  kills: number;
  deaths: number;
  assists: number;
  observaciones?: string;
}

export interface ReportePayload {
  motivo: string;
  scrimId?: string;
  reportanteId?: string;
  reportadoId?: string;
}

export interface BusquedaFavorita {
  id: string;
  usuarioId: string;
  juego: string;
  rangoMin?: number;
  rangoMax?: number;
  region?: string;
  rolBuscado?: string;
  alertaActiva: boolean;
}

export interface BusquedaFavoritaPayload {
  juego: string;
  rangoMin?: number;
  rangoMax?: number;
  servidor: string;
  zona: string;
  rolBuscado?: string;
  activarAlerta: boolean;
}

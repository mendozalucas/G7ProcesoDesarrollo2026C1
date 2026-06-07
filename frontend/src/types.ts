export interface AuthResponse {
  usuarioId: string;
}

export interface Scrim {
  id: string;
  juego: string;
  estado: string;
  jugadoresPorLado: number;
  servidor: string;
  zona: string;
  rangoMin: number;
  rangoMax: number;
  latenciaMaxMs: number;
  fechaHora: string;
  duracionMinutos: number;
  modalidad: string;
  organizadorId: string;
  cuposDisponibles: number;
  participantes: string[];
}

export interface UsuarioProfile {
  id: string;
  username: string;
  email: string;
  verificado: boolean;
  rol: string;
  strikes: number;
  perfilesJuego: Array<{
    juego: string;
    servidor?: string;
    zona?: string;
    mmr?: number;
    rolesPreferidos: string[];
  }>;
  proveedoresOAuth: string[];
}

export interface CreateScrimPayload {
  juego: string;
  jugadoresPorLado: number;
  servidor: string;
  zona: string;
  rangoMin: { juego: string; tier: string; numerico: number };
  rangoMax: { juego: string; tier: string; numerico: number };
  latenciaMaxMs: number;
  fechaHora: string;
  duracionMinutos: number;
  modalidadNombre: string;
  organizadorId: string;
}

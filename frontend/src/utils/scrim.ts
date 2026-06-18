import type { ModalidadScrim } from '../types';

export function parseRegion(region: string): { servidor: string; zona: string } {
  const slash = region.indexOf('/');
  if (slash >= 0) {
    return { servidor: region.slice(0, slash), zona: region.slice(slash + 1) };
  }
  return { servidor: region, zona: '' };
}

export function formatRegion(region: string): string {
  const { servidor, zona } = parseRegion(region);
  return zona ? `${servidor}/${zona}` : servidor;
}

export function formatFormato(jugadoresPorLado: number, formato?: string): string {
  if (formato) return formato;
  return `${jugadoresPorLado}v${jugadoresPorLado}`;
}

export const MODALIDAD_LABEL: Record<ModalidadScrim | string, string> = {
  CASUAL: 'Casual',
  RANKED_LIKE: 'Ranked-like',
  RANKED: 'Ranked-like',
  PRACTICA_ESTRATOS: 'Práctica por estratos',
  PRACTICA: 'Práctica por estratos',
};

export function formatModalidad(modalidad: string): string {
  return MODALIDAD_LABEL[modalidad] ?? modalidad.replaceAll('_', ' ').toLowerCase();
}

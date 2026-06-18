import type { NavigateFunction } from 'react-router-dom';
import { ApiError } from '../api/client';

export function isSessionInvalid(err: unknown): boolean {
  return err instanceof ApiError && (
    err.message.includes('Usuario no encontrado')
    || err.message.includes('Organizador no encontrado')
  );
}

export function redirectToLogin(
  logout: () => void,
  navigate: NavigateFunction,
  message: string,
): void {
  logout();
  navigate('/login', { replace: true, state: { message } });
}

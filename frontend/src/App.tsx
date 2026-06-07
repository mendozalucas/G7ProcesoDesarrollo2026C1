import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { Layout } from './components/Layout';
import { ProtectedRoute } from './components/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import { CreateScrimPage } from './pages/CreateScrimPage';
import { HomePage } from './pages/HomePage';
import { LoginPage } from './pages/LoginPage';
import { ProfilePage } from './pages/ProfilePage';
import { RegisterPage } from './pages/RegisterPage';
import { ScrimDetailPage } from './pages/ScrimDetailPage';
import { ScrimsPage } from './pages/ScrimsPage';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
            <Route index element={<HomePage />} />
            <Route path="login" element={<LoginPage />} />
            <Route path="register" element={<RegisterPage />} />
            <Route
              path="scrims"
              element={
                <ProtectedRoute>
                  <ScrimsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="scrims/nuevo"
              element={
                <ProtectedRoute>
                  <CreateScrimPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="scrims/:id"
              element={
                <ProtectedRoute>
                  <ScrimDetailPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="perfil"
              element={
                <ProtectedRoute>
                  <ProfilePage />
                </ProtectedRoute>
              }
            />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

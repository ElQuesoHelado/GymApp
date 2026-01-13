import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';

// Importa tus Layouts
import LoginPage from '../auth/LoginPage';
import EntrenadorLayout from '../layouts/EntrenadorLayout';

// Importa tus Páginas (Asegúrate que estas rutas sean correctas)
import EntrenadorHome from '../pages/entrenador/EntrenadorHome';
import PlanesPage from '../pages/entrenador/PlanesPage';
import RutinasPage from '../pages/entrenador/RutinasPage';
import SesionesPage from '../pages/entrenador/SesionesPage';

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Ruta Login */}
        <Route path="/" element={<LoginPage />} />

        {/* --- RUTA ENTRENADOR --- */}
        <Route path="/entrenador" element={<EntrenadorLayout />}>
          
          <Route index element={<EntrenadorHome />} />
          
          <Route path="planes" element={<PlanesPage />} />
          <Route path="rutinas" element={<RutinasPage />} />
          <Route path="sesiones" element={<SesionesPage />} />
          
        </Route>
        
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
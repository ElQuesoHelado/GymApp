import { BrowserRouter, Routes, Route } from "react-router-dom";

import LoginPage from "../pages/LoginPage"; 
import EntrenadorLayout from "../layouts/EntrenadorLayout";
import EntrenadorHome from "../pages/entrenador/EntrenadorHome"; 

import PlanesPage from "../pages/entrenador/PlanesPage";
import RutinasPage from "../pages/entrenador/RutinasPage";
import SesionesPage from "../pages/entrenador/SesionesPage"; 

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Ruta Login */}
        <Route path="/" element={<LoginPage />} />
        <Route path="/login" element={<LoginPage />} />

        {/* Rutas Entrenador */}
        <Route path="/entrenador" element={<EntrenadorLayout />}>
          
          <Route index element={<EntrenadorHome />} />
          
          <Route path="planes" element={<PlanesPage />} />
          <Route path="rutinas" element={<RutinasPage />} />
          <Route path="sesiones" element={<SesionesPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
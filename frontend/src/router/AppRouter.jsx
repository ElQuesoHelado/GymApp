import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "../pages/LoginPage";

import ClienteLayout from "../layouts/ClienteLayout";
import ClienteHome from "../pages/cliente/ClienteHome";
import Membresia from "../pages/cliente/Membresia";
import PlanesCliente from "../pages/cliente/PlanesCliente";
import Notificaciones from "../pages/Notificaciones";

import EntrenadorLayout from "../layouts/EntrenadorLayout";
import EntrenadorHome from "../pages/entrenador/EntrenadorHome";
import ClientesAsignados from "../pages/entrenador/ClientesAsignados.jsx";
import PlanesEntrenador from "../pages/entrenador/PlanesEntrenador";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />

        <Route path="/cliente" element={<ClienteLayout />}>
          <Route index element={<ClienteHome />} />
          <Route path="membresia" element={<Membresia />} />
          <Route path="planes" element={<PlanesCliente />} />
          <Route path="notificaciones" element={<Notificaciones />} />
        </Route>

        <Route path="/entrenador" element={<EntrenadorLayout />}>
          <Route index element={<EntrenadorHome />} />
          <Route path="clientes" element={<ClientesAsignados />} />
          <Route path="planes" element={<PlanesEntrenador />} />
          <Route path="notificaciones" element={<Notificaciones />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}


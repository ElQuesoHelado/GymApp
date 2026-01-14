import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardRedirect from "./pages/DashboardRedirect";
import RequireRole from "./auth/RequireRole";
import ClienteLayout from "./layouts/ClienteLayout";
import EntrenadorLayout from "./layouts/EntrenadorLayout";
import ClienteHome from "./pages/cliente/Home";

import EntrenadorHome from "./pages/entrenador/EntrenadorHome";
import Membresia from "./pages/cliente/Membresia";
import PlanesCliente from "./pages/cliente/Plan";
import Sesiones from "./pages/Sesiones"
import Notificaciones from "./pages/Notificaciones";
import ClientesAsignados from "./pages/entrenador/ClientesAsignados.jsx";
import PlanesEntrenador from "./pages/entrenador/Planes";
import RutinasPage from "./pages/entrenador/Rutinas";
import SesionesPage from "./pages/entrenador/Sesiones";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/dashboard" element={<DashboardRedirect />} />

      <Route
        path="/dashboard/cliente/*"
        element={
          <RequireRole allowedRoles={["CLIENTE"]}>
            <ClienteLayout />
          </RequireRole>
        }
      >
        <Route index element={<ClienteHome />} />
        <Route path="membresia" element={<Membresia />} />
        <Route path="planes" element={<PlanesCliente />} />
        <Route path="sesiones" element={<Sesiones />} />
        <Route path="notificaciones" element={<Notificaciones />} />
      </Route>

      <Route
        path="/dashboard/entrenador/*"
        element={
          <RequireRole allowedRoles={["ENTRENADOR"]}>
            <EntrenadorLayout />
          </RequireRole>
        }
      >
        <Route index element={<EntrenadorHome />} />

        <Route path="planes" element={<PlanesEntrenador />} />
        <Route path="rutinas" element={<RutinasPage />} />
        <Route path="sesiones" element={<SesionesPage />} />

        <Route path="clientes" element={<ClientesAsignados />} />
        <Route path="notificaciones" element={<Notificaciones />} />
      </Route>
    </Routes>
  );
}

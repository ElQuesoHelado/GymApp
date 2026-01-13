import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardRedirect from "./pages/DashboardRedirect";
import RequireRole from "./auth/RequireRole";
import ClienteLayout from "./layouts/ClienteLayout";
import EntrenadorLayout from "./layouts/EntrenadorLayout";
import ClienteHome from "./pages/cliente/Home";
import EntrenadorHome from "./pages/entrenador/Home";
import Membresia from "./pages/cliente/Membresia";
import PlanesCliente from "./pages/cliente/Plan";
import Notificaciones from "./pages/Notificaciones";
import ClientesAsignados from "./pages/entrenador/ClientesAsignados.jsx";
import PlanesEntrenador from "./pages/entrenador/Planes";

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
        <Route path="clientes" element={<ClientesAsignados />} />
        <Route path="planes" element={<PlanesEntrenador />} />
        <Route path="notificaciones" element={<Notificaciones />} />
      </Route>
    </Routes>
  );
}

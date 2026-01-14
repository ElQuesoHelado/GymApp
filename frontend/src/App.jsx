import { Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DashboardRedirect from "./pages/DashboardRedirect";
import RequireRole from "./auth/RequireRole";
import ClienteLayout from "./layouts/ClienteLayout";
import EntrenadorLayout from "./layouts/EntrenadorLayout";
import ClienteHome from "./pages/cliente/Home";
import EntrenadorHome from "./pages/entrenador/EntrenadorHome";

import PlanesPage from "./pages/entrenador/PlanesPage";
import RutinasPage from "./pages/entrenador/RutinasPage";
import SesionesPage from "./pages/entrenador/SesionesPage";

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

        <Route path="planes" element={<PlanesPage />} />
        <Route path="rutinas" element={<RutinasPage />} />
        <Route path="sesiones" element={<SesionesPage />} />
      </Route>
    </Routes>
  );
}


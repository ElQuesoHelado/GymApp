import { Routes, Route, Outlet } from "react-router-dom";
import ClienteHome from "../pages/cliente/ClienteHome";
import MiPlan from "../pages/cliente/MiPlan";
import MisSesiones from "../pages/cliente/MisSesiones";
import Notificaciones from "../pages/cliente/Notificaciones";

export default function ClienteLayout() {
  return (
    <div>
      <h2>Zona Cliente</h2>

      <Routes>
        <Route index element={<ClienteHome />} />
        <Route path="plan" element={<MiPlan />} />
        <Route path="sesiones" element={<MisSesiones />} />
        <Route path="notificaciones" element={<Notificaciones />} />
      </Routes>
    </div>
  );
}

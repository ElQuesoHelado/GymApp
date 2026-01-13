import { NavLink, Outlet } from "react-router-dom";

export default function ClienteLayout() {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <aside style={{ width: 200, padding: 20 }}>
        <NavLink to="/dashboard/cliente" end>Inicio</NavLink><br />
        <NavLink to="/dashboard/cliente/membresia">Membresía</NavLink><br />
        <NavLink to="/dashboard/cliente/planes">Planes</NavLink><br />
        <NavLink to="/dashboard/cliente/notificaciones">Notificaciones</NavLink>
      </aside>

      <main style={{ flex: 1, padding: 20 }}>
        <Outlet />
      </main>
    </div>
  );
}


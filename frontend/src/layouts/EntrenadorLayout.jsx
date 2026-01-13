import { NavLink, Outlet } from "react-router-dom";

export default function EntrenadorLayout() {
  return (
    <div style={{ display: "flex" }}>
      <aside style={{ width: 200 }}>
        <NavLink to="">Inicio</NavLink><br />
        <NavLink to="clientes">Clientes</NavLink><br />
        <NavLink to="planes">Planes</NavLink><br />
        <NavLink to="notificaciones">Notificaciones</NavLink>
      </aside>

      <main style={{ padding: 20 }}>
        <Outlet />
      </main>
    </div>
  );
}

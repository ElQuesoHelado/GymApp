import { Outlet, NavLink } from "react-router-dom";

export default function EntrenadorLayout() {
  return (
    <div style={{ display: "flex", minHeight: '100vh', background: '#1a1a1a', color: 'white' }}>

      <aside style={{ width: 200, padding: '20px', background: '#111', borderRight: '1px solid #333' }}>
        <h3 style={{ marginTop: 0 }}>Menu</h3>
        <nav style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
            <NavLink to="/entrenador" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Inicio</NavLink>
            <NavLink to="clientes" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Clientes</NavLink>
            <NavLink to="planes" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Planes</NavLink>
            <NavLink to="rutinas" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Rutinas</NavLink>
            <NavLink to="sesiones" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Sesiones</NavLink>
            <NavLink to="notificaciones" style={({isActive}) => ({ color: isActive ? '#007bff' : 'white', textDecoration: 'none' })}>Notificaciones</NavLink>
        </nav>
      </aside>

      <div style={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
        <main style={{ padding: 20, flex: 1 }}>
          <Outlet />
        </main>

        <footer style={{ background: '#111', padding: '10px', textAlign: 'center', color: '#888', borderTop: '1px solid #333' }}>
          <p style={{ margin: 0, fontSize: '0.9rem' }}>Sistema GymApp © 2026</p>
        </footer>
      </div>
    </div>
  );
}
import { Outlet, NavLink, Link } from "react-router-dom";

export default function EntrenadorLayout() {
  
  const navLinkClass = ({ isActive }) =>
    `flex items-center gap-3 px-4 py-3 rounded-xl transition-all duration-200 font-medium text-sm ${
      isActive
        ? "bg-blue-600 text-white shadow-lg shadow-blue-500/30"
        : "text-gray-400 hover:bg-white/5 hover:text-white"
    }`;

  return (
    <div className="flex min-h-screen bg-gray-900 text-white font-sans">
      
      <aside className="w-64 bg-[#111827] border-r border-gray-800 flex flex-col fixed h-full z-10 top-0 left-0">
        
        {/* Header del Menú */}
        <div className="p-6 border-b border-gray-800">
          <h2 className="text-2xl font-bold bg-gradient-to-r from-blue-400 to-indigo-500 bg-clip-text text-transparent">
            GymApp
          </h2>
          <p className="text-xs text-gray-500 mt-1">Panel de Entrenador</p>
        </div>

        {/* Navegación */}
        <nav className="flex-1 p-4 space-y-2 overflow-y-auto">
          
          <p className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-2 px-4 mt-4">Principal</p>
          
          
          <NavLink to="/dashboard/entrenador" end className={navLinkClass}>
            <span></span> Inicio
          </NavLink>
          
          <NavLink to="/dashboard/entrenador/clientes" className={navLinkClass}>
            <span></span> Mis Alumnos
          </NavLink>

          <p className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-2 px-4 mt-6">Gestión</p>

          <NavLink to="/dashboard/entrenador/planes" className={navLinkClass}>
            <span></span> Planes
          </NavLink>
          
          <NavLink to="/dashboard/entrenador/rutinas" className={navLinkClass}>
            <span></span> Rutinas
          </NavLink>
          
          <NavLink to="/dashboard/entrenador/sesiones" className={navLinkClass}>
            <span></span> Sesiones
          </NavLink>

          <p className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-2 px-4 mt-6">Cuenta</p>

          <NavLink to="/dashboard/entrenador/notificaciones" className={navLinkClass}>
            <span></span> Notificaciones
          </NavLink>
        </nav>

        <div className="p-4 border-t border-gray-800">
          <Link to="/" className="flex items-center gap-2 text-red-400 hover:text-red-300 transition-colors text-sm px-4">
            <span></span> Cerrar Sesión
          </Link>
        </div>
      </aside>

      <div className="flex-1 flex flex-col ml-64 bg-gray-50 min-h-screen">
        <main className="flex-1">
          <Outlet />
        </main>
        
        <footer className="bg-white border-t border-gray-200 py-4 text-center text-gray-500 text-xs">
          <p>© 2026 GymApp - Sistema de Gestión Deportiva</p>
        </footer>
      </div>
    </div>
  );
}
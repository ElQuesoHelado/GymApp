import { NavLink, Outlet } from 'react-router-dom';

export default function DashboardLayout({ menu }) {
  return (
    <div className="dashboard">
      <aside className="sidebar">
        {menu.map(item => (
          <NavLink key={item.path} to={item.path}>
            {item.label}
          </NavLink>
        ))}
      </aside>

      <main className="content">
        <Outlet />
      </main>
    </div>
  );
}

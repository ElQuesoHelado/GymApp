import React from 'react';
import { Outlet } from 'react-router-dom';

const EntrenadorLayout = () => {
  return (
    <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>

        <nav style={{ background: '#2c3e50', padding: '15px', color: 'white' }}>
            <h2>Gimnasio - Panel Entrenador</h2>
        </nav>

        <main style={{ flex: 1, padding: '20px' }}>
            <Outlet />
        </main>
      
        <footer style={{ background: '#1a1a1a', padding: '10px', textAlign: 'center', color: '#888', borderTop: '1px solid #333' }}>
            <p style={{ margin: 0, fontSize: '0.9rem' }}>Sistema GymApp © 2026</p>
        </footer>
    </div>
  );
};

export default EntrenadorLayout;
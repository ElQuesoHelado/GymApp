import React from 'react';
import { Link } from 'react-router-dom';

const EntrenadorHome = () => {
  return (
    <div style={{ color: 'white' }}>
      <h1>Panel de Control del Entrenador</h1>
      <p>Bienvenido al sistema. Selecciona una opción para comenzar:</p>
      
      <div style={{ display: 'flex', gap: '20px', marginTop: '30px' }}>
        {/* Tarjeta para Planes */}
        <Link to="/entrenador/planes" style={{ textDecoration: 'none' }}>
          <div style={{ 
            border: '1px solid #444', 
            padding: '30px', 
            borderRadius: '8px', 
            background: '#222', 
            color: 'white',
            width: '200px',
            textAlign: 'center',
            cursor: 'pointer'
          }}>
            <h3>📋 Planes</h3>
            <p>Crear y asignar planes</p>
          </div>
        </Link>

        {/* Tarjeta para Rutinas */}
        <Link to="/entrenador/rutinas" style={{ textDecoration: 'none' }}>
          <div style={{ 
             border: '1px solid #444', 
             padding: '30px', 
             borderRadius: '8px', 
             background: '#222', 
             color: 'white',
             width: '200px',
             textAlign: 'center',
             cursor: 'pointer'
          }}>
            <h3>💪 Rutinas</h3>
            <p>Gestionar ejercicios</p>
          </div>
        </Link>

        {/* Tarjeta para Sesiones */}
        <Link to="/entrenador/sesiones" style={{ textDecoration: 'none' }}>
          <div style={{ 
             border: '1px solid #444', 
             padding: '30px', 
             borderRadius: '8px', 
             background: '#222', 
             color: 'white',
             width: '200px',
             textAlign: 'center',
             cursor: 'pointer'
          }}>
            <h3>⏱️ Sesiones</h3>
            <p>Ver horarios y clases</p>
          </div>
        </Link>
      </div>
    </div>
  );
};

export default EntrenadorHome;
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
// Asegúrate de que esta ruta sea la correcta hacia tu archivo API
import { getEntrenadorHome } from "../../api/entrenador.api"; 

export default function EntrenadorHome() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  const fallbackData = {
    nombre: "Entrenador",
    email: "usuario@gymapp.com",
    dni: "---",
    tipo: "ENTRENADOR",
    rol: "Staff"
  };

  useEffect(() => {
    let montado = true;

    const tiempoEspera = setTimeout(() => {
      if (montado && loading) {
        console.warn("La API tardó mucho, usando modo offline.");
        setData(fallbackData);
        setLoading(false);
      }
    }, 800);

    getEntrenadorHome()
      .then(res => {
        if (montado) {
          clearTimeout(tiempoEspera);
          setData(res.data);
          setLoading(false);
        }
      })
      .catch(err => {
        console.warn("Fallo en API, usando respaldo.", err);
        if (montado) {
          clearTimeout(tiempoEspera);
          setData(fallbackData);
          setLoading(false);
        }
      });

    return () => {
      montado = false;
      clearTimeout(tiempoEspera);
    };
  }, []);

  if (loading) return (
    <div style={{color:'white', padding: 20, textAlign: 'center'}}>
      <p>Cargando panel...</p>
    </div>
  );

  return (
    <div style={{ color: 'white' }}>
      
      {/* SECCIÓN PERFIL */}
      <div style={{ 
        background: '#222', 
        padding: '20px', 
        borderRadius: '8px', 
        marginBottom: '30px',
        borderLeft: '5px solid #007bff'
      }}>
        <h2 style={{ margin: '0 0 10px 0' }}>Bienvenido, {data ? data.nombre : 'Entrenador'}</h2>
        <div style={{ display: 'flex', gap: '20px', color: '#aaa', flexWrap: 'wrap' }}>
            <span>📧 {data ? data.email : '...'}</span>
            <span>🪪 DNI: {data ? data.dni : '...'}</span>
        </div>
      </div>

      <h3 style={{ borderBottom: '1px solid #444', paddingBottom: '10px' }}>Panel de Control</h3>
      
      <div style={{ display: 'flex', gap: '20px', flexWrap: 'wrap', marginTop: '20px' }}>
        
        <Link to="/dashboard/entrenador/planes" style={{ textDecoration: 'none' }}>
          <div style={cardStyle}>
            <div style={{ fontSize: '2rem', marginBottom: '10px' }}>📋</div>
            <h3 style={{ margin: '0 0 10px 0', color: '#fff' }}>Planes</h3>
          </div>
        </Link>

        <Link to="/dashboard/entrenador/rutinas" style={{ textDecoration: 'none' }}>
          <div style={cardStyle}>
            <div style={{ fontSize: '2rem', marginBottom: '10px' }}>💪</div>
            <h3 style={{ margin: '0 0 10px 0', color: '#fff' }}>Rutinas</h3>
          </div>
        </Link>

        <Link to="/dashboard/entrenador/sesiones" style={{ textDecoration: 'none' }}>
          <div style={cardStyle}>
            <div style={{ fontSize: '2rem', marginBottom: '10px' }}>⏱️</div>
            <h3 style={{ margin: '0 0 10px 0', color: '#fff' }}>Sesiones</h3>
          </div>
        </Link>

      </div>
    </div>
  );
}

const cardStyle = {
  border: '1px solid #444', 
  padding: '30px', 
  borderRadius: '8px', 
  background: '#1a1a1a', 
  width: '200px',
  textAlign: 'center',
  cursor: 'pointer',
  transition: 'transform 0.2s',
  boxShadow: '0 4px 6px rgba(0,0,0,0.3)'
};
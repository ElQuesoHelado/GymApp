import React, { useEffect, useState } from 'react';

import { listarSesionesPorUsuario, crearSesion, cancelarSesion } from '../../api/sesion.api';

export default function SesionesPage() {
  const [sesiones, setSesiones] = useState([]);
  const [nuevaSesion, setNuevaSesion] = useState({ 
    fecha: '', 
    horaInicio: '', 
    horaFin: '',
    usuarioId: 1 
  });

  const USUARIO_TEST_ID = 1;

  useEffect(() => {
    cargarSesiones();
  }, []);


  const formatearFecha = (fechaRaw) => {
    if (!fechaRaw) return 'Fecha pendiente';
    if (Array.isArray(fechaRaw)) {
      return `${fechaRaw[0]}-${fechaRaw[1].toString().padStart(2, '0')}-${fechaRaw[2].toString().padStart(2, '0')}`;
    }
    return fechaRaw;
  };

  const formatearHora = (horaRaw) => {
    if (!horaRaw) return '--:--';


    if (Array.isArray(horaRaw)) {


        return `${horaRaw[0].toString().padStart(2, '0')}:${horaRaw[1].toString().padStart(2, '0')}`;


    }


    return horaRaw;


  }

  const cargarSesiones = async () => {
    try {
      const res = await listarSesionesPorUsuario(USUARIO_TEST_ID);
      console.log("Datos recibidos del backend:", res.data);
      if (Array.isArray(res.data)) {
        setSesiones(res.data);
      } else if (res.data && res.data.data && Array.isArray(res.data.data)) {
        setSesiones(res.data.data);
      } else {
        setSesiones([]);
      }
    } catch (err) {
      console.error("Error cargando sesiones", err);
    }
  };

  const handleChange = (e) => {
    setNuevaSesion({ ...nuevaSesion, [e.target.name]: e.target.value });
  };

  const handleCrear = async (e) => {
    e.preventDefault();

    const sesionParaBackend = {
      estado: "SIN_EMPEZAR", 
      horario: {
        fecha: nuevaSesion.fecha,
        horaInicio: nuevaSesion.horaInicio, 
        horaFin: nuevaSesion.horaFin
      },
      entrenador: {
        id: USUARIO_TEST_ID
      }
    };

    try {
      await crearSesion(sesionParaBackend);
      alert('Sesión programada con éxito');
      setNuevaSesion({ fecha: '', horaInicio: '', horaFin: '', usuarioId: 1 });
      cargarSesiones(); 
    } catch (err) {
      console.error(err);
      alert('Error al programar. Revisa la consola (F12) para ver el error rojo.');
    }
  };
  const handleCancelar = async (id) => {
    if (window.confirm('¿Cancelar esta sesión?')) {
      try {
        await cancelarSesion(id);
        cargarSesiones();
        alert('Sesión cancelada');
      } catch (err) {
        alert('Error al cancelar');
      }
    }
  };
  return (
    <div style={{ color: 'white', padding: '20px' }}>
      <h2>Gestión de Sesiones</h2>
      {/* Formulario */}
      <div style={{ background: '#222', padding: '20px', borderRadius: '8px', marginBottom: '20px' }}>
        <h3>Programar Nueva Clase</h3>
        <form onSubmit={handleCrear} style={{ display: 'flex', gap: '10px', flexWrap: 'wrap', alignItems: 'flex-end' }}>          
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <label style={{ fontSize: '0.8rem', marginBottom: '5px' }}>Fecha</label>
            <input 
              name="fecha" type="date" required 
              value={nuevaSesion.fecha} onChange={handleChange}
              style={{ padding: '8px' }}
            />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <label style={{ fontSize: '0.8rem', marginBottom: '5px' }}>Inicio</label>
            <input 
              name="horaInicio" type="time" required 
              value={nuevaSesion.horaInicio} onChange={handleChange}
              style={{ padding: '8px' }}
            />
          </div>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <label style={{ fontSize: '0.8rem', marginBottom: '5px' }}>Fin</label>
            <input 
              name="horaFin" type="time" required 
              value={nuevaSesion.horaFin} onChange={handleChange}
              style={{ padding: '8px' }}
            />
          </div>
          <button type="submit" style={{ padding: '10px 20px', background: '#007bff', color: 'white', border: 'none', cursor: 'pointer', height: 'fit-content' }}>
            Agendar
          </button>
        </form>
      </div>
      {/* Lista */}
      <div>
        <h3>Mis Sesiones</h3>
        {sesiones.length === 0 ? (
          <p style={{ color: '#888' }}>No tienes sesiones asignadas.</p>) : (
          <ul style={{ listStyle: 'none', padding: 0 }}>
            {sesiones.map((s) => (
              <li key={s.id} style={{ background: '#333', margin: '10px 0', padding: '15px', borderRadius: '5px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <span style={{ fontSize: '1.1rem', fontWeight: 'bold' }}>
                    {s.horario ? formatearFecha(s.horario.fecha) : 'Fecha pendiente'}
                  </span>
                  <br />
                  <span style={{ color: '#ccc' }}>
                     Horario: {s.horario ? `${formatearHora(s.horario.horaInicio)} - ${formatearHora(s.horario.horaFin)}` : '--:--'}
                  </span>
                  <br />
                  <small style={{ color: '#aaa' }}>Estado: {s.estado}</small>
                </div>
                <button onClick={() => handleCancelar(s.id)} style={{ background: '#dc3545', color: 'white', border: 'none', padding: '8px 12px', cursor: 'pointer', borderRadius: '4px' }}>
                  Cancelar
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
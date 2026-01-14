import { useEffect, useState } from 'react';
import { listarRutinas, crearRutina, eliminarRutina } from '../../api/planEntrenamiento.api';

export default function RutinasPage() {
  const [rutinas, setRutinas] = useState([]);
  const [nuevaRutina, setNuevaRutina] = useState({ nombre: '', objetivo: '' });

  useEffect(() => {
    cargarRutinas();
  }, []);

  const cargarRutinas = async () => {
    try {
      const res = await listarRutinas();
      setRutinas(res.data.data || []);
    } catch (err) {
      alert('Error al cargar rutinas');
    }
  };

  const handleChange = e => {
    setNuevaRutina({ ...nuevaRutina, [e.target.name]: e.target.value });
  };

  const handleCrear = async e => {
    e.preventDefault();
    try {
      await crearRutina(nuevaRutina);
      setNuevaRutina({ nombre: '', objetivo: '' });
      cargarRutinas();
    } catch (err) {
      alert('Error al crear rutina');
    }
  };

  const handleEliminar = async id => {
    if (window.confirm('¿Eliminar rutina?')) {
      try {
        await eliminarRutina(id);
        cargarRutinas();
      } catch (err) {
        alert('Error al eliminar rutina');
      }
    }
  };

  return (
    <div>
      <h2>Rutinas</h2>
      <form onSubmit={handleCrear}>
        <input name="nombre" value={nuevaRutina.nombre} onChange={handleChange} placeholder="Nombre" required />
        <input name="objetivo" value={nuevaRutina.objetivo} onChange={handleChange} placeholder="Objetivo" required />
        <button type="submit">Crear Rutina</button>
      </form>
      <ul>
        {rutinas.map(r => (
          <li key={r.id}>
            <b>{r.nombre}</b> - {r.objetivo}
            <button onClick={() => handleEliminar(r.id)}>Eliminar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

import { useEffect, useState } from 'react';
import { listarPlanes, crearPlan, eliminarPlan } from '../../api/planEntrenamiento.api';

export default function PlanesPage() {
  const [planes, setPlanes] = useState([]);
  const [nuevoPlan, setNuevoPlan] = useState({ fechaInicio: '', duracionSemanas: 1 });

  useEffect(() => {
    cargarPlanes();
  }, []);

  const cargarPlanes = async () => {
    try {
      const res = await listarPlanes();
      setPlanes(res.data.data || []);
    } catch (err) {
      alert('Error al cargar planes');
    }
  };

  const handleChange = e => {
    setNuevoPlan({ ...nuevoPlan, [e.target.name]: e.target.value });
  };

  const handleCrear = async e => {
    e.preventDefault();
    try {
      await crearPlan(nuevoPlan);
      setNuevoPlan({ fechaInicio: '', duracionSemanas: 1 });
      cargarPlanes();
    } catch (err) {
      alert('Error al crear plan');
    }
  };

  const handleEliminar = async id => {
    if (window.confirm('¿Eliminar plan?')) {
      try {
        await eliminarPlan(id);
        cargarPlanes();
      } catch (err) {
        alert('Error al eliminar plan');
      }
    }
  };

  return (
    <div>
      <h2>Planes de Entrenamiento</h2>
      <form onSubmit={handleCrear}>
        <input name="fechaInicio" type="date" value={nuevoPlan.fechaInicio} onChange={handleChange} required />
        <input name="duracionSemanas" type="number" min="1" value={nuevoPlan.duracionSemanas} onChange={handleChange} required />
        <button type="submit">Crear Plan</button>
      </form>
      <ul>
        {planes.map(p => (
          <li key={p.id}>
            <b>Inicio:</b> {p.fechaInicio} - <b>Duración:</b> {p.duracionSemanas} semanas
            <button onClick={() => handleEliminar(p.id)}>Eliminar</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

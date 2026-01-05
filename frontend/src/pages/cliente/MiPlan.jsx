import { useEffect, useState } from 'react';
import { listarPlanes } from '../../api/planEntrenamiento.api';

export default function MiPlan() {
  const [plan, setPlan] = useState(null);

  useEffect(() => {
    cargarPlan();
  }, []);

  const cargarPlan = async () => {
    try {
      const res = await listarPlanes();
      // Suponiendo que el cliente solo tiene un plan, tomamos el primero
      setPlan(res.data[0] || null);
    } catch (err) {
      alert('Error al cargar tu plan');
    }
  };

  if (!plan) return <div>No tienes plan de entrenamiento asignado.</div>;

  return (
    <div>
      <h2>Mi Plan de Entrenamiento</h2>
      <p><b>Inicio:</b> {plan.fechaInicio}</p>
      <p><b>Duración:</b> {plan.duracionSemanas} semanas</p>
      <h3>Rutinas</h3>
      <ul>
        {plan.rutinas?.map(r => (
          <li key={r.id}>
            <b>{r.nombre}</b> - {r.objetivo}
          </li>
        ))}
      </ul>
    </div>
  );
}

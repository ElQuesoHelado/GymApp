import { useEffect, useState } from "react";
import { getClientePlan } from "../../api/cliente.api";

export default function Plan() {
  const [plan, setPlan] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getClientePlan()
      .then(res => {
        setPlan(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError("Error al cargar el plan de entrenamiento");
        setLoading(false);
      });
  }, []);

  if (loading) return (
    <div className="plan-loading">
      <p>Cargando plan de entrenamiento...</p>
    </div>
  );

  if (error) return (
    <div className="plan-error">
      <p>{error}</p>
    </div>
  );

  if (!plan) return (
    <div className="plan-no-data">
      <p>No tienes un plan de entrenamiento asignado</p>
    </div>
  );

  // Calcular fecha de fin
  const fechaInicio = new Date(plan.fechaInicio);
  const fechaFin = new Date(fechaInicio);
  fechaFin.setDate(fechaFin.getDate() + (plan.duracionSemanas * 7));

  return (
    <div className="plan-container">
      <div className="plan-header">
        <h2 className="plan-title">Mi Plan de Entrenamiento</h2>
        <div className="plan-info-grid">
          <div className="plan-info-card">
            <h4>Información General</h4>
            <p><strong>Fecha de inicio:</strong> {new Date(plan.fechaInicio).toLocaleDateString()}</p>
            <p><strong>Duración:</strong> {plan.duracionSemanas} semanas</p>
            <p><strong>Fecha estimada de fin:</strong> {fechaFin.toLocaleDateString()}</p>
            <p><strong>ID del plan:</strong> {plan.id}</p>
            {plan.entrenadorId && <p><strong>Entrenador ID:</strong> {plan.entrenadorId}</p>}
          </div>

          <div className="plan-summary-card">
            <h4>Resumen del Plan</h4>
            <p><strong>Total de rutinas:</strong> {plan.rutinas.length}</p>
            <p><strong>Total de ejercicios:</strong> {
              plan.rutinas.reduce((total, rutina) => total + rutina.ejercicios.length, 0)
            }</p>
          </div>
        </div>
      </div>

      <div className="rutinas-section">
        <h3>Rutinas ({plan.rutinas.length})</h3>

        {plan.rutinas.length === 0 ? (
          <p className="no-rutinas">No hay rutinas asignadas en este plan</p>
        ) : (
          <div className="rutinas-grid">
            {plan.rutinas.map((rutina, index) => (
              <div key={rutina.id} className="rutina-card">
                <div className="rutina-header">
                  <h4>Rutina {index + 1}: {rutina.nombre}</h4>
                  <span className="rutina-objetivo">{rutina.objetivo}</span>
                </div>

                <div className="ejercicios-section">
                  <h5>Ejercicios ({rutina.ejercicios.length})</h5>

                  {rutina.ejercicios.length === 0 ? (
                    <p className="no-ejercicios">No hay ejercicios en esta rutina</p>
                  ) : (
                    <div className="ejercicios-list">
                      {rutina.ejercicios.map((ejercicio, ejIndex) => (
                        <div key={ejercicio.id} className="ejercicio-item">
                          <div className="ejercicio-header">
                            <span className="ejercicio-numero">{ejIndex + 1}</span>
                            <span className="ejercicio-nombre">{ejercicio.nombre || `Ejercicio ${ejIndex + 1}`}</span>
                          </div>
                          <div className="ejercicio-detalles">
                            {ejercicio.descripcion && <p><strong>Descripción:</strong> {ejercicio.descripcion}</p>}
                            {ejercicio.series && <p><strong>Series:</strong> {ejercicio.series}</p>}
                            {ejercicio.repeticiones && <p><strong>Repeticiones:</strong> {ejercicio.repeticiones}</p>}
                            {ejercicio.descanso && <p><strong>Descanso:</strong> {ejercicio.descanso}</p>}
                            {ejercicio.maquina && <p><strong>Máquina:</strong> {ejercicio.maquina}</p>}
                            {ejercicio.imagenUrl && (
                              <div className="ejercicio-imagen">
                                <img src={ejercicio.imagenUrl} alt={ejercicio.nombre} />
                              </div>
                            )}
                          </div>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

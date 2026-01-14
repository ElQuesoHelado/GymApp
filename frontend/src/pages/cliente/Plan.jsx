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
    <div className="min-h-screen bg-gray-50 flex items-center justify-center">
      <div className="text-center">
        <div className="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600 mb-4"></div>
        <p className="text-gray-600 text-lg font-medium">Cargando plan de entrenamiento...</p>
      </div>
    </div>
  );

  if (error) return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div className="bg-red-50 border border-red-200 rounded-xl p-8 max-w-md text-center">
        <div className="text-red-500 text-4xl mb-4">⚠️</div>
        <h3 className="text-red-700 font-bold text-xl mb-2">Error</h3>
        <p className="text-red-600 mb-4">{error}</p>
        <button
          onClick={() => window.location.reload()}
          className="bg-red-600 hover:bg-red-700 text-white font-medium py-2 px-4 rounded-lg transition"
        >
          Reintentar
        </button>
      </div>
    </div>
  );

  if (!plan) return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center px-4">
      <div className="bg-yellow-50 border border-yellow-200 rounded-xl p-8 max-w-md text-center">
        <div className="text-yellow-500 text-4xl mb-4">🎯</div>
        <h3 className="text-yellow-700 font-bold text-xl mb-2">Sin Plan Asignado</h3>
        <p className="text-gray-600 mb-4">Actualmente no tienes un plan de entrenamiento asignado.</p>
        <button className="bg-yellow-600 hover:bg-yellow-700 text-white font-medium py-2 px-4 rounded-lg transition">
          Solicitar Plan
        </button>
      </div>
    </div>
  );

  // Calcular fecha de fin
  const fechaInicio = new Date(plan.fechaInicio);
  const fechaFin = new Date(fechaInicio);
  fechaFin.setDate(fechaFin.getDate() + (plan.duracionSemanas * 7));

  // Calcular progreso
  const hoy = new Date();
  const diasTotales = plan.duracionSemanas * 7;
  const diasTranscurridos = Math.floor((hoy - fechaInicio) / (1000 * 60 * 60 * 24));
  const progreso = Math.min(Math.max((diasTranscurridos / diasTotales) * 100, 0), 100);

  // Calcular total de ejercicios
  const totalEjercicios = plan.rutinas.reduce((total, rutina) => total + rutina.ejercicios.length, 0);

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">

        {/* Header */}
        <div className="mb-8 text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-3">
            Mi Plan de Entrenamiento
          </h1>
          <p className="text-gray-600 text-lg">Sigue tu progreso y completa tus rutinas diarias</p>
        </div>

        {/* Tarjeta de información general */}
        <div className="bg-white rounded-2xl shadow-xl p-6 mb-8">
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-6">
            {/* Información General */}
            <div className="lg:col-span-2">
              <h2 className="text-2xl font-bold text-gray-800 mb-4">Información del Plan</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="bg-gray-50 rounded-xl p-4">
                  <div className="text-sm text-gray-500 mb-1">Fecha de Inicio</div>
                  <div className="text-lg font-semibold text-gray-800">
                    {fechaInicio.toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
                  </div>
                </div>
                <div className="bg-gray-50 rounded-xl p-4">
                  <div className="text-sm text-gray-500 mb-1">Duración</div>
                  <div className="text-lg font-semibold text-gray-800">{plan.duracionSemanas} semanas</div>
                </div>
                <div className="bg-gray-50 rounded-xl p-4">
                  <div className="text-sm text-gray-500 mb-1">Fecha Estimada de Fin</div>
                  <div className="text-lg font-semibold text-gray-800">
                    {fechaFin.toLocaleDateString('es-ES', { year: 'numeric', month: 'short', day: 'numeric' })}
                  </div>
                </div>
                {plan.entrenadorId && (
                  <div className="bg-gray-50 rounded-xl p-4">
                    <div className="text-sm text-gray-500 mb-1">Entrenador Asignado</div>
                    <div className="text-lg font-semibold text-blue-600">ID: {plan.entrenadorId}</div>
                  </div>
                )}
              </div>
            </div>

            {/* Resumen */}
            <div className="bg-gradient-to-br from-blue-500 to-purple-600 rounded-2xl p-6 text-white">
              <h3 className="text-xl font-bold mb-4">Resumen del Plan</h3>
              <div className="space-y-4">
                <div>
                  <div className="text-sm opacity-90">Total de Rutinas</div>
                  <div className="text-3xl font-bold">{plan.rutinas.length}</div>
                </div>
                <div>
                  <div className="text-sm opacity-90">Total de Ejercicios</div>
                  <div className="text-3xl font-bold">{totalEjercicios}</div>
                </div>
                <div className="pt-4 border-t border-white/20">
                  <div className="text-sm opacity-90 mb-2">Progreso del Plan</div>
                  <div className="w-full bg-white/20 rounded-full h-2">
                    <div
                      className="bg-white h-2 rounded-full transition-all duration-500"
                      style={{ width: `${progreso}%` }}
                    ></div>
                  </div>
                  <div className="text-sm mt-1">{Math.round(progreso)}% completado</div>
                </div>
              </div>
            </div>
          </div>

          {/* ID del plan */}
          <div className="pt-6 border-t border-gray-200">
            <div className="inline-flex items-center bg-gray-100 px-4 py-2 rounded-lg">
              <span className="text-gray-600 mr-2">ID del Plan:</span>
              <span className="font-mono font-bold text-gray-800">#{plan.id}</span>
            </div>
          </div>
        </div>

        {/* Sección de Rutinas */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-800">Mis Rutinas ({plan.rutinas.length})</h2>
            <span className="bg-blue-100 text-blue-800 text-sm font-semibold px-3 py-1 rounded-full">
              {plan.duracionSemanas} semanas
            </span>
          </div>

          {plan.rutinas.length === 0 ? (
            <div className="bg-yellow-50 border border-yellow-200 rounded-xl p-8 text-center">
              <div className="text-yellow-500 text-3xl mb-4">📝</div>
              <p className="text-yellow-700 font-medium">No hay rutinas asignadas en este plan</p>
              <p className="text-gray-600 mt-1">Contacta con tu entrenador para asignarte rutinas.</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
              {plan.rutinas.map((rutina, index) => (
                <div key={rutina.id} className="bg-white rounded-xl shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300">
                  {/* Header de la rutina */}
                  <div className="bg-gradient-to-r from-blue-600 to-blue-700 px-6 py-4">
                    <div className="flex justify-between items-center">
                      <div>
                        <h3 className="text-xl font-bold text-white">
                          Rutina {index + 1}: {rutina.nombre}
                        </h3>
                        <p className="text-blue-100 text-sm mt-1">
                          {rutina.ejercicios.length} {rutina.ejercicios.length === 1 ? 'ejercicio' : 'ejercicios'}
                        </p>
                      </div>
                      <span className="bg-white/20 backdrop-blur-sm text-white px-3 py-1 rounded-full text-sm font-semibold">
                        {rutina.objetivo}
                      </span>
                    </div>
                  </div>

                  {/* Contenido de la rutina */}
                  <div className="p-6">
                    <h4 className="font-semibold text-gray-800 mb-4 text-lg">Ejercicios</h4>

                    {rutina.ejercicios.length === 0 ? (
                      <div className="text-center py-4">
                        <p className="text-gray-500">No hay ejercicios en esta rutina</p>
                      </div>
                    ) : (
                      <div className="space-y-4">
                        {rutina.ejercicios.map((ejercicio, ejIndex) => (
                          <div key={ejercicio.id} className="bg-gray-50 rounded-lg p-4 border border-gray-200">
                            <div className="flex items-start mb-3">
                              <div className="flex-shrink-0 w-8 h-8 bg-blue-600 text-white rounded-full flex items-center justify-center font-bold mr-3">
                                {ejIndex + 1}
                              </div>
                              <div className="flex-1">
                                <h5 className="font-bold text-gray-800 text-lg">
                                  {ejercicio.nombre || `Ejercicio ${ejIndex + 1}`}
                                </h5>
                                {ejercicio.descripcion && (
                                  <p className="text-gray-600 mt-1 text-sm">{ejercicio.descripcion}</p>
                                )}
                              </div>
                            </div>

                            {/* Detalles del ejercicio */}
                            <div className="grid grid-cols-2 md:grid-cols-4 gap-2 mt-3">
                              {ejercicio.series && (
                                <div className="bg-white rounded p-2 text-center">
                                  <div className="text-xs text-gray-500">Series</div>
                                  <div className="font-bold text-gray-800">{ejercicio.series}</div>
                                </div>
                              )}
                              {ejercicio.repeticiones && (
                                <div className="bg-white rounded p-2 text-center">
                                  <div className="text-xs text-gray-500">Repeticiones</div>
                                  <div className="font-bold text-gray-800">{ejercicio.repeticiones}</div>
                                </div>
                              )}
                              {ejercicio.descanso && (
                                <div className="bg-white rounded p-2 text-center">
                                  <div className="text-xs text-gray-500">Descanso</div>
                                  <div className="font-bold text-gray-800">{ejercicio.descanso}</div>
                                </div>
                              )}
                              {ejercicio.maquina && (
                                <div className="bg-white rounded p-2 text-center">
                                  <div className="text-xs text-gray-500">Máquina</div>
                                  <div className="font-bold text-gray-800">{ejercicio.maquina}</div>
                                </div>
                              )}
                            </div>

                            {/* Imagen del ejercicio */}
                            {ejercicio.imagenUrl && (
                              <div className="mt-4">
                                <div className="text-xs text-gray-500 mb-2">Demostración:</div>
                                <img
                                  src={ejercicio.imagenUrl}
                                  alt={ejercicio.nombre}
                                  className="w-full h-48 object-cover rounded-lg"
                                  onError={(e) => {
                                    e.target.onerror = null;
                                    e.target.src = 'https://via.placeholder.com/400x200?text=Imagen+no+disponible';
                                  }}
                                />
                              </div>
                            )}
                          </div>
                        ))}
                      </div>
                    )}
                  </div>

                  {/* Footer de la rutina */}
                  <div className="px-6 py-4 bg-gray-50 border-t border-gray-200">
                    <div className="flex justify-between items-center">
                      <span className="text-sm text-gray-600">
                        {rutina.ejercicios.length} {rutina.ejercicios.length === 1 ? 'ejercicio' : 'ejercicios'} totales
                      </span>
                      <button className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition text-sm">
                        Comenzar Rutina
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Acciones rápidas */}
        <div className="bg-gradient-to-r from-gray-900 to-gray-800 rounded-2xl p-6 text-white">
          <div className="flex flex-col md:flex-row items-center justify-between">
            <div>
              <h3 className="text-xl font-bold mb-2">¿Necesitas ayuda con tu plan?</h3>
              <p className="text-gray-300">Tu entrenador está disponible para resolver dudas y ajustar ejercicios.</p>
            </div>
            <div className="mt-4 md:mt-0 flex space-x-4">
              <button className="bg-white text-gray-900 hover:bg-gray-100 font-semibold py-2 px-6 rounded-lg transition">
                Contactar Entrenador
              </button>
              <button className="bg-transparent border border-white hover:bg-white/10 text-white font-semibold py-2 px-6 rounded-lg transition">
                Descargar Plan
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

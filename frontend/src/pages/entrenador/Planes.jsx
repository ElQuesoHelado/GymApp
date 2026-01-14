import React, { useState, useEffect } from 'react';
import axios from '../../api/axiosConfig';

export default function Planes() {
  const [planes, setPlanes] = useState([]);
  const [rutinasDisponibles, setRutinasDisponibles] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  
  const [mostrarForm, setMostrarForm] = useState(false);
  const [planParaGestionar, setPlanParaGestionar] = useState(null); 
  
  const [nuevoPlan, setNuevoPlan] = useState({
    fechaInicio: new Date().toISOString().split('T')[0], 
    duracionSemanas: 4,
    clienteId: '' 
  });

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const [resPlanes, resRutinas, resClientes] = await Promise.all([
        axios.get('/planes-entrenamiento'),
        axios.get('/planes-entrenamiento/rutinas'),
        axios.get('/planes-entrenamiento/clientes')
      ]);
      setPlanes(Array.isArray(resPlanes.data) ? resPlanes.data : resPlanes.data.data || []);
      setRutinasDisponibles(Array.isArray(resRutinas.data) ? resRutinas.data : resRutinas.data.data || []);
      setClientes(Array.isArray(resClientes.data) ? resClientes.data : resClientes.data.data || []);
    } catch (error) {
      console.error("Error cargando datos:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleCrearPlan = async (e) => {
    e.preventDefault();
    try {
      const dataAEnviar = {
        ...nuevoPlan,
        clienteId: nuevoPlan.clienteId ? Number(nuevoPlan.clienteId) : null,
        rutinas: []
      };
      await axios.post('/planes-entrenamiento', dataAEnviar);
      alert("¡Plan creado con éxito!");
      setMostrarForm(false);
      setNuevoPlan({ fechaInicio: new Date().toISOString().split('T')[0], duracionSemanas: 4, clienteId: '' });
      cargarDatos();
    } catch (error) {
      alert("Error al crear el plan.");
    }
  };

  // --- LÓGICA DE GESTIÓN DE RUTINAS (AGREGAR Y BORRAR) ---

  const handleAsignarRutina = async (rutinaId) => {
    try {
      const response = await axios.post(`/planes-entrenamiento/${planParaGestionar.id}/rutinas/${rutinaId}`);
      const planActualizado = response.data; // El backend devuelve el DTO con la nueva rutina

      setPlanes(planes.map(p => p.id === planActualizado.id ? planActualizado : p));
      setPlanParaGestionar(planActualizado);
    } catch (error) {
      alert("Error al agregar rutina");
    }
  };

  const handleRemoverRutina = async (rutinaId) => {
    if (!window.confirm("¿Quitar esta rutina del plan?")) return;
    try {
      const response = await axios.delete(`/planes-entrenamiento/${planParaGestionar.id}/rutinas/${rutinaId}`);
      const planActualizado = response.data;

      setPlanes(planes.map(p => p.id === planActualizado.id ? planActualizado : p));
      setPlanParaGestionar(planActualizado);
      
      console.log("Rutina eliminada con éxito");
    } catch (error) {
      console.error("Error al borrar rutina:", error);
      alert("No se pudo borrar la rutina del plan.");
    }
  };

  if (loading) return <div className="p-10 text-center text-blue-600 font-bold">Cargando sistema de entrenamiento...</div>;

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      
      {/* HEADER */}
      <div className="flex justify-between items-center mb-10">
        <div>
          <h1 className="text-4xl font-bold text-gray-900 italic font-serif">Planes Maestros</h1>
          <p className="text-gray-500 italic">Asigna y gestiona las rutinas de tus alumnos</p>
        </div>
        <button 
          onClick={() => setMostrarForm(!mostrarForm)}
          className={`px-6 py-2 rounded-xl font-bold text-white shadow-lg transition-all ${mostrarForm ? 'bg-red-500' : 'bg-blue-600'}`}
        >
          {mostrarForm ? 'Cerrar' : '+ Nuevo Plan'}
        </button>
      </div>

      {/* FORMULARIO*/}
      {mostrarForm && (
        <div className="bg-white p-8 rounded-3xl shadow-xl mb-10 border border-blue-50">
          <form onSubmit={handleCrearPlan} className="grid grid-cols-1 md:grid-cols-4 gap-6 items-end">
            <div>
              <label className="block text-xs font-bold text-gray-400 mb-2 uppercase">Fecha Inicio</label>
              <input type="date" className="w-full p-3 border rounded-2xl text-gray-900 bg-white" value={nuevoPlan.fechaInicio} onChange={e => setNuevoPlan({...nuevoPlan, fechaInicio: e.target.value})} required />
            </div>
            <div>
              <label className="block text-xs font-bold text-gray-400 mb-2 uppercase">Semanas</label>
              <input type="number" className="w-full p-3 border rounded-2xl text-gray-900 bg-white" value={nuevoPlan.duracionSemanas} onChange={e => setNuevoPlan({...nuevoPlan, duracionSemanas: e.target.value})} required />
            </div>
            <div>
              <label className="block text-xs font-bold text-gray-400 mb-2 uppercase">Alumno</label>
              <select className="w-full p-3 border rounded-2xl text-gray-900 bg-white" value={nuevoPlan.clienteId} onChange={e => setNuevoPlan({...nuevoPlan, clienteId: e.target.value})} required>
                <option value="">Seleccionar Alumno...</option>
                {clientes.map(c => <option key={c.id} value={c.id}>{c.nombre} {c.apellido}</option>)}
              </select>
            </div>
            <button type="submit" className="bg-green-600 text-white p-3 rounded-2xl font-bold hover:bg-green-700 transition-all">Guardar Plan</button>
          </form>
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {planes.map(plan => (
          <div key={plan.id} className="bg-white p-6 rounded-[2rem] shadow-sm border-t-8 border-t-blue-500 hover:shadow-lg transition-all">
            <h3 className="font-bold text-xl italic text-gray-800">Plan #{plan.id}</h3>
            <div className="mt-4 space-y-2 text-sm text-gray-600">
              <p>👤 <b>Alumno ID:</b> {plan.clienteId || 'Sin asignar'}</p>
              <p>📅 <b>Inicia:</b> {plan.fechaInicio}</p>
              <p>💪 <b>Rutinas:</b> {plan.rutinas?.length || 0} asignadas</p>
            </div>
            <button 
              onClick={() => setPlanParaGestionar(plan)} 
              className="mt-6 w-full py-2 bg-blue-50 text-blue-600 rounded-xl font-bold hover:bg-blue-600 hover:text-white transition-all"
            >
              Gestionar Rutinas
            </button>
          </div>
        ))}
      </div>

      {planParaGestionar && (
        <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex justify-center items-center z-50 p-4">
          <div className="bg-white w-full max-w-2xl rounded-[2.5rem] shadow-2xl overflow-hidden animate-zoom-in">
            <div className="p-6 bg-gray-900 text-white flex justify-between items-center">
              <h2 className="text-xl font-bold italic">Gestión del Plan #{planParaGestionar.id}</h2>
              <button onClick={() => setPlanParaGestionar(null)} className="text-2xl hover:text-red-400 transition-colors">&times;</button>
            </div>
            
            <div className="p-8 grid grid-cols-2 gap-8 max-h-[60vh] overflow-y-auto">
              <div>
                <h4 className="text-xs font-black text-gray-400 uppercase tracking-widest mb-4">En el Plan (Click X para borrar)</h4>
                <div className="space-y-2">
                  {planParaGestionar.rutinas?.map(r => (
                    <div key={r.id} className="flex justify-between items-center p-3 bg-blue-50 rounded-xl border border-blue-100 group">
                      <span className="text-xs font-bold text-blue-900">{r.nombre}</span>
                      <button 
                        onClick={() => handleRemoverRutina(r.id)} 
                        className="text-red-400 hover:text-red-600 font-bold px-2"
                      >
                        ×
                      </button>
                    </div>
                  ))}
                  {planParaGestionar.rutinas?.length === 0 && <p className="text-xs italic text-gray-300">No hay rutinas asignadas.</p>}
                </div>
              </div>

              <div>
                <h4 className="text-xs font-black text-gray-400 uppercase tracking-widest mb-4">Biblioteca de Rutinas</h4>
                <div className="space-y-2">
                  {rutinasDisponibles
                    .filter(rd => !planParaGestionar.rutinas?.some(pr => pr.id === rd.id))
                    .map(r => (
                      <div key={r.id} className="flex justify-between items-center p-3 bg-gray-50 rounded-xl hover:bg-gray-100 transition-all">
                        <span className="text-xs font-bold text-gray-700">{r.nombre}</span>
                        <button 
                          onClick={() => handleAsignarRutina(r.id)} 
                          className="bg-blue-600 text-white w-6 h-6 rounded-full font-bold flex items-center justify-center hover:scale-110 transition-transform"
                        >
                          +
                        </button>
                      </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
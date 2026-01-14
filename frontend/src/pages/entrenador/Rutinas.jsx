import React, { useState, useEffect } from 'react';
import axios from '../../api/axiosConfig';

export default function Rutinas() {
  const [rutinas, setRutinas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [mostrarFormulario, setMostrarFormulario] = useState(false);
  const [nuevaRutina, setNuevaRutina] = useState({ nombre: '', objetivo: '' });
  const [rutinaSeleccionada, setRutinaSeleccionada] = useState(null);

  const [nuevoEjercicio, setNuevoEjercicio] = useState({ nombre: '', series: 4, repeticiones: 12, descripcion: '' });

  useEffect(() => { cargarRutinas(); }, []);

  const cargarRutinas = async () => {
    try {
      const res = await axios.get('/rutinas');
      setRutinas(res.data);
    } catch (error) { console.error(error); } finally { setLoading(false); }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('/rutinas', { ...nuevaRutina, ejercicios: [] });
      setMostrarFormulario(false);
      setNuevaRutina({ nombre: '', objetivo: '' });
      cargarRutinas();
    } catch (error) { alert("Error al crear rutina"); }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("¿Seguro que quieres borrar esta rutina y sus ejercicios?")) return;
    try {
      await axios.delete(`/rutinas/${id}`);
      cargarRutinas();
    } catch (error) {
      console.error(error);
      alert("Error al eliminar");
    }
  };

  const handleAddEjercicio = async (e) => {
    e.preventDefault();
    if (!rutinaSeleccionada) return;

    try {
      const res = await axios.post(`/rutinas/${rutinaSeleccionada.id}/ejercicios`, nuevoEjercicio);
      setRutinaSeleccionada(res.data); 
      setNuevoEjercicio({ nombre: '', series: 4, repeticiones: 12, descripcion: '' }); 
      cargarRutinas();
    } catch (error) {
      console.error(error);
      alert("Error agregando ejercicio");
    }
  };

  if (loading) return <div className="p-8 text-center text-gray-500">Cargando...</div>;

  return (
    <div className="p-8 bg-gray-50 min-h-screen relative">
      <div className="flex justify-between items-center mb-8">
        <div><h1 className="text-3xl font-bold text-gray-800">Biblioteca de Rutinas</h1></div>
        <button onClick={() => setMostrarFormulario(!mostrarFormulario)} className="bg-blue-600 text-white px-6 py-2 rounded-lg font-bold shadow hover:bg-blue-700 transition-colors">
          {mostrarFormulario ? 'Cancelar' : '+ Nueva Rutina'}
        </button>
      </div>

      {/* FORMULARIO CREAR RUTINA */}
      {mostrarFormulario && (
        <div className="bg-white p-6 rounded-xl shadow-lg border border-blue-100 mb-8 animate-fade-in-down">
          <form onSubmit={handleSubmit} className="flex flex-col md:flex-row gap-4 items-end">
            <div className="w-full">
                <label className="block text-gray-700 text-sm font-bold mb-1">Nombre Rutina</label>
                <input 
                    className="border p-2 rounded w-full text-gray-900 bg-white focus:ring-2 focus:ring-blue-500 outline-none" 
                    placeholder="Ej: Full Body" 
                    value={nuevaRutina.nombre} 
                    onChange={e => setNuevaRutina({...nuevaRutina, nombre: e.target.value})} 
                    required 
                />
            </div>
            <div className="w-full">
                <label className="block text-gray-700 text-sm font-bold mb-1">Objetivo</label>
                <input 
                    className="border p-2 rounded w-full text-gray-900 bg-white focus:ring-2 focus:ring-blue-500 outline-none" 
                    placeholder="Ej: Hipertrofia" 
                    value={nuevaRutina.objetivo} 
                    onChange={e => setNuevaRutina({...nuevaRutina, objetivo: e.target.value})} 
                    required 
                />
            </div>
            <button type="submit" className="bg-green-600 text-white px-6 py-2 rounded font-bold hover:bg-green-700 transition-colors shadow">Guardar</button>
          </form>
        </div>
      )}

      {/* GRID DE RUTINAS */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {rutinas.map((r) => (
          <div key={r.id} className="bg-white rounded-xl shadow p-6 border border-gray-100 flex flex-col group hover:shadow-md transition-all">
            <div className="flex justify-between items-start">
                <h3 className="font-bold text-xl text-gray-800">{r.nombre}</h3>
                <span className="text-[10px] uppercase font-bold bg-blue-100 text-blue-700 px-2 py-1 rounded">{r.objetivo}</span>
            </div>
            
            <p className="text-sm text-gray-500 mt-4 mb-6 flex items-center gap-2">
                <span>🏋️‍♂️</span> {r.ejercicios ? r.ejercicios.length : 0} Ejercicios
            </p>
            
            <div className="mt-auto flex gap-2">
              <button onClick={() => setRutinaSeleccionada(r)} className="flex-1 bg-blue-50 text-blue-700 py-2 rounded text-sm font-bold hover:bg-blue-100 transition-colors">Ver Detalles</button>
              <button onClick={() => handleDelete(r.id)} className="text-gray-400 hover:text-red-500 px-3 transition-colors">🗑️</button>
            </div>
          </div>
        ))}
      </div>

      {/* MODAL DETALLES */}
      {rutinaSeleccionada && (
        <div className="fixed inset-0 bg-black/50 backdrop-blur-sm flex justify-center items-center z-50 p-4">
          <div className="bg-white rounded-2xl shadow-2xl w-full max-w-2xl overflow-hidden flex flex-col max-h-[90vh] animate-fade-in-up">
            
            {/* Header Modal */}
            <div className="bg-gray-100 p-6 flex justify-between items-center border-b">
              <div>
                <h2 className="text-2xl font-bold text-gray-800">{rutinaSeleccionada.nombre}</h2>
                <p className="text-sm text-gray-500">Gestión de ejercicios</p>
              </div>
              <button onClick={() => setRutinaSeleccionada(null)} className="text-3xl font-bold text-gray-400 hover:text-gray-600">&times;</button>
            </div>

            <div className="p-6 overflow-y-auto flex-1">
              {/* LISTA DE EJERCICIOS */}
              <ul className="space-y-2 mb-6">
                {(!rutinaSeleccionada.ejercicios || rutinaSeleccionada.ejercicios.length === 0) 
                  ? <div className="text-center py-6 border-2 border-dashed rounded-lg text-gray-400 italic">No hay ejercicios asignados todavía.</div>
                  : rutinaSeleccionada.ejercicios.map((ej, idx) => (
                    <li key={idx} className="bg-gray-50 p-3 rounded border border-gray-200 flex justify-between items-center shadow-sm">
                      <div>
                        <span className="font-bold text-gray-700 block">{ej.nombre}</span>
                        {ej.descripcion && <span className="text-xs text-gray-500">{ej.descripcion}</span>}
                      </div>
                      <span className="text-sm bg-blue-100 text-blue-800 font-bold px-2 py-1 rounded">{ej.series} x {ej.repeticiones}</span>
                    </li>
                  ))
                }
              </ul>

              {/* FORMULARIO AGREGAR EJERCICIO */}
              <div className="bg-blue-50 p-5 rounded-xl border border-blue-100 shadow-inner">
                <h4 className="font-bold text-blue-800 mb-3 text-sm flex items-center gap-2">
                    <span>➕</span> Agregar Nuevo Ejercicio
                </h4>
                <form onSubmit={handleAddEjercicio} className="grid grid-cols-2 md:grid-cols-4 gap-3">
                  <div className="col-span-2">
                    <input 
                        placeholder="Nombre (ej: Press Banca)" 
                        className="w-full p-2 rounded border border-gray-300 text-sm text-gray-900 focus:ring-2 focus:ring-blue-400 outline-none" 
                        value={nuevoEjercicio.nombre} 
                        onChange={e => setNuevoEjercicio({...nuevoEjercicio, nombre: e.target.value})} 
                        required 
                    />
                  </div>
                  <input 
                    type="number" placeholder="Series" 
                    className="w-full p-2 rounded border border-gray-300 text-sm text-gray-900 focus:ring-2 focus:ring-blue-400 outline-none" 
                    value={nuevoEjercicio.series} 
                    onChange={e => setNuevoEjercicio({...nuevoEjercicio, series: parseInt(e.target.value)})} 
                   />
                  <input 
                    type="number" placeholder="Reps" 
                    className="w-full p-2 rounded border border-gray-300 text-sm text-gray-900 focus:ring-2 focus:ring-blue-400 outline-none" 
                    value={nuevoEjercicio.repeticiones} 
                    onChange={e => setNuevoEjercicio({...nuevoEjercicio, repeticiones: parseInt(e.target.value)})} 
                   />
                  <button type="submit" className="col-span-2 md:col-span-4 bg-blue-600 text-white py-2 rounded font-bold hover:bg-blue-700 text-sm transition-colors shadow">
                    Agregar a la Rutina
                  </button>
                </form>
              </div>
            </div>

            <div className="p-4 bg-gray-50 text-right border-t">
              <button onClick={() => setRutinaSeleccionada(null)} className="px-6 py-2 bg-gray-800 text-white rounded font-medium hover:bg-gray-900 transition-colors">Cerrar</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
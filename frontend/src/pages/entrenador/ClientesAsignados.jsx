import React, { useState, useEffect } from 'react';
import axios from '../../api/axiosConfig';
import { useNavigate } from 'react-router-dom';

export default function ClientesAsignados() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [busqueda, setBusqueda] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    cargarDatosIntegrados();
  }, []);

  const cargarDatosIntegrados = async () => {
    try {
      const [resPlanes, resClientes] = await Promise.all([
        axios.get('/planes-entrenamiento'),
        axios.get('/planes-entrenamiento/clientes')
      ]);

      const listaPlanes = Array.isArray(resPlanes.data) ? resPlanes.data : resPlanes.data.data || [];
      const listaClientes = Array.isArray(resClientes.data) ? resClientes.data : resClientes.data.data || [];

      const alumnosConInfoReal = listaClientes.map(cliente => {
        const planEncontrado = listaPlanes.find(p => p.clienteId === cliente.id);
        
        return {
          ...cliente,
          plan: planEncontrado || null,
          estado: planEncontrado ? "Activo" : "Sin Plan" 
        };
      });

      setClientes(alumnosConInfoReal);
    } catch (error) {
      console.error("Error al sincronizar datos de alumnos:", error);
    } finally {
      setLoading(false);
    }
  };

  const clientesFiltrados = clientes.filter(c =>
    c.nombre.toLowerCase().includes(busqueda.toLowerCase()) ||
    (c.apellido && c.apellido.toLowerCase().includes(busqueda.toLowerCase()))
  );

  if (loading) return (
    <div className="flex flex-col justify-center items-center h-screen bg-gray-50">
      <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-indigo-600 mb-4"></div>
      <p className="text-indigo-600 font-bold animate-pulse">Cargando comunidad...</p>
    </div>
  );

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      
      {/* HEADER */}
      <div className="bg-gradient-to-r from-blue-700 to-indigo-600 rounded-[2rem] p-10 shadow-2xl text-white mb-10 flex flex-col md:flex-row justify-between items-center gap-6">
        <div>
          <h1 className="text-4xl font-black italic tracking-tight">Mis Alumnos</h1>
          <p className="text-blue-100 opacity-80 mt-2 font-medium">Gestión y seguimiento de planes maestros</p>
        </div>
        
        <div className="bg-white/10 backdrop-blur-md px-10 py-5 rounded-[1.5rem] text-center border border-white/20">
          <span className="block text-4xl font-black">{clientes.length}</span>
          <span className="text-[10px] uppercase tracking-[0.3em] font-bold opacity-70">Asignados</span>
        </div>
      </div>

      {/* BARRA DE BÚSQUEDA */}
      <div className="mb-10 flex gap-4">
        <div className="relative flex-1 max-w-2xl">
          <span className="absolute left-5 top-4 text-gray-400">🔍</span>
          <input 
            type="text"
            placeholder="Buscar alumno por nombre..."
            className="w-full pl-14 pr-6 py-4 rounded-2xl border-none shadow-sm focus:ring-2 focus:ring-blue-500 text-gray-900 bg-white"
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
          />
        </div>
      </div>

      {/* GRILLA DE TARJETAS (Estilo bosquejo) */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        {clientesFiltrados.map((alumno) => (
          <div key={alumno.id} className="bg-white rounded-[2.5rem] shadow-sm hover:shadow-2xl transition-all p-8 border border-gray-100 flex flex-col relative overflow-hidden group">
            
            <div className={`absolute top-0 left-0 w-full h-2 ${alumno.plan ? 'bg-blue-500' : 'bg-gray-200'}`}></div>

            <div className="flex items-start justify-between mb-8">
              <div className="flex items-center gap-4">
                <div className={`w-14 h-14 rounded-2xl flex items-center justify-center text-white font-black text-xl shadow-lg ${
                  alumno.plan ? 'bg-blue-600 shadow-blue-100' : 'bg-gray-400'
                }`}>
                  {alumno.nombre.charAt(0)}
                </div>
                <div>
                  <h3 className="font-bold text-gray-900 text-lg leading-tight">{alumno.nombre} {alumno.apellido}</h3>
                  <p className="text-[10px] font-black text-gray-400 uppercase tracking-widest mt-1">
                    {alumno.plan ? 'Nivel Asignado' : 'Sin Categoría'}
                  </p>
                </div>
              </div>
              
              <span className={`text-[10px] px-3 py-1 rounded-full font-black uppercase tracking-tighter ${
                alumno.plan ? 'bg-green-100 text-green-700' : 'bg-red-50 text-red-400'
              }`}>
                {alumno.estado}
              </span>
            </div>

            {/* INFORMACIÓN DEL PLAN REAL */}
            <div className="space-y-4 mb-8 bg-gray-50 p-6 rounded-[1.5rem] flex-1 border border-gray-100">
              <div className="flex justify-between items-center">
                <span className="text-[10px] font-black text-gray-400 uppercase">Plan Maestro</span>
                <span className="text-xs font-bold text-gray-700">
                  {alumno.plan ? `ID #${alumno.plan.id}` : 'No Asignado'}
                </span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-[10px] font-black text-gray-400 uppercase">Rutinas</span>
                <span className={`text-xs font-bold ${alumno.plan?.rutinas?.length > 0 ? 'text-blue-600' : 'text-gray-400'}`}>
                  {alumno.plan?.rutinas?.length || 0} cargadas
                </span>
              </div>
              <div className="flex justify-between items-center">
                <span className="text-[10px] font-black text-gray-400 uppercase">Inicio</span>
                <span className="text-xs font-bold text-gray-600">
                  {alumno.plan?.fechaInicio || 'Pendiente'}
                </span>
              </div>
            </div>

            {/* BOTONES DE ACCIÓN */}
            <div className="grid grid-cols-2 gap-4">
              <button 
                onClick={() => navigate('/planes')}
                className="px-4 py-3 bg-blue-600 text-white rounded-xl text-xs font-bold hover:bg-blue-700 transition-all active:scale-95 shadow-md shadow-blue-100"
              >
                Ver Plan
              </button>
              <button className="px-4 py-3 border border-gray-100 text-gray-400 rounded-xl text-xs font-bold hover:bg-gray-50 hover:text-gray-600 transition-all">
                Detalles
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* FOOTER VACÍO */}
      {clientesFiltrados.length === 0 && (
        <div className="text-center py-24">
          <p className="text-gray-400 italic font-medium">No se encontraron alumnos bajo tu supervisión.</p>
        </div>
      )}
    </div>
  );
}
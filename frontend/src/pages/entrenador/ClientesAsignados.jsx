import React, { useState, useEffect } from 'react';
// import { getClientesEntrenador } from "../../api/entrenador.api"; // Lo activaremos luego

export default function ClientesAsignados() {
  const [clientes, setClientes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [busqueda, setBusqueda] = useState("");

  // --- DATOS FALSOS PARA VER EL DISEÑO (Mientras conectamos el Backend) ---
  const datosSimulados = [
    { id: 1, nombre: "Carlos Mendoza", objetivo: "Ganar Masa Muscular", nivel: "Intermedio", estado: "Activo", ultimaAsistencia: "Hoy" },
    { id: 2, nombre: "Lucía Fernández", objetivo: "Pérdida de Peso", nivel: "Principiante", estado: "Pendiente", ultimaAsistencia: "Hace 3 días" },
    { id: 3, nombre: "Marcos Jiménez", objetivo: "Envejecimiento Activo", nivel: "Principiante", estado: "Activo", ultimaAsistencia: "Ayer" },
    { id: 4, nombre: "Sofía Rojas", objetivo: "Tonificación", nivel: "Avanzado", estado: "Inactivo", ultimaAsistencia: "Hace 1 semana" },
    { id: 5, nombre: "Jorge Torres", objetivo: "Fuerza Potencia", nivel: "Intermedio", estado: "Activo", ultimaAsistencia: "Hoy" },
  ];

  useEffect(() => {
    // Simulamos una carga de API
    setTimeout(() => {
      setClientes(datosSimulados);
      setLoading(false);
    }, 800);
  }, []);

  // Filtro de búsqueda
  const clientesFiltrados = clientes.filter(cliente =>
    cliente.nombre.toLowerCase().includes(busqueda.toLowerCase())
  );

  if (loading) return (
    <div className="flex justify-center items-center h-screen bg-gray-50">
      <p className="text-blue-600 animate-pulse font-bold text-lg">Cargando tus alumnos...</p>
    </div>
  );

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      
      {/* 1. HEADER (Estilo consistente con el Home) */}
      <div className="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-xl p-8 shadow-lg text-white mb-8 flex flex-col md:flex-row justify-between items-center gap-4">
        <div>
          <h1 className="text-3xl font-bold">Mis Alumnos</h1>
          <p className="text-blue-100 opacity-90">Gestión y seguimiento de progreso</p>
        </div>
        
        {/* Contador rápido */}
        <div className="bg-white/20 backdrop-blur-sm px-6 py-3 rounded-lg text-center">
          <span className="block text-2xl font-bold">{clientes.length}</span>
          <span className="text-xs uppercase tracking-wide opacity-80">Asignados</span>
        </div>
      </div>

      {/* 2. BARRA DE HERRAMIENTAS */}
      <div className="flex flex-col md:flex-row gap-4 mb-6">
        <div className="relative flex-1">
          <span className="absolute left-3 top-3 text-gray-400">🔍</span>
          <input 
            type="text"
            placeholder="Buscar por nombre..."
            className="w-full pl-10 pr-4 py-3 rounded-xl border border-gray-200 focus:outline-none focus:ring-2 focus:ring-blue-500 shadow-sm"
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
          />
        </div>
        {/* Aquí podrías poner filtros extra (Activos, Inactivos, etc.) */}
      </div>

      {/* 3. GRILLA DE CLIENTES */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {clientesFiltrados.map((cliente) => (
          <div key={cliente.id} className="bg-white rounded-xl shadow-sm hover:shadow-md transition-shadow p-6 border border-gray-100 flex flex-col">
            
            <div className="flex items-start justify-between mb-4">
              <div className="flex items-center gap-3">
                {/* Avatar con iniciales */}
                <div className={`w-12 h-12 rounded-full flex items-center justify-center text-white font-bold text-lg ${
                  cliente.estado === 'Activo' ? 'bg-blue-500' : 'bg-gray-400'
                }`}>
                  {cliente.nombre.charAt(0)}
                </div>
                <div>
                  <h3 className="font-bold text-gray-800">{cliente.nombre}</h3>
                  <span className="text-xs text-gray-500">{cliente.nivel}</span>
                </div>
              </div>
              
              {/* Badge de Estado */}
              <span className={`text-xs px-2 py-1 rounded-full font-medium ${
                cliente.estado === 'Activo' ? 'bg-green-100 text-green-700' : 
                cliente.estado === 'Pendiente' ? 'bg-yellow-100 text-yellow-700' : 
                'bg-red-100 text-red-700'
              }`}>
                {cliente.estado}
              </span>
            </div>

            <div className="space-y-2 mb-6 flex-1">
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Objetivo:</span>
                <span className="font-medium text-gray-700">{cliente.objetivo}</span>
              </div>
              <div className="flex justify-between text-sm">
                <span className="text-gray-500">Última vez:</span>
                <span className="font-medium text-gray-700">{cliente.ultimaAsistencia}</span>
              </div>
            </div>

            <div className="grid grid-cols-2 gap-3 mt-auto">
              <button className="px-4 py-2 bg-blue-50 text-blue-600 rounded-lg text-sm font-semibold hover:bg-blue-100 transition-colors">
                Ver Plan
              </button>
              <button className="px-4 py-2 border border-gray-200 text-gray-600 rounded-lg text-sm font-semibold hover:bg-gray-50 transition-colors">
                Detalles
              </button>
            </div>

          </div>
        ))}
      </div>
      
      {/* Mensaje si no hay resultados */}
      {clientesFiltrados.length === 0 && (
        <div className="text-center py-12 text-gray-500">
          <p>No se encontraron alumnos con ese nombre.</p>
        </div>
      )}

    </div>
  );
}
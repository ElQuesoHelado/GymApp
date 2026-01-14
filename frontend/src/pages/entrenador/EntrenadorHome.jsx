import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getEntrenadorHome } from "../../api/entrenador.api";

export default function EntrenadorHome() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  // Datos de respaldo (FALSOS) que se muestran si falla la conexión
  const fallbackData = {
    nombre: "Entrenador",
    apellido: "Demo",
    email: "entrenador@gymapp.com",
    dni: "---",
    telefono: "999-999-999",
    especialidad: "General",
    clientesActivos: 0,
    id: "#ST-001"
  };

  useEffect(() => {
    let montado = true;
    
    const tiempoEspera = setTimeout(() => {
      if (montado && loading) {
        console.warn("Usando datos offline por demora de API");
        setData(fallbackData);
        setLoading(false);
      }
    }, 800);

    getEntrenadorHome()
      .then(res => {
        if (montado) {
          clearTimeout(tiempoEspera);
          setData(res.data);
          setLoading(false);
        }
      })
      .catch(err => {
        console.error("Error conectando a API:", err);
        if (montado) {
          clearTimeout(tiempoEspera);
          setData(fallbackData);
          setLoading(false);
        }
      });

    return () => { montado = false; clearTimeout(tiempoEspera); };
  }, []);

  if (loading) return (
    <div className="flex justify-center items-center h-screen bg-gray-50">
      <p className="text-blue-600 animate-pulse font-semibold">Cargando perfil...</p>
    </div>
  );

  return (
    <div className="p-8 bg-gray-50 min-h-screen">
      
      {/* HEADER DE BIENVENIDA */}
      <div className="bg-gradient-to-r from-blue-600 to-indigo-600 rounded-t-xl p-8 shadow-md text-white flex justify-between items-center mb-6">
        <div>
          <h1 className="text-3xl font-bold">
            ¡Bienvenido, {data ? data.nombre : 'Entrenador'}!
          </h1>
          <p className="text-blue-100 mt-1 opacity-90">
            Tu centro de control de entrenamiento
          </p>
        </div>
        <div className="bg-white/20 px-4 py-2 rounded-lg backdrop-blur-sm">
          <span className="font-mono font-bold">{data?.id || 'ID: ???'}</span>
        </div>
      </div>

      {/* SECCIÓN DE DATOS */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        
        {/* TARJETA 1: Información Personal */}
        <div className="bg-blue-50 p-6 rounded-xl shadow-sm border border-blue-100">
          <h3 className="text-blue-800 font-semibold mb-4 flex items-center gap-2">
            👤 Información Personal
          </h3>
          <div className="space-y-3">
            <div>
              <p className="text-xs text-blue-500 uppercase font-bold">Nombre Completo</p>
              <p className="text-gray-700 font-medium">{data?.nombre} {data?.apellido}</p>
            </div>
            <div>
              <p className="text-xs text-blue-500 uppercase font-bold">DNI</p>
              <p className="text-gray-700 font-medium">{data?.dni}</p>
            </div>
            <div>
              <p className="text-xs text-blue-500 uppercase font-bold">Especialidad</p>
              <span className="inline-block bg-blue-200 text-blue-800 text-xs px-2 py-1 rounded mt-1">
                {data?.especialidad || 'Entrenador de Planta'}
              </span>
            </div>
          </div>
        </div>

        {/* TARJETA 2: Información de Contacto */}
        <div className="bg-purple-50 p-6 rounded-xl shadow-sm border border-purple-100">
          <h3 className="text-purple-800 font-semibold mb-4 flex items-center gap-2">
            ✉️ Información de Contacto
          </h3>
          <div className="space-y-3">
            <div>
              <p className="text-xs text-purple-500 uppercase font-bold">Email</p>
              <p className="text-gray-700 font-medium break-all">{data?.email}</p>
            </div>
            <div>
              <p className="text-xs text-purple-500 uppercase font-bold">Teléfono</p>
              <p className="text-gray-700 font-medium">{data?.telefono || 'Sin registrar'}</p>
            </div>
          </div>
        </div>

        {/* TARJETA 3: Estado / Resumen */}
        <div className="bg-green-50 p-6 rounded-xl shadow-sm border border-green-100">
          <h3 className="text-green-800 font-semibold mb-4 flex items-center gap-2">
            📈 Estado Actual
          </h3>
          <div className="flex flex-col items-center justify-center h-full pb-4">
            <p className="text-5xl font-bold text-green-600 mb-2">{data?.clientesActivos || 0}</p>
            <p className="text-green-700 font-medium">Clientes Asignados</p>
            <div className="mt-4 w-full bg-green-200 rounded-full h-2">
              <div className="bg-green-500 h-2 rounded-full" style={{ width: '40%' }}></div>
            </div>
            <p className="text-xs text-green-600 mt-2">Capacidad ocupada</p>
          </div>
        </div>

      </div>

      {/* ACCIONES RÁPIDAS */}
      <h2 className="text-xl font-bold text-gray-800 mb-4 px-1">Acciones Rápidas</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Link to="/dashboard/entrenador/planes" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-lg transition-all border border-gray-100 hover:-translate-y-1 flex items-center gap-4 group">
          <div className="bg-blue-100 p-4 rounded-full text-2xl group-hover:bg-blue-600 group-hover:text-white transition-colors">📋</div>
          <div>
            <h3 className="font-bold text-gray-800">Planes</h3>
            <p className="text-sm text-gray-500">Gestionar rutinas</p>
          </div>
        </Link>
        
        <Link to="/dashboard/entrenador/rutinas" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-lg transition-all border border-gray-100 hover:-translate-y-1 flex items-center gap-4 group">
          <div className="bg-indigo-100 p-4 rounded-full text-2xl group-hover:bg-indigo-600 group-hover:text-white transition-colors">💪</div>
          <div>
            <h3 className="font-bold text-gray-800">Ejercicios</h3>
            <p className="text-sm text-gray-500">Ver biblioteca</p>
          </div>
        </Link>

        <Link to="/dashboard/entrenador/sesiones" className="bg-white p-6 rounded-xl shadow-sm hover:shadow-lg transition-all border border-gray-100 hover:-translate-y-1 flex items-center gap-4 group">
          <div className="bg-purple-100 p-4 rounded-full text-2xl group-hover:bg-purple-600 group-hover:text-white transition-colors">⏱️</div>
          <div>
            <h3 className="font-bold text-gray-800">Horarios</h3>
            <p className="text-sm text-gray-500">Mis sesiones</p>
          </div>
        </Link>
      </div>
    </div>
  );
}
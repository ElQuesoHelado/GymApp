import { useEffect, useState } from "react";
import { getClienteHome } from "../../api/cliente.api";

export default function ClienteHome() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getClienteHome()
      .then(res => {
        console.log(res.data);
        setData(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError("Error al cargar los datos del cliente");
        setLoading(false);
      });
  }, []);

  // Función para calcular la edad a partir de fechaNacimiento
  const calcularEdad = (fechaNacimiento) => {
    if (!fechaNacimiento) return "No especificada";
    const fechaNac = new Date(fechaNacimiento);
    const hoy = new Date();
    let edad = hoy.getFullYear() - fechaNac.getFullYear();
    const mes = hoy.getMonth() - fechaNac.getMonth();
    if (mes < 0 || (mes === 0 && hoy.getDate() < fechaNac.getDate())) {
      edad--;
    }
    return `${edad} años`;
  };

  // Función para formatear fecha
  const formatearFecha = (fecha) => {
    if (!fecha) return "No especificada";
    return new Date(fecha).toLocaleDateString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric'
    });
  };

  // Función para obtener color según nivel
  const getNivelColor = (nivel) => {
    const colores = {
      'PRINCIPIANTE': 'bg-yellow-100 text-yellow-800',
      'INTERMEDIO': 'bg-blue-100 text-blue-800',
      'AVANZADO': 'bg-purple-100 text-purple-800',
      'PROFESIONAL': 'bg-red-100 text-red-800'
    };
    return colores[nivel?.toUpperCase()] || 'bg-gray-100 text-gray-800';
  };

  // Función para obtener icono según objetivo
  const getObjetivoIcono = (objetivo) => {
    const iconos = {
      'PERDIDA_PESO': '🏋️‍♂️',
      'GANANCIA_MUSCULAR': '💪',
      'TONIFICACION': '⚡',
      'SALUD': '❤️',
      'RENDIMIENTO': '🏆'
    };
    return iconos[objetivo] || '🎯';
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600 mb-4"></div>
          <p className="text-gray-600 text-lg font-medium">Cargando información del cliente...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="bg-red-50 border-l-4 border-red-500 p-6 rounded-lg shadow-lg max-w-md">
          <div className="flex items-center">
            <div className="flex-shrink-0">
              <svg className="h-8 w-8 text-red-500" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
              </svg>
            </div>
            <div className="ml-3">
              <p className="text-red-700 font-medium">{error}</p>
              <button
                onClick={() => window.location.reload()}
                className="mt-2 text-red-600 hover:text-red-800 font-medium"
              >
                Reintentar
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      {/* Header con título y bienvenida */}
      <div className="max-w-7xl mx-auto">
        <div className="mb-10 text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-3">
            <span className="bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              GymApp Dashboard
            </span>
          </h1>
          <p className="text-gray-600 text-lg">Tu centro de control de entrenamiento</p>
        </div>

        {/* Tarjeta principal del cliente */}
        <div className="bg-white rounded-2xl shadow-2xl overflow-hidden mb-8">
          <div className="bg-gradient-to-r from-blue-600 to-purple-600 px-8 py-6">
            <div className="flex flex-col md:flex-row justify-between items-start md:items-center">
              <div>
                <h2 className="text-3xl font-bold text-white mb-2">
                  ¡Bienvenido, {data.nombre}!
                </h2>
                <p className="text-blue-100">Tu información personal y objetivos de entrenamiento</p>
              </div>
              <div className="mt-4 md:mt-0">
                <span className="inline-block bg-white/20 backdrop-blur-sm px-4 py-2 rounded-full text-white font-semibold">
                  ID: #{data.id}
                </span>
              </div>
            </div>
          </div>

          <div className="p-8">
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

              {/* Información Básica */}
              <div className="bg-blue-50 rounded-xl p-6 border border-blue-100">
                <h3 className="text-lg font-semibold text-blue-900 mb-4 flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path fillRule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z" clipRule="evenodd" />
                  </svg>
                  Información Personal
                </h3>
                <div className="space-y-3">
                  <div>
                    <p className="text-sm text-blue-600 font-medium">Nombre completo</p>
                    <p className="text-gray-800 font-semibold">{data.nombre}</p>
                  </div>
                  <div>
                    <p className="text-sm text-blue-600 font-medium">DNI</p>
                    <p className="text-gray-800 font-semibold">{data.dni}</p>
                  </div>
                  <div>
                    <p className="text-sm text-blue-600 font-medium">Fecha de Nacimiento</p>
                    <p className="text-gray-800 font-semibold">{formatearFecha(data.fechaNacimiento)}</p>
                    <p className="text-sm text-gray-500">{calcularEdad(data.fechaNacimiento)}</p>
                  </div>
                </div>
              </div>

              {/* Contacto */}
              <div className="bg-purple-50 rounded-xl p-6 border border-purple-100">
                <h3 className="text-lg font-semibold text-purple-900 mb-4 flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M2.003 5.884L10 9.882l7.997-3.998A2 2 0 0016 4H4a2 2 0 00-1.997 1.884z" />
                    <path d="M18 8.118l-8 4-8-4V14a2 2 0 002 2h12a2 2 0 002-2V8.118z" />
                  </svg>
                  Información de Contacto
                </h3>
                <div className="space-y-3">
                  <div>
                    <p className="text-sm text-purple-600 font-medium">Email</p>
                    <a href={`mailto:${data.email}`} className="text-gray-800 font-semibold hover:text-purple-700 transition">
                      {data.email}
                    </a>
                  </div>
                  <div>
                    <p className="text-sm text-purple-600 font-medium">Teléfono</p>
                    <a href={`tel:${data.telefono}`} className="text-gray-800 font-semibold hover:text-purple-700 transition">
                      {data.telefono || "No especificado"}
                    </a>
                  </div>
                </div>
              </div>

              {/* Objetivos y Nivel */}
              <div className="bg-green-50 rounded-xl p-6 border border-green-100">
                <h3 className="text-lg font-semibold text-green-900 mb-4 flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path fillRule="evenodd" d="M12 7a1 1 0 110-2h5a1 1 0 011 1v5a1 1 0 11-2 0V8.414l-4.293 4.293a1 1 0 01-1.414 0L8 10.414l-4.293 4.293a1 1 0 01-1.414-1.414l5-5a1 1 0 011.414 0L11 10.586 14.586 7H12z" clipRule="evenodd" />
                  </svg>
                  Estado de Entrenamiento
                </h3>
                <div className="space-y-4">
                  <div>
                    <p className="text-sm text-green-600 font-medium">Objetivo Principal</p>
                    <div className="flex items-center mt-2">
                      <span className="text-2xl mr-3">{getObjetivoIcono(data.objetivo)}</span>
                      <span className="text-gray-800 font-semibold capitalize">
                        {data.objetivo?.replace('_', ' ') || "No especificado"}
                      </span>
                    </div>
                  </div>
                  <div>
                    <p className="text-sm text-green-600 font-medium">Nivel Actual</p>
                    <span className={`inline-block px-4 py-1.5 rounded-full text-sm font-semibold mt-2 ${getNivelColor(data.nivel)}`}>
                      {data.nivel || "No especificado"}
                    </span>
                  </div>
                </div>
              </div>

            </div>

            {/* Resumen visual */}
            <div className="mt-8 pt-8 border-t border-gray-200">
              <h3 className="text-xl font-bold text-gray-900 mb-6 text-center">Resumen de tu Perfil</h3>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <div className="text-center p-4 bg-gray-50 rounded-xl">
                  <div className="text-2xl font-bold text-blue-600">{data.id ? `#${data.id}` : '--'}</div>
                  <p className="text-sm text-gray-600 mt-1">ID Cliente</p>
                </div>
                <div className="text-center p-4 bg-gray-50 rounded-xl">
                  <div className="text-2xl font-bold text-purple-600">
                    {data.fechaNacimiento ? calcularEdad(data.fechaNacimiento).split(' ')[0] : '--'}
                  </div>
                  <p className="text-sm text-gray-600 mt-1">Edad</p>
                </div>
                <div className="text-center p-4 bg-gray-50 rounded-xl">
                  <div className="text-2xl font-bold text-green-600">
                    {data.objetivo ? getObjetivoIcono(data.objetivo) : '🎯'}
                  </div>
                  <p className="text-sm text-gray-600 mt-1">Objetivo</p>
                </div>
                <div className="text-center p-4 bg-gray-50 rounded-xl">
                  <div className="text-2xl font-bold text-yellow-600">{data.nivel?.charAt(0) || 'N'}</div>
                  <p className="text-sm text-gray-600 mt-1">Nivel</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Mensaje de bienvenida personalizado */}
        <div className="bg-gradient-to-r from-gray-900 to-gray-800 rounded-2xl p-8 text-white">
          <div className="flex flex-col md:flex-row items-center">
            <div className="md:w-2/3">
              <h3 className="text-2xl font-bold mb-3">Tu viaje fitness comienza aquí</h3>
              <p className="text-gray-300 mb-4">
                Con tu nivel <span className="font-bold">{data.nivel?.toLowerCase() || "actual"}</span> y objetivo de
                <span className="font-bold ml-1">{data.objetivo?.replace('_', ' ').toLowerCase() || "fitness"}</span>,
                estamos preparados para crear la rutina perfecta para ti.
              </p>
              <p className="text-blue-300">
                ¡Mantén la motivación y alcanza tus metas con GymApp!
              </p>
            </div>
            <div className="md:w-1/3 mt-6 md:mt-0 flex justify-center">
              <div className="bg-white/10 backdrop-blur-sm p-6 rounded-xl">
                <div className="text-6xl">💪</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

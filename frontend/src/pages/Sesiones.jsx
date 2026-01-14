import { useEffect, useState } from "react";
import { getSesiones } from "../api/sesion.api";

export default function Sesiones() {
  const [sesiones, setSesiones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filtroEstado, setFiltroEstado] = useState("TODAS");
  const [fechaFiltro, setFechaFiltro] = useState("");

  useEffect(() => {
    cargarSesiones();
  }, []);

  const cargarSesiones = () => {
    setLoading(true);
    getSesiones()
      .then(res => {
        setSesiones(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError("Error al cargar las sesiones");
        setLoading(false);
      });
  };

  // Formatear fecha
  const formatFecha = (fechaString) => {
    if (!fechaString) return "No especificada";
    const fecha = new Date(fechaString);
    return fecha.toLocaleDateString('es-ES', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  };

  // Formatear hora
  const formatHora = (horaString) => {
    if (!horaString) return "";
    return horaString.slice(0, 5); // Formato HH:mm
  };

  // Obtener color según estado
  const getEstadoColor = (estado) => {
    const colores = {
      'PROGRAMADA': 'bg-blue-100 text-blue-800 border-blue-200',
      'EN_CURSO': 'bg-green-100 text-green-800 border-green-200',
      'COMPLETADA': 'bg-gray-100 text-gray-800 border-gray-200',
      'CANCELADA': 'bg-red-100 text-red-800 border-red-200',
      'SUSPENDIDA': 'bg-yellow-100 text-yellow-800 border-yellow-200'
    };
    return colores[estado] || 'bg-gray-100 text-gray-800';
  };

  // Obtener texto del estado
  const getEstadoTexto = (estado) => {
    const textos = {
      'PROGRAMADA': 'Programada',
      'EN_CURSO': 'En Curso',
      'COMPLETADA': 'Completada',
      'CANCELADA': 'Cancelada',
      'SUSPENDIDA': 'Suspendida'
    };
    return textos[estado] || estado;
  };

  // Obtener icono según estado
  const getEstadoIcono = (estado) => {
    const iconos = {
      'PROGRAMADA': (
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
        </svg>
      ),
      'EN_CURSO': (
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
        </svg>
      ),
      'COMPLETADA': (
        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
          <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
        </svg>
      ),
      'CANCELADA': (
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      ),
      'SUSPENDIDA': (
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
        </svg>
      )
    };
    return iconos[estado] || (
      <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
        <path d="M10 2a6 6 0 00-6 6v3.586l-.707.707A1 1 0 004 14h12a1 1 0 00.707-1.707L16 11.586V8a6 6 0 00-6-6zM10 18a3 3 0 01-3-3h6a3 3 0 01-3 3z" />
      </svg>
    );
  };

  // Filtrar sesiones
  const filtrarSesiones = () => {
    let filtradas = sesiones;

    if (filtroEstado !== "TODAS") {
      filtradas = filtradas.filter(s => s.estado === filtroEstado);
    }

    if (fechaFiltro) {
      filtradas = filtradas.filter(s =>
        s.horario?.fecha && s.horario.fecha.startsWith(fechaFiltro)
      );
    }

    // Ordenar por fecha más próxima
    filtradas.sort((a, b) => {
      if (!a.horario?.fecha) return 1;
      if (!b.horario?.fecha) return -1;
      return new Date(a.horario.fecha) - new Date(b.horario.fecha);
    });

    return filtradas;
  };

  const sesionesFiltradas = filtrarSesiones();

  // Contar sesiones por estado
  const contarSesionesPorEstado = () => {
    const conteo = {
      TODAS: sesiones.length,
      PROGRAMADA: sesiones.filter(s => s.estado === 'PROGRAMADA').length,
      EN_CURSO: sesiones.filter(s => s.estado === 'EN_CURSO').length,
      COMPLETADA: sesiones.filter(s => s.estado === 'COMPLETADA').length,
      CANCELADA: sesiones.filter(s => s.estado === 'CANCELADA').length,
      SUSPENDIDA: sesiones.filter(s => s.estado === 'SUSPENDIDA').length
    };
    return conteo;
  };

  const conteoEstados = contarSesionesPorEstado();

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-blue-600 mb-6"></div>
          <h3 className="text-xl font-semibold text-gray-700 mb-2">Cargando sesiones</h3>
          <p className="text-gray-500">Obteniendo información de tus entrenamientos...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="max-w-md w-full">
          <div className="bg-red-50 border-l-4 border-red-500 p-6 rounded-r-xl shadow-lg">
            <div className="flex">
              <div className="flex-shrink-0">
                <svg className="h-8 w-8 text-red-500" fill="currentColor" viewBox="0 0 20 20">
                  <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                </svg>
              </div>
              <div className="ml-3">
                <h3 className="text-lg font-medium text-red-800">Error al cargar</h3>
                <div className="mt-2 text-red-700">
                  <p>{error}</p>
                </div>
                <button
                  onClick={cargarSesiones}
                  className="mt-4 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition duration-200"
                >
                  Reintentar
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-10">
          <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 mb-6">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">
                <span className="bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                  Mis Sesiones
                </span>
              </h1>
              <p className="text-gray-600">Gestiona y consulta tus sesiones de entrenamiento</p>
            </div>

            <div className="flex items-center space-x-4">
              <button
                onClick={cargarSesiones}
                className="px-4 py-2 bg-white border border-gray-300 rounded-xl text-gray-700 hover:bg-gray-50 transition duration-200 flex items-center"
              >
                <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                Actualizar
              </button>

              <button className="px-6 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-xl font-semibold hover:from-blue-700 hover:to-purple-700 transition duration-200 flex items-center">
                <svg className="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                </svg>
                Nueva Sesión
              </button>
            </div>
          </div>

          {/* Estadísticas rápidas */}
          <div className="grid grid-cols-2 md:grid-cols-6 gap-4">
            {Object.entries(conteoEstados).map(([estado, count]) => (
              <div
                key={estado}
                className={`bg-white rounded-xl p-4 shadow-sm cursor-pointer transition-all duration-200 hover:shadow-md ${filtroEstado === estado ? 'ring-2 ring-blue-500' : ''
                  }`}
                onClick={() => setFiltroEstado(estado)}
              >
                <div className="text-center">
                  <div className="text-2xl font-bold text-gray-800 mb-1">{count}</div>
                  <div className={`text-xs font-medium px-2 py-1 rounded-full inline-block ${estado === 'TODAS' ? 'bg-gray-100 text-gray-800' : getEstadoColor(estado)
                    }`}>
                    {estado === 'TODAS' ? 'TOTAL' : getEstadoTexto(estado)}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Filtros */}
        <div className="mb-8">
          <div className="bg-white rounded-2xl shadow-lg p-6">
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
              <div>
                <h3 className="text-lg font-semibold text-gray-800 mb-2">Filtrar sesiones</h3>
                <div className="flex flex-wrap gap-2">
                  {['TODAS', 'PROGRAMADA', 'EN_CURSO', 'COMPLETADA', 'CANCELADA', 'SUSPENDIDA'].map((estado) => (
                    <button
                      key={estado}
                      onClick={() => setFiltroEstado(estado)}
                      className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtroEstado === estado
                        ? estado === 'TODAS'
                          ? 'bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-md'
                          : 'bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-md'
                        : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                        }`}
                    >
                      {estado === 'TODAS' ? 'Todas' : getEstadoTexto(estado)}
                      <span className="ml-2 bg-white/30 px-2 py-1 rounded-full text-xs">
                        {conteoEstados[estado]}
                      </span>
                    </button>
                  ))}
                </div>
              </div>

              <div className="flex-1 max-w-xs">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Filtrar por fecha
                </label>
                <input
                  type="date"
                  value={fechaFiltro}
                  onChange={(e) => setFechaFiltro(e.target.value)}
                  className="w-full px-4 py-2 bg-gray-50 border border-gray-300 rounded-xl focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                />
              </div>
            </div>
          </div>
        </div>

        {/* Lista de sesiones */}
        {sesionesFiltradas.length === 0 ? (
          <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
            <div className="text-6xl mb-6">🏋️‍♂️</div>
            <h3 className="text-2xl font-bold text-gray-700 mb-3">
              {filtroEstado !== "TODAS" || fechaFiltro
                ? "No hay sesiones con estos filtros"
                : "¡No hay sesiones programadas!"}
            </h3>
            <p className="text-gray-500 mb-8">
              {filtroEstado !== "TODAS" || fechaFiltro
                ? "Prueba con otros filtros o consulta todas las sesiones"
                : "Comienza por programar tu primera sesión de entrenamiento"}
            </p>
            {(filtroEstado !== "TODAS" || fechaFiltro) && (
              <div className="space-x-4">
                <button
                  onClick={() => {
                    setFiltroEstado("TODAS");
                    setFechaFiltro("");
                  }}
                  className="px-6 py-3 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-xl font-semibold hover:from-blue-700 hover:to-purple-700 transition duration-200"
                >
                  Ver todas las sesiones
                </button>
              </div>
            )}
          </div>
        ) : (
          <div className="space-y-6">
            {/* Encabezado de resultados */}
            <div className="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl p-4 shadow-sm">
              <div className="flex flex-wrap items-center justify-between">
                <p className="text-gray-700 font-medium">
                  Mostrando <span className="font-bold text-blue-600">{sesionesFiltradas.length}</span> de{" "}
                  <span className="font-bold text-gray-800">{sesiones.length}</span> sesiones
                </p>
                <div className="flex items-center space-x-4">
                  <span className="text-sm text-gray-600">
                    {sesionesFiltradas.filter(s => s.estado === 'PROGRAMADA').length} programadas
                  </span>
                  <button
                    onClick={() => setFechaFiltro('')}
                    className="text-sm text-blue-600 hover:text-blue-800 font-medium"
                  >
                    Limpiar filtros
                  </button>
                </div>
              </div>
            </div>

            {/* Grid de sesiones */}
            <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
              {sesionesFiltradas.map((sesion) => (
                <div
                  key={sesion.id}
                  className="bg-white rounded-2xl shadow-lg overflow-hidden hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1"
                >
                  {/* Header de la sesión */}
                  <div className="bg-gradient-to-r from-gray-900 to-gray-800 px-6 py-4">
                    <div className="flex justify-between items-center">
                      <div>
                        <h3 className="text-xl font-bold text-white">Sesión #{sesion.id}</h3>
                        <p className="text-gray-300 text-sm">ID: {sesion.id}</p>
                      </div>
                      <div className={`px-4 py-2 rounded-full flex items-center space-x-2 ${getEstadoColor(sesion.estado)}`}>
                        <span>{getEstadoIcono(sesion.estado)}</span>
                        <span className="font-semibold">{getEstadoTexto(sesion.estado)}</span>
                      </div>
                    </div>
                  </div>

                  <div className="p-6">
                    {/* Horario y Sala */}
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                      <div className="bg-blue-50 rounded-xl p-4">
                        <h4 className="font-semibold text-blue-900 mb-3 flex items-center">
                          <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                          </svg>
                          Horario
                        </h4>
                        {sesion.horario ? (
                          <div className="space-y-2">
                            <div className="flex justify-between">
                              <span className="text-gray-600">Fecha:</span>
                              <span className="font-semibold">{formatFecha(sesion.horario.fecha)}</span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-gray-600">Inicio:</span>
                              <span className="font-semibold">{formatHora(sesion.horario.horaInicio)}</span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-gray-600">Fin:</span>
                              <span className="font-semibold">{formatHora(sesion.horario.horaFin)}</span>
                            </div>
                          </div>
                        ) : (
                          <p className="text-gray-500 italic">Horario no especificado</p>
                        )}
                      </div>

                      <div className="bg-purple-50 rounded-xl p-4">
                        <h4 className="font-semibold text-purple-900 mb-3 flex items-center">
                          <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                          </svg>
                          Sala
                        </h4>
                        {sesion.sala ? (
                          <div className="space-y-2">
                            <div className="flex justify-between">
                              <span className="text-gray-600">Nombre:</span>
                              <span className="font-semibold">{sesion.sala.nombre}</span>
                            </div>
                            <div className="flex justify-between">
                              <span className="text-gray-600">Capacidad:</span>
                              <span className="font-semibold">{sesion.sala.capacidad} personas</span>
                            </div>
                          </div>
                        ) : (
                          <p className="text-gray-500 italic">Sala no asignada</p>
                        )}
                      </div>
                    </div>

                    {/* Entrenador */}
                    <div className="mb-6">
                      <h4 className="font-semibold text-gray-800 mb-3 flex items-center">
                        <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
                        Entrenador
                      </h4>
                      {sesion.entrenador ? (
                        <div className="bg-gradient-to-r from-green-50 to-emerald-50 rounded-xl p-4">
                          <div className="flex items-center space-x-4">
                            <div className="h-12 w-12 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center">
                              <span className="text-white font-bold text-lg">
                                {sesion.entrenador.nombre?.charAt(0) || 'E'}
                              </span>
                            </div>
                            <div className="flex-1">
                              <div className="flex justify-between items-center">
                                <div>
                                  <h5 className="font-bold text-gray-800">{sesion.entrenador.nombre}</h5>
                                  <p className="text-sm text-gray-600">{sesion.entrenador.especialidad}</p>
                                </div>
                                <div className="text-right">
                                  <p className="text-sm text-gray-600">Certificado</p>
                                  <p className="text-xs text-gray-500 truncate max-w-[150px]">
                                    {sesion.entrenador.certificaciones || 'No especificada'}
                                  </p>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      ) : (
                        <div className="bg-gray-50 rounded-xl p-4">
                          <p className="text-gray-500 italic text-center">Entrenador no asignado</p>
                        </div>
                      )}
                    </div>

                    {/* Participantes */}
                    <div>
                      <div className="flex justify-between items-center mb-3">
                        <h4 className="font-semibold text-gray-800 flex items-center">
                          <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197m13 0h-6" />
                          </svg>
                          Participantes ({sesion.clientes?.length || 0})
                        </h4>
                        {sesion.sala && sesion.clientes && (
                          <span className={`text-sm px-3 py-1 rounded-full ${sesion.clientes.length >= sesion.sala.capacidad
                            ? 'bg-red-100 text-red-800'
                            : 'bg-green-100 text-green-800'
                            }`}>
                            {sesion.clientes.length}/{sesion.sala.capacidad} cupos
                          </span>
                        )}
                      </div>

                      {sesion.clientes && sesion.clientes.length > 0 ? (
                        <div className="space-y-3">
                          {sesion.clientes.slice(0, 3).map((cliente, index) => (
                            <div key={cliente.id} className="flex items-center justify-between bg-gray-50 p-3 rounded-xl">
                              <div className="flex items-center space-x-3">
                                <div className="h-10 w-10 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full flex items-center justify-center">
                                  <span className="text-white font-bold">
                                    {cliente.nombre?.charAt(0) || 'C'}
                                  </span>
                                </div>
                                <div>
                                  <p className="font-medium text-gray-800">{cliente.nombre}</p>
                                  <p className="text-xs text-gray-500">Nivel: {cliente.nivel || 'No especificado'}</p>
                                </div>
                              </div>
                              <span className="text-xs px-2 py-1 bg-gray-200 text-gray-700 rounded-full">
                                {cliente.objetivo?.replace('_', ' ') || 'Sin objetivo'}
                              </span>
                            </div>
                          ))}

                          {sesion.clientes.length > 3 && (
                            <div className="text-center pt-2">
                              <button className="text-blue-600 hover:text-blue-800 text-sm font-medium">
                                +{sesion.clientes.length - 3} participantes más
                              </button>
                            </div>
                          )}
                        </div>
                      ) : (
                        <div className="bg-gray-50 rounded-xl p-4">
                          <p className="text-gray-500 italic text-center">No hay participantes registrados</p>
                        </div>
                      )}
                    </div>

                    {/* Acciones */}
                    <div className="mt-6 pt-6 border-t border-gray-200">
                      <div className="flex justify-between">
                        <div className="flex space-x-2">
                          {sesion.estado === 'PROGRAMADA' && (
                            <>
                              <button className="px-4 py-2 bg-blue-600 text-white rounded-xl hover:bg-blue-700 transition duration-200">
                                Unirme
                              </button>
                              <button className="px-4 py-2 bg-red-600 text-white rounded-xl hover:bg-red-700 transition duration-200">
                                Cancelar
                              </button>
                            </>
                          )}
                          {sesion.estado === 'EN_CURSO' && (
                            <button className="px-4 py-2 bg-green-600 text-white rounded-xl hover:bg-green-700 transition duration-200">
                              Ver en directo
                            </button>
                          )}
                        </div>
                        <button className="px-4 py-2 bg-gray-100 text-gray-700 rounded-xl hover:bg-gray-200 transition duration-200">
                          Detalles
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Quick Stats Footer */}
        {sesiones.length > 0 && (
          <div className="mt-8 bg-gradient-to-r from-gray-900 to-gray-800 rounded-2xl p-6 text-white">
            <div className="flex flex-col md:flex-row items-center justify-between">
              <div>
                <h3 className="text-xl font-bold mb-2">Resumen de sesiones</h3>
                <div className="flex space-x-6">
                  <div>
                    <p className="text-gray-300">Próxima sesión</p>
                    <p className="text-2xl font-bold">
                      {sesionesFiltradas.filter(s => s.estado === 'PROGRAMADA').length > 0
                        ? formatFecha(sesionesFiltradas.filter(s => s.estado === 'PROGRAMADA')[0]?.horario?.fecha)
                        : 'No programada'}
                    </p>
                  </div>
                  <div>
                    <p className="text-gray-300">Sesiones completadas</p>
                    <p className="text-2xl font-bold">
                      {sesiones.filter(s => s.estado === 'COMPLETADA').length}
                    </p>
                  </div>
                </div>
              </div>
              <div className="mt-4 md:mt-0">
                <button className="px-6 py-3 bg-white text-gray-900 rounded-xl font-semibold hover:bg-gray-100 transition duration-200">
                  Exportar Calendario
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

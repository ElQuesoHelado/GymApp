import { useEffect, useState } from "react";
import { getNotificaciones } from "../api/notificacion.api";

export default function Notificaciones() {
  const [notificaciones, setNotificaciones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filtro, setFiltro] = useState("TODAS");

  useEffect(() => {
    getNotificaciones()
      .then(res => {
        setNotificaciones(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setError("Error al cargar las notificaciones");
        setLoading(false);
      });
  }, []);

  const marcarComoLeido = (id) => {
    setNotificaciones(prev =>
      prev.map(notif =>
        notif.id === id ? { ...notif, leido: true } : notif
      )
    );
  };

  const marcarTodasComoLeidas = () => {
    setNotificaciones(prev =>
      prev.map(notif => ({ ...notif, leido: true }))
    );
  };

  const filtrarNotificaciones = () => {
    switch (filtro) {
      case "NO_LEIDAS":
        return notificaciones.filter(n => !n.leido);
      case "LEIDAS":
        return notificaciones.filter(n => n.leido);
      case "IMPORTANTE":
        return notificaciones.filter(n => n.tipo === "IMPORTANTE");
      case "SESIONINMINENTE":
        return notificaciones.filter(n => n.tipo === "SESIONINMINENTE");
      case "MENSAJE":
        return notificaciones.filter(n => n.tipo === "MENSAJE");
      default:
        return notificaciones;
    }
  };

  const notificacionesFiltradas = filtrarNotificaciones();

  const obtenerIconoTipo = (tipo) => {
    const iconos = {
      "IMPORTANTE": (
        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
          <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
        </svg>
      ),
      "SESIONINMINENTE": (
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
        </svg>
      ),
      "MENSAJE": (
        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
          <path fillRule="evenodd" d="M18 5v8a2 2 0 01-2 2h-5l-5 4v-4H4a2 2 0 01-2-2V5a2 2 0 012-2h12a2 2 0 012 2zM7 8H5v2h2V8zm2 0h2v2H9V8zm6 0h-2v2h2V8z" clipRule="evenodd" />
        </svg>
      ),
      "default": (
        <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
          <path d="M10 2a6 6 0 00-6 6v3.586l-.707.707A1 1 0 004 14h12a1 1 0 00.707-1.707L16 11.586V8a6 6 0 00-6-6zM10 18a3 3 0 01-3-3h6a3 3 0 01-3 3z" />
        </svg>
      )
    };
    return iconos[tipo] || iconos.default;
  };

  const obtenerColorTipo = (tipo) => {
    const colores = {
      "IMPORTANTE": "bg-red-100 text-red-800 border-red-200",
      "SESIONINMINENTE": "bg-orange-100 text-orange-800 border-orange-200",
      "MENSAJE": "bg-blue-100 text-blue-800 border-blue-200",
      "default": "bg-gray-100 text-gray-800 border-gray-200"
    };
    return colores[tipo] || colores.default;
  };

  const obtenerTipoTexto = (tipo) => {
    const textos = {
      "IMPORTANTE": "Importante",
      "SESIONINMINENTE": "Sesión Inminente",
      "MENSAJE": "Mensaje",
      "default": tipo
    };
    return textos[tipo] || textos.default;
  };

  const contarNoLeidas = () => {
    return notificaciones.filter(n => !n.leido).length;
  };

  const formatFecha = (fechaString) => {
    const fecha = new Date(fechaString);
    const ahora = new Date();
    const diffMs = ahora - fecha;
    const diffMin = Math.floor(diffMs / (1000 * 60));
    const diffHoras = Math.floor(diffMs / (1000 * 60 * 60));
    const diffDias = Math.floor(diffMs / (1000 * 60 * 60 * 24));

    if (diffMin < 1) {
      return "Ahora mismo";
    } else if (diffMin < 60) {
      return `Hace ${diffMin} min`;
    } else if (diffHoras < 24) {
      return `Hace ${diffHoras} h`;
    } else if (diffDias === 1) {
      return "Ayer";
    } else if (diffDias < 7) {
      return `Hace ${diffDias} d`;
    } else if (diffDias < 30) {
      return `Hace ${Math.floor(diffDias / 7)} sem`;
    } else {
      return fecha.toLocaleDateString('es-ES', {
        day: 'numeric',
        month: 'short',
        year: 'numeric'
      });
    }
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-600 mb-4"></div>
          <h3 className="text-xl font-semibold text-gray-700 mb-2">Cargando notificaciones</h3>
          <p className="text-gray-500">Estamos obteniendo tus últimas actualizaciones...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="max-w-md w-full mx-4">
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
                  onClick={() => window.location.reload()}
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
      <div className="max-w-6xl mx-auto">
        {/* Header */}
        <div className="mb-8">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">
                <span className="bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                  Notificaciones
                </span>
              </h1>
              <p className="text-gray-600">Mantente al día con tus actividades en GymApp</p>
            </div>

            {contarNoLeidas() > 0 && (
              <div className="flex items-center space-x-4">
                <span className="relative">
                  <div className="absolute -top-2 -right-2 h-6 w-6 bg-red-500 rounded-full flex items-center justify-center">
                    <span className="text-xs font-bold text-white">{contarNoLeidas()}</span>
                  </div>
                  <svg className="w-8 h-8 text-gray-600" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M10 2a6 6 0 00-6 6v3.586l-.707.707A1 1 0 004 14h12a1 1 0 00.707-1.707L16 11.586V8a6 6 0 00-6-6zM10 18a3 3 0 01-3-3h6a3 3 0 01-3 3z" />
                  </svg>
                </span>
                <button
                  onClick={marcarTodasComoLeidas}
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition duration-200 flex items-center"
                >
                  <svg className="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                  </svg>
                  Marcar todas como leídas
                </button>
              </div>
            )}
          </div>
        </div>

        {/* Filtros */}
        <div className="mb-8">
          <div className="bg-white rounded-2xl shadow-lg p-4">
            <h3 className="text-lg font-semibold text-gray-700 mb-4">Filtrar por</h3>
            <div className="flex flex-wrap gap-2">
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "TODAS"
                    ? "bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("TODAS")}
              >
                Todas ({notificaciones.length})
              </button>
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "NO_LEIDAS"
                    ? "bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("NO_LEIDAS")}
              >
                <span className="flex items-center">
                  No leídas
                  {contarNoLeidas() > 0 && (
                    <span className="ml-2 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
                      {contarNoLeidas()}
                    </span>
                  )}
                </span>
              </button>
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "LEIDAS"
                    ? "bg-gradient-to-r from-blue-600 to-purple-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("LEIDAS")}
              >
                Leídas ({notificaciones.length - contarNoLeidas()})
              </button>
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "IMPORTANTE"
                    ? "bg-gradient-to-r from-red-600 to-rose-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("IMPORTANTE")}
              >
                Importantes ({notificaciones.filter(n => n.tipo === "IMPORTANTE").length})
              </button>
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "SESIONINMINENTE"
                    ? "bg-gradient-to-r from-orange-600 to-amber-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("SESIONINMINENTE")}
              >
                Sesiones ({notificaciones.filter(n => n.tipo === "SESIONINMINENTE").length})
              </button>
              <button
                className={`px-4 py-2 rounded-xl font-medium transition duration-200 ${filtro === "MENSAJE"
                    ? "bg-gradient-to-r from-blue-600 to-cyan-600 text-white shadow-md"
                    : "bg-gray-100 text-gray-700 hover:bg-gray-200"
                  }`}
                onClick={() => setFiltro("MENSAJE")}
              >
                Mensajes ({notificaciones.filter(n => n.tipo === "MENSAJE").length})
              </button>
            </div>
          </div>
        </div>

        {/* Lista de notificaciones */}
        {notificacionesFiltradas.length === 0 ? (
          <div className="bg-white rounded-2xl shadow-lg p-12 text-center">
            <div className="text-6xl mb-6">📭</div>
            <h3 className="text-2xl font-bold text-gray-700 mb-3">
              {filtro !== "TODAS" ? "No hay notificaciones con este filtro" : "¡No hay notificaciones!"}
            </h3>
            <p className="text-gray-500 mb-8">
              {filtro !== "TODAS"
                ? "Prueba con otro filtro o revisa todas las notificaciones"
                : "Estás al día con todas tus actividades"}
            </p>
            {filtro !== "TODAS" && (
              <button
                onClick={() => setFiltro("TODAS")}
                className="px-6 py-3 bg-gradient-to-r from-blue-600 to-purple-600 text-white rounded-xl font-semibold hover:from-blue-700 hover:to-purple-700 transition duration-200"
              >
                Ver todas las notificaciones
              </button>
            )}
          </div>
        ) : (
          <div className="space-y-4">
            {/* Estadísticas */}
            <div className="bg-gradient-to-r from-blue-50 to-indigo-50 rounded-2xl p-4 shadow-sm">
              <div className="flex flex-wrap items-center justify-between">
                <p className="text-gray-700 font-medium">
                  Mostrando <span className="font-bold text-blue-600">{notificacionesFiltradas.length}</span> de{" "}
                  <span className="font-bold text-gray-800">{notificaciones.length}</span> notificaciones
                </p>
                {contarNoLeidas() > 0 && (
                  <div className="flex items-center">
                    <div className="h-2 w-2 bg-red-500 rounded-full animate-pulse mr-2"></div>
                    <span className="text-red-600 font-semibold">{contarNoLeidas()} sin leer</span>
                  </div>
                )}
              </div>
            </div>

            {/* Notificaciones */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              {notificacionesFiltradas.map(notif => (
                <div
                  key={notif.id}
                  className={`bg-white rounded-2xl shadow-lg overflow-hidden transition-all duration-300 transform hover:-translate-y-1 cursor-pointer ${notif.leido ? "opacity-90 border-l-4 border-gray-300" : "border-l-4 border-blue-500"
                    }`}
                  onClick={() => !notif.leido && marcarComoLeido(notif.id)}
                >
                  <div className="p-6">
                    {/* Header */}
                    <div className="flex justify-between items-start mb-4">
                      <div className="flex items-center space-x-3">
                        <div className={`p-2 rounded-lg ${obtenerColorTipo(notif.tipo)}`}>
                          {obtenerIconoTipo(notif.tipo)}
                        </div>
                        <div>
                          <span className={`text-sm font-semibold px-3 py-1 rounded-full border ${obtenerColorTipo(notif.tipo)}`}>
                            {obtenerTipoTexto(notif.tipo)}
                          </span>
                        </div>
                      </div>
                      <div className="flex items-center space-x-2">
                        <span className="text-sm text-gray-500 font-medium">
                          {formatFecha(notif.fechaEnvio)}
                        </span>
                        {!notif.leido && (
                          <div className="h-2 w-2 bg-blue-500 rounded-full animate-pulse"></div>
                        )}
                      </div>
                    </div>

                    {/* Mensaje */}
                    <div className="mb-6">
                      <p className={`text-gray-800 ${notif.leido ? "" : "font-medium"}`}>
                        {notif.mensaje}
                      </p>
                    </div>

                    {/* Footer */}
                    <div className="flex justify-between items-center pt-4 border-t border-gray-100">
                      <div className="flex items-center space-x-4">
                        <span className={`text-sm font-medium ${notif.leido ? "text-gray-500" : "text-blue-600"
                          }`}>
                          {notif.leido ? (
                            <span className="flex items-center">
                              <svg className="w-4 h-4 mr-1 text-green-500" fill="currentColor" viewBox="0 0 20 20">
                                <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                              </svg>
                              Leído
                            </span>
                          ) : (
                            <span className="flex items-center">
                              <div className="h-2 w-2 bg-blue-500 rounded-full mr-2 animate-pulse"></div>
                              No leído • Haz clic para marcar como leído
                            </span>
                          )}
                        </span>
                      </div>

                      <div className="flex space-x-2">
                        {!notif.leido && (
                          <button
                            onClick={(e) => {
                              e.stopPropagation();
                              marcarComoLeido(notif.id);
                            }}
                            className="text-sm text-blue-600 hover:text-blue-800 font-medium"
                          >
                            Marcar como leído
                          </button>
                        )}
                        {notif.tipo === "SESIONINMINENTE" && (
                          <button className="text-sm text-orange-600 hover:text-orange-800 font-medium">
                            Ver sesión →
                          </button>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* Quick actions */}
        {notificaciones.length > 0 && (
          <div className="mt-8 bg-gradient-to-r from-gray-900 to-gray-800 rounded-2xl p-6 text-white">
            <div className="flex flex-col md:flex-row items-center justify-between">
              <div>
                <h3 className="text-xl font-bold mb-2">¿Necesitas ayuda con tus notificaciones?</h3>
                <p className="text-gray-300">Configura recordatorios o contacta con soporte</p>
              </div>
              <div className="flex space-x-4 mt-4 md:mt-0">
                <button className="px-4 py-2 bg-white/20 backdrop-blur-sm rounded-xl hover:bg-white/30 transition duration-200">
                  Configurar recordatorios
                </button>
                <button className="px-4 py-2 bg-white text-gray-900 rounded-xl font-semibold hover:bg-gray-100 transition duration-200">
                  Contactar soporte
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

import { useEffect, useState } from "react";
import { getClienteMembresia } from "../../api/cliente.api";

function formatDate(date) {
  if (!date) return "-";
  return new Date(date).toLocaleDateString('es-ES', {
    day: '2-digit',
    month: 'long',
    year: 'numeric'
  });
}

export default function Membresia() {
  const [membresia, setMembresia] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getClienteMembresia()
      .then(res => {
        setMembresia(res.data);
      })
      .catch(err => {
        console.error(err);
        setError("No se pudo cargar la información de tu membresía");
      })
      .finally(() => setLoading(false));
  }, []);

  // Calcular días restantes
  const calcularDiasRestantes = (fechaFin) => {
    if (!fechaFin) return null;
    const hoy = new Date();
    const fin = new Date(fechaFin);
    const diff = fin - hoy;
    return Math.max(0, Math.ceil(diff / (1000 * 60 * 60 * 24)));
  };

  // Obtener color según estado
  const getEstadoColor = (activa, vencida) => {
    if (activa) return "bg-gradient-to-r from-green-500 to-emerald-600";
    if (vencida) return "bg-gradient-to-r from-red-500 to-rose-600";
    return "bg-gradient-to-r from-yellow-500 to-amber-600";
  };

  // Obtener icono según tipo
  const getTipoIcono = (tipoNombre) => {
    const iconos = {
      'BASICA': '🥉',
      'PREMIUM': '🥈',
      'VIP': '🥇',
      'ANUAL': '🏆',
      'MENSUAL': '📅',
      'TRIMESTRAL': '📊'
    };
    return iconos[tipoNombre?.toUpperCase()] || '🎫';
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-blue-600 mb-6"></div>
          <h3 className="text-xl font-semibold text-gray-700 mb-2">Cargando tu membresía</h3>
          <p className="text-gray-500">Obteniendo información actualizada...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center p-4">
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

  if (!membresia) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex items-center justify-center p-4">
        <div className="max-w-lg w-full text-center">
          <div className="bg-white rounded-2xl shadow-xl p-8">
            <div className="text-6xl mb-6">🏋️‍♂️</div>
            <h2 className="text-2xl font-bold text-gray-800 mb-4">No tienes una membresía activa</h2>
            <p className="text-gray-600 mb-8">Activa tu membresía para acceder a todas las funcionalidades de GymApp</p>
            <button className="bg-gradient-to-r from-blue-600 to-purple-600 text-white px-8 py-3 rounded-xl font-semibold hover:from-blue-700 hover:to-purple-700 transition duration-200">
              Adquirir Membresía
            </button>
          </div>
        </div>
      </div>
    );
  }

  const {
    fechaInicio,
    fechaFin,
    estado,
    deuda,
    activa,
    vencida,
    tipo,
    pagos
  } = membresia;

  const diasRestantes = calcularDiasRestantes(fechaFin);

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="mb-10">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">
            <span className="bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              Mi Membresía
            </span>
          </h1>
          <p className="text-gray-600">Gestiona y consulta el estado de tu suscripción</p>
        </div>

        {/* Tarjeta de estado principal */}
        <div className="mb-8">
          <div className={`${getEstadoColor(activa, vencida)} rounded-2xl p-8 text-white shadow-xl`}>
            <div className="flex flex-col lg:flex-row justify-between items-start lg:items-center">
              <div className="mb-6 lg:mb-0">
                <div className="flex items-center mb-4">
                  <span className="text-4xl mr-4">{getTipoIcono(tipo?.nombre)}</span>
                  <div>
                    <h2 className="text-2xl font-bold">Estado de Membresía</h2>
                    <p className="text-white/90">Información actualizada al día de hoy</p>
                  </div>
                </div>

                <div className="flex flex-wrap gap-4">
                  <div className="bg-white/20 backdrop-blur-sm px-4 py-2 rounded-xl">
                    <p className="text-sm opacity-90">Estado</p>
                    <p className="text-xl font-bold">{estado}</p>
                  </div>
                  <div className="bg-white/20 backdrop-blur-sm px-4 py-2 rounded-xl">
                    <p className="text-sm opacity-90">Deuda Total</p>
                    <p className="text-xl font-bold">S/. {deuda?.toFixed(2) || "0.00"}</p>
                  </div>
                  {diasRestantes !== null && (
                    <div className="bg-white/20 backdrop-blur-sm px-4 py-2 rounded-xl">
                      <p className="text-sm opacity-90">Días Restantes</p>
                      <p className="text-xl font-bold">{diasRestantes}</p>
                    </div>
                  )}
                </div>
              </div>

              <div className="bg-white/10 backdrop-blur-sm p-4 rounded-xl">
                <p className="text-sm mb-2">Acciones rápidas</p>
                <div className="flex gap-2">
                  {deuda > 0 && (
                    <button className="bg-white text-blue-600 px-4 py-2 rounded-lg font-semibold hover:bg-gray-100 transition duration-200">
                      Pagar Deuda
                    </button>
                  )}
                  <button className="bg-white/20 text-white px-4 py-2 rounded-lg font-semibold hover:bg-white/30 transition duration-200">
                    Renovar
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Información de la membresía */}
          <div className="lg:col-span-2">
            <div className="bg-white rounded-2xl shadow-lg overflow-hidden">
              <div className="bg-gray-900 px-6 py-4">
                <h3 className="text-xl font-bold text-white">Detalles de la Membresía</h3>
              </div>

              <div className="p-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  {/* Tipo de membresía */}
                  <div className="bg-blue-50 rounded-xl p-5 border border-blue-100">
                    <h4 className="font-bold text-blue-900 mb-4 flex items-center">
                      <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M9 4.804A7.968 7.968 0 005.5 4c-1.255 0-2.443.29-3.5.804v10A7.969 7.969 0 015.5 14c1.669 0 3.218.51 4.5 1.385A7.962 7.962 0 0114.5 14c1.255 0 2.443.29 3.5.804v-10A7.968 7.968 0 0014.5 4c-1.255 0-2.443.29-3.5.804V12a1 1 0 11-2 0V4.804z" />
                      </svg>
                      Tipo de Membresía
                    </h4>
                    {tipo ? (
                      <div className="space-y-3">
                        <div>
                          <p className="text-sm text-blue-600">Nombre</p>
                          <p className="text-lg font-semibold text-gray-800">{tipo.nombre}</p>
                        </div>
                        <div>
                          <p className="text-sm text-blue-600">Duración</p>
                          <p className="text-gray-800">{tipo.duracionMeses} meses</p>
                        </div>
                        <div>
                          <p className="text-sm text-blue-600">Precio</p>
                          <p className="text-2xl font-bold text-blue-700">S/. {tipo.precio?.toFixed(2)}</p>
                        </div>
                      </div>
                    ) : (
                      <p className="text-gray-500 italic">Información no disponible</p>
                    )}
                  </div>

                  {/* Fechas importantes */}
                  <div className="bg-purple-50 rounded-xl p-5 border border-purple-100">
                    <h4 className="font-bold text-purple-900 mb-4 flex items-center">
                      <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                        <path fillRule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clipRule="evenodd" />
                      </svg>
                      Período de Validez
                    </h4>
                    <div className="space-y-4">
                      <div className="flex justify-between items-center p-3 bg-white rounded-lg">
                        <div>
                          <p className="text-sm text-gray-500">Inicio</p>
                          <p className="font-semibold text-gray-800">{formatDate(fechaInicio)}</p>
                        </div>
                        <div className="text-green-600">
                          <svg className="w-6 h-6" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z" clipRule="evenodd" />
                          </svg>
                        </div>
                      </div>
                      <div className="flex justify-between items-center p-3 bg-white rounded-lg">
                        <div>
                          <p className="text-sm text-gray-500">Vencimiento</p>
                          <p className="font-semibold text-gray-800">{formatDate(fechaFin)}</p>
                        </div>
                        {diasRestantes !== null && (
                          <div className="text-center">
                            <div className={`px-3 py-1 rounded-full text-sm font-bold ${diasRestantes > 30 ? "bg-green-100 text-green-800" :
                                diasRestantes > 7 ? "bg-yellow-100 text-yellow-800" :
                                  "bg-red-100 text-red-800"
                              }`}>
                              {diasRestantes}d
                            </div>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                </div>

                {/* Estado financiero */}
                <div className="mt-6 bg-gray-50 rounded-xl p-5">
                  <h4 className="font-bold text-gray-900 mb-4">Estado Financiero</h4>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-gray-600">Saldo pendiente</p>
                      <p className={`text-3xl font-bold ${deuda > 0 ? "text-red-600" : "text-green-600"}`}>
                        S/. {deuda?.toFixed(2)}
                      </p>
                    </div>
                    {deuda > 0 ? (
                      <div className="text-center">
                        <div className="bg-red-100 text-red-800 px-4 py-2 rounded-lg font-semibold mb-2">
                          Pago pendiente
                        </div>
                        <button className="bg-red-600 text-white px-6 py-2 rounded-lg font-semibold hover:bg-red-700 transition duration-200">
                          Realizar Pago
                        </button>
                      </div>
                    ) : (
                      <div className="text-center">
                        <div className="bg-green-100 text-green-800 px-4 py-2 rounded-lg font-semibold">
                          Al día en pagos
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            </div>

            {/* Historial de pagos */}
            <div className="mt-8 bg-white rounded-2xl shadow-lg overflow-hidden">
              <div className="bg-gray-900 px-6 py-4">
                <h3 className="text-xl font-bold text-white flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M8.433 7.418c.155-.103.346-.196.567-.267v1.698a2.305 2.305 0 01-.567-.267C8.07 8.34 8 8.114 8 8c0-.114.07-.34.433-.582zM11 12.849v-1.698c.22.071.412.164.567.267.364.243.433.468.433.582 0 .114-.07.34-.433.582a2.305 2.305 0 01-.567.267z" />
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-13a1 1 0 10-2 0v.092a4.535 4.535 0 00-1.676.662C6.602 6.234 6 7.009 6 8c0 .99.602 1.765 1.324 2.246.48.32 1.054.545 1.676.662v1.941c-.391-.127-.68-.317-.843-.504a1 1 0 10-1.51 1.31c.562.649 1.413 1.076 2.353 1.253V15a1 1 0 102 0v-.092a4.535 4.535 0 001.676-.662C13.398 13.766 14 12.991 14 12c0-.99-.602-1.765-1.324-2.246A4.535 4.535 0 0011 9.092V7.151c.391.127.68.317.843.504a1 1 0 101.511-1.31c-.563-.649-1.413-1.076-2.354-1.253V5z" clipRule="evenodd" />
                  </svg>
                  Historial de Pagos
                </h3>
              </div>

              <div className="p-6">
                {pagos && pagos.length > 0 ? (
                  <div className="overflow-x-auto">
                    <table className="min-w-full divide-y divide-gray-200">
                      <thead>
                        <tr>
                          <th className="px-4 py-3 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            ID Pago
                          </th>
                          <th className="px-4 py-3 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Monto
                          </th>
                          <th className="px-4 py-3 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Fecha de Pago
                          </th>
                          <th className="px-4 py-3 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                            Estado
                          </th>
                        </tr>
                      </thead>
                      <tbody className="bg-white divide-y divide-gray-200">
                        {pagos.map((pago, index) => (
                          <tr key={pago.id} className="hover:bg-gray-50 transition duration-150">
                            <td className="px-4 py-4 whitespace-nowrap">
                              <div className="flex items-center">
                                <div className="flex-shrink-0 h-8 w-8 bg-blue-100 rounded-lg flex items-center justify-center">
                                  <span className="text-blue-600 font-bold">#{index + 1}</span>
                                </div>
                                <div className="ml-4">
                                  <div className="text-sm font-medium text-gray-900">
                                    Pago {pago.id}
                                  </div>
                                </div>
                              </div>
                            </td>
                            <td className="px-4 py-4 whitespace-nowrap">
                              <span className="text-lg font-bold text-green-600">
                                S/. {pago.monto?.toFixed(2)}
                              </span>
                            </td>
                            <td className="px-4 py-4 whitespace-nowrap">
                              <div className="text-sm text-gray-900">{formatDate(pago.fechaPago)}</div>
                            </td>
                            <td className="px-4 py-4 whitespace-nowrap">
                              <span className="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                                Completado
                              </span>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>
                  </div>
                ) : (
                  <div className="text-center py-12">
                    <div className="text-6xl mb-4">💰</div>
                    <h4 className="text-xl font-semibold text-gray-700 mb-2">No hay pagos registrados</h4>
                    <p className="text-gray-500">No se encontraron registros de pagos para esta membresía</p>
                  </div>
                )}

                {pagos && pagos.length > 0 && (
                  <div className="mt-6 pt-6 border-t border-gray-200">
                    <div className="flex justify-between items-center">
                      <div>
                        <p className="text-sm text-gray-600">Total pagado</p>
                        <p className="text-2xl font-bold text-green-600">
                          S/. {pagos.reduce((sum, pago) => sum + pago.monto, 0).toFixed(2)}
                        </p>
                      </div>
                      <button className="px-6 py-2 bg-blue-600 text-white rounded-lg font-semibold hover:bg-blue-700 transition duration-200">
                        Descargar Comprobantes
                      </button>
                    </div>
                  </div>
                )}
              </div>
            </div>
          </div>

          {/* Panel lateral - Resumen y acciones */}
          <div>
            {/* Resumen rápido */}
            <div className="bg-white rounded-2xl shadow-lg p-6 mb-6">
              <h3 className="font-bold text-gray-900 mb-4">Resumen</h3>
              <div className="space-y-4">
                <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                  <span className="text-gray-600">Estado</span>
                  <span className={`px-3 py-1 rounded-full text-sm font-bold ${activa ? "bg-green-100 text-green-800" :
                      vencida ? "bg-red-100 text-red-800" :
                        "bg-yellow-100 text-yellow-800"
                    }`}>
                    {activa ? "Activa" : vencida ? "Vencida" : "Inactiva"}
                  </span>
                </div>
                <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                  <span className="text-gray-600">Pagos registrados</span>
                  <span className="font-bold text-blue-600">{pagos?.length || 0}</span>
                </div>
                <div className="flex justify-between items-center p-3 bg-gray-50 rounded-lg">
                  <span className="text-gray-600">Duración total</span>
                  <span className="font-bold text-purple-600">{tipo?.duracionMeses || 0} meses</span>
                </div>
              </div>
            </div>

            {/* Acciones */}
            <div className="bg-gradient-to-br from-blue-50 to-indigo-50 rounded-2xl shadow-lg p-6 mb-6 border border-blue-100">
              <h3 className="font-bold text-blue-900 mb-4">Acciones</h3>
              <div className="space-y-3">
                <button className="w-full bg-blue-600 text-white px-4 py-3 rounded-xl font-semibold hover:bg-blue-700 transition duration-200 flex items-center justify-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  Renovar Membresía
                </button>
                <button className="w-full bg-white text-blue-600 px-4 py-3 rounded-xl font-semibold hover:bg-gray-50 transition duration-200 border border-blue-200 flex items-center justify-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                  </svg>
                  Descargar Contrato
                </button>
                <button className="w-full bg-white text-gray-700 px-4 py-3 rounded-xl font-semibold hover:bg-gray-50 transition duration-200 border border-gray-300 flex items-center justify-center">
                  <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192l-3.536 3.536M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-5 0a4 4 0 11-8 0 4 4 0 018 0z" />
                  </svg>
                  Soporte
                </button>
              </div>
            </div>

            {/* Próximo vencimiento */}
            {diasRestantes !== null && diasRestantes <= 30 && (
              <div className="bg-gradient-to-br from-amber-50 to-orange-50 rounded-2xl shadow-lg p-6 border border-amber-200">
                <div className="flex items-center mb-4">
                  <div className="bg-amber-100 p-3 rounded-xl mr-4">
                    <svg className="w-6 h-6 text-amber-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                  </div>
                  <div>
                    <h4 className="font-bold text-amber-900">Próximo vencimiento</h4>
                    <p className="text-amber-700 text-sm">Tu membresía está por vencer</p>
                  </div>
                </div>
                <div className="text-center mb-4">
                  <div className="text-3xl font-bold text-amber-700 mb-2">{diasRestantes} días</div>
                  <p className="text-amber-600">Te quedan {diasRestantes} días para renovar</p>
                </div>
                <button className="w-full bg-amber-600 text-white px-4 py-3 rounded-xl font-semibold hover:bg-amber-700 transition duration-200">
                  Renovar Ahora
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

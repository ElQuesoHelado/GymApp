import { useEffect, useState } from "react";
import { getClienteMembresia } from "../../api/cliente.api";

function formatDate(date) {
  if (!date) return "-";
  return new Date(date).toLocaleDateString('es-ES', {
    day: '2-digit',
    month: '2-digit',
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
        setError("No se pudo cargar la información de la membresía");
      })
      .finally(() => setLoading(false));
  }, []);

  // Estado de carga
  if (loading) {
    return (
      <div className="flex items-center justify-center py-12">
        <div className="text-center">
          <div className="inline-block animate-spin rounded-full h-10 w-10 border-t-2 border-b-2 border-blue-600 mb-4"></div>
          <p className="text-gray-600">Cargando información de membresía...</p>
        </div>
      </div>
    );
  }

  // Estado de error
  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-lg p-6 text-center">
        <div className="text-red-600 font-semibold mb-2">Error</div>
        <p className="text-red-700">{error}</p>
      </div>
    );
  }

  // Sin membresía
  if (!membresia) {
    return (
      <div className="bg-yellow-50 border border-yellow-200 rounded-lg p-8 text-center">
        <div className="text-yellow-700 font-bold text-lg mb-2">
          Sin membresía activa
        </div>
        <p className="text-gray-700">
          Actualmente no tienes una membresía registrada.
        </p>
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

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      {/* Header */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">Mi Membresía</h1>
        <p className="text-gray-600">Gestiona y visualiza el estado de tu membresía</p>
      </div>

      {/* Tarjeta de estado principal */}
      <div className="bg-white rounded-xl shadow-lg p-6 mb-8">
        <div className="flex flex-col md:flex-row md:items-center justify-between mb-6">
          <div>
            <h2 className="text-xl font-semibold text-gray-800 mb-2">Estado de Membresía</h2>
            <div className="flex items-center">
              <div className={`w-3 h-3 rounded-full mr-3 ${activa ? 'bg-green-500' : vencida ? 'bg-red-500' : 'bg-yellow-500'}`}></div>
              <span className={`text-lg font-bold ${activa ? 'text-green-700' : vencida ? 'text-red-700' : 'text-yellow-700'}`}>
                {estado}
              </span>
            </div>
          </div>

          <div className="mt-4 md:mt-0">
            <div className={`px-4 py-2 rounded-lg ${deuda > 0 ? 'bg-red-50 text-red-700' : 'bg-green-50 text-green-700'}`}>
              <span className="font-semibold">Deuda: S/. {deuda}</span>
            </div>
          </div>
        </div>

        {/* Información de fechas */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
          <div className="bg-gray-50 rounded-lg p-4">
            <div className="text-sm text-gray-500 mb-1">Fecha de Inicio</div>
            <div className="text-lg font-semibold text-gray-800">{formatDate(fechaInicio)}</div>
          </div>

          <div className="bg-gray-50 rounded-lg p-4">
            <div className="text-sm text-gray-500 mb-1">Fecha de Fin</div>
            <div className="text-lg font-semibold text-gray-800">{formatDate(fechaFin)}</div>
          </div>
        </div>

        {/* Tipo de membresía */}
        {tipo && (
          <div className="border-t pt-6">
            <h3 className="text-lg font-semibold text-gray-800 mb-4">Detalles del Plan</h3>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
              <div className="bg-blue-50 rounded-lg p-4">
                <div className="text-sm text-blue-600 mb-1">Nombre del Plan</div>
                <div className="font-semibold text-gray-800">{tipo.nombre}</div>
              </div>

              <div className="bg-blue-50 rounded-lg p-4">
                <div className="text-sm text-blue-600 mb-1">Duración</div>
                <div className="font-semibold text-gray-800">{tipo.duracionMeses} meses</div>
              </div>

              <div className="bg-blue-50 rounded-lg p-4">
                <div className="text-sm text-blue-600 mb-1">Precio</div>
                <div className="font-semibold text-gray-800">S/. {tipo.precio}</div>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Historial de pagos */}
      <div className="bg-white rounded-xl shadow-lg p-6">
        <h3 className="text-xl font-semibold text-gray-800 mb-6">Historial de Pagos</h3>

        {pagos && pagos.length > 0 ? (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    ID Pago
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Monto
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Fecha de Pago
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    Estado
                  </th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {pagos.map(pago => (
                  <tr key={pago.id} className="hover:bg-gray-50">
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                      #{pago.id}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-800 font-semibold">
                      S/. {pago.monto}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                      {formatDate(pago.fechaPago)}
                    </td>
                    <td className="px-6 py-4 whitespace-nowrap">
                      <span className="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                        Completado
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>

            {/* Resumen de pagos */}
            <div className="mt-6 pt-6 border-t">
              <div className="flex justify-between items-center">
                <div className="text-gray-600">
                  Mostrando {pagos.length} {pagos.length === 1 ? 'pago' : 'pagos'}
                </div>
                <div className="text-gray-800 font-semibold">
                  Total pagado: S/. {pagos.reduce((sum, pago) => sum + pago.monto, 0)}
                </div>
              </div>
            </div>
          </div>
        ) : (
          <div className="text-center py-8">
            <div className="text-gray-400 mb-2">
              <svg className="w-16 h-16 mx-auto" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
              </svg>
            </div>
            <p className="text-gray-600 font-medium mb-1">No hay pagos registrados</p>
            <p className="text-gray-500 text-sm">Los pagos realizados aparecerán aquí</p>
          </div>
        )}
      </div>

      {/* Información adicional */}
      <div className="mt-6 text-sm text-gray-500 text-center">
        <p>Para consultas sobre tu membresía, contacta con nuestro soporte.</p>
      </div>
    </div>
  );
}

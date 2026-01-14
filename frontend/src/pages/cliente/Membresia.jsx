import { useEffect, useState } from "react";
import { getClienteMembresia } from "../../api/cliente.api";

function formatDate(date) {
  if (!date) return "-";
  return new Date(date).toLocaleDateString();
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
        setError("No se pudo cargar la membresía");
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Cargando membresía...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!membresia) return <p>No tienes una membresía registrada</p>;

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
    <div>
      <h2>Membresía</h2>

      {/* Estado */}
      <p>
        Estado:{" "}
        <strong
          style={{
            color: activa ? "green" : vencida ? "red" : "orange"
          }}
        >
          {estado}
        </strong>
      </p>

      {/* Datos generales */}
      <div style={{ marginBottom: 20 }}>
        <p>Fecha inicio: {formatDate(fechaInicio)}</p>
        <p>Fecha fin: {formatDate(fechaFin)}</p>
        <p>
          Deuda:{" "}
          <strong style={{ color: deuda > 0 ? "red" : "green" }}>
            S/. {deuda}
          </strong>
        </p>
      </div>

      {/* Tipo de membresía */}
      {tipo && (
        <div style={{ marginBottom: 20 }}>
          <h3>Tipo de Membresía</h3>
          <p>Nombre: {tipo.nombre}</p>
          <p>Duración: {tipo.duracionMeses} meses</p>
          <p>Precio: S/. {tipo.precio}</p>
        </div>
      )}

      {/* Pagos */}
      <div>
        <h3>Pagos realizados</h3>

        {pagos && pagos.length > 0 ? (
          <table border="1" cellPadding="8">
            <thead>
              <tr>
                <th>ID</th>
                <th>Monto</th>
                <th>Fecha</th>
              </tr>
            </thead>
            <tbody>
              {pagos.map(pago => (
                <tr key={pago.id}>
                  <td>{pago.id}</td>
                  <td>S/. {pago.monto.toFixed(2)}</td>
                  <td>{formatDate(pago.fechaPago)}</td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No hay pagos registrados</p>
        )}
      </div>
    </div>
  );
}


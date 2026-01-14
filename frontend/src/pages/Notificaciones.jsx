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
    switch (tipo) {
      case "IMPORTANTE":
        return "❗";
      case "SESIONINMINENTE":
        return "⏰";
      case "MENSAJE":
        return "💬";
      default:
        return "📢";
    }
  };

  const obtenerColorTipo = (tipo) => {
    switch (tipo) {
      case "IMPORTANTE":
        return "#dc3545"; // Rojo
      case "SESIONINMINENTE":
        return "#ff9800"; // Naranja
      case "MENSAJE":
        return "#2196f3"; // Azul
      default:
        return "#6c757d"; // Gris
    }
  };

  const obtenerTipoTexto = (tipo) => {
    switch (tipo) {
      case "IMPORTANTE":
        return "Importante";
      case "SESIONINMINENTE":
        return "Sesión Inminente";
      case "MENSAJE":
        return "Mensaje";
      default:
        return tipo;
    }
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

    if (diffMin < 60) {
      return `Hace ${diffMin} minuto${diffMin !== 1 ? 's' : ''}`;
    } else if (diffHoras < 24) {
      return `Hace ${diffHoras} hora${diffHoras !== 1 ? 's' : ''}`;
    } else if (diffDias === 1) {
      return "Ayer";
    } else if (diffDias < 7) {
      return `Hace ${diffDias} día${diffDias !== 1 ? 's' : ''}`;
    } else {
      return fecha.toLocaleDateString('es-ES', {
        day: 'numeric',
        month: 'short',
        year: 'numeric'
      });
    }
  };

  if (loading) return (
    <div className="notificaciones-loading">
      <div className="spinner"></div>
      <p>Cargando notificaciones...</p>
    </div>
  );

  if (error) return (
    <div className="notificaciones-error">
      <p>❌ {error}</p>
    </div>
  );

  return (
    <div className="notificaciones-container">
      <div className="notificaciones-header">
        <h2 className="notificaciones-title">
          Notificaciones
          {contarNoLeidas() > 0 && (
            <span className="badge-no-leidas">{contarNoLeidas()}</span>
          )}
        </h2>

        <div className="notificaciones-actions">
          {contarNoLeidas() > 0 && (
            <button
              onClick={marcarTodasComoLeidas}
              className="btn-marcar-todas"
            >
              Marcar todas como leídas
            </button>
          )}
        </div>
      </div>

      <div className="filtros-container">
        <div className="filtros">
          <button
            className={`filtro-btn ${filtro === "TODAS" ? "active" : ""}`}
            onClick={() => setFiltro("TODAS")}
          >
            Todas ({notificaciones.length})
          </button>
          <button
            className={`filtro-btn ${filtro === "NO_LEIDAS" ? "active" : ""}`}
            onClick={() => setFiltro("NO_LEIDAS")}
          >
            No leídas ({contarNoLeidas()})
          </button>
          <button
            className={`filtro-btn ${filtro === "LEIDAS" ? "active" : ""}`}
            onClick={() => setFiltro("LEIDAS")}
          >
            Leídas ({notificaciones.length - contarNoLeidas()})
          </button>
          <button
            className={`filtro-btn ${filtro === "IMPORTANTE" ? "active" : ""}`}
            onClick={() => setFiltro("IMPORTANTE")}
          >
            Importantes ({notificaciones.filter(n => n.tipo === "IMPORTANTE").length})
          </button>
        </div>
      </div>

      {notificacionesFiltradas.length === 0 ? (
        <div className="no-notificaciones">
          <p>📭 No hay notificaciones</p>
          <p className="subtitulo">
            {filtro !== "TODAS"
              ? "No hay notificaciones con este filtro"
              : "¡Todo al día!"}
          </p>
        </div>
      ) : (
        <div className="notificaciones-list">
          {notificacionesFiltradas.map(notif => (
            <div
              key={notif.id}
              className={`notificacion-card ${notif.leido ? "leida" : "no-leida"}`}
              onClick={() => !notif.leido && marcarComoLeido(notif.id)}
            >
              <div className="notificacion-header">
                <div className="tipo-container">
                  <span
                    className="tipo-icono"
                    style={{ backgroundColor: obtenerColorTipo(notif.tipo) }}
                  >
                    {obtenerIconoTipo(notif.tipo)}
                  </span>
                  <span className="tipo-texto">
                    {obtenerTipoTexto(notif.tipo)}
                  </span>
                </div>
                <span className="notificacion-fecha">
                  {formatFecha(notif.fechaEnvio)}
                </span>
              </div>

              <div className="notificacion-mensaje">
                {notif.mensaje}
              </div>

              <div className="notificacion-footer">
                {!notif.leido && (
                  <span className="estado-no-leido">
                    ● No leído
                  </span>
                )}
                {notif.leido && (
                  <span className="estado-leido">
                    ✓ Leído
                  </span>
                )}
              </div>
            </div>
          ))}
        </div>
      )}

      {notificaciones.length > 0 && (
        <div className="notificaciones-stats">
          <p>
            Mostrando {notificacionesFiltradas.length} de {notificaciones.length} notificaciones
            {contarNoLeidas() > 0 && ` • ${contarNoLeidas()} sin leer`}
          </p>
        </div>
      )}
    </div>
  );
}

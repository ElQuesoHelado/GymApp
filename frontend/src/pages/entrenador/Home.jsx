import { useEffect, useState } from "react";
import { getEntrenadorHome } from "../../api/entrenador.api";

export default function ClienteHome() {
  const [data, setData] = useState(null);

  useEffect(() => {
    getEntrenadorHome()
      .then(res => setData(res.data))
      .catch(err => {
        console.error(err);
      });
  }, []);

  if (!data) return <p>Cargando...</p>;

  return (
    <div>
      {/* {data} */}
      <h3>Bienvenido {data.nombre}</h3>
      <h4>Id {data.id}</h4>
      <h4>Email {data.email}</h4>
      <h4>DNI {data.dni}</h4>
      <h4>Tipo {data.tipo}</h4>
    </div>
  );
}

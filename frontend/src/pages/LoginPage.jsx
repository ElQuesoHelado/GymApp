import { useState } from "react";
import { login, me } from "../api/auth.api";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const { loginUser } = useAuth();


  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");

    try {
      await login(username, password);

      const { data: usuario } = await me();

      loginUser(usuario);
      navigate("/dashboard");

    } catch (err) {
      if (err.response?.status === 401) {
        setError("Usuario o contraseña incorrectos");
      } else {
        setError("Error inesperado");
      }
    }
  };


  return (
    <div style={{ maxWidth: 400, margin: "100px auto" }}>
      <h2>Iniciar sesión</h2>

      <form onSubmit={handleSubmit}>
        <input
          placeholder="Usuario"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit">Entrar</button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
}

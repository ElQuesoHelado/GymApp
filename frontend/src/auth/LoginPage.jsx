import { useState } from "react";
import { login } from "../api/auth.api";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const usuario = await login(username, password);

            switch (usuario.tipo) {
                case "ADMIN":
                    navigate("/admin");
                    break;
                case "CLIENTE":
                    navigate("/cliente");
                    break;
                case "ENTRENADOR":
                    navigate("/entrenador");
                    break;
                default:
                    setError("Rol desconocido");
            }
        } catch (err) {
            setError(err.message);
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

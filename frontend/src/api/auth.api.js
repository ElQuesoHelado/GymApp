const BASE_URL = "http://localhost:8080/api/auth";

/**
 * @param {string} username
 * @param {string} password
 */
export async function login(username, password) {
  const response = await fetch(`${BASE_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include", // MUY IMPORTANTE para sesiones
    body: JSON.stringify({ username, password }),
  });

  if (!response.ok) {
    throw new Error("Credenciales incorrectas");
  }

  return response.json(); // devuelve UsuarioDTO
}


export async function me() {
  const response = await fetch(`${BASE_URL}/me`, {
    credentials: "include",
  });

  if (!response.ok) {
    throw new Error("No autenticado");
  }

  return response.json();
}


export async function logout() {
  await fetch(`${BASE_URL}/logout`, {
    method: "POST",
    credentials: "include",
  });
}

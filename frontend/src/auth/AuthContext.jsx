import { createContext, useContext, useState } from "react";

const AuthContext = createContext(undefined);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);

  const loginUser = (usuario) => setUser(usuario);
  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, loginUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (context === undefined) {
    throw new Error("useAuth debe usarse dentro de AuthProvider");
  }

  return context;
}


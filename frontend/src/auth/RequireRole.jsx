import { Navigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

export default function RequireRole({ allowedRoles, children }) {
  const { user } = useAuth();

  if (!user) return <Navigate to="/" replace />;

  if (!allowedRoles.includes(user.tipo)) {
    return <Navigate to="/dashboard" replace />;
  }

  return children;
}

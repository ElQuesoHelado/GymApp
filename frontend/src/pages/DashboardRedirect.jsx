import { Navigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export default function DashboardRedirect() {
  const { user } = useAuth();

  if (!user) return <Navigate to="/" replace />;

  switch (user.tipo) {
    case "CLIENTE":
      return <Navigate to="/dashboard/cliente" replace />;
    case "ENTRENADOR":
      return <Navigate to="/dashboard/entrenador" replace />;
    case "ADMIN":
      return <Navigate to="/dashboard/admin" replace />;
    default:
      return <Navigate to="/" replace />;
  }
}

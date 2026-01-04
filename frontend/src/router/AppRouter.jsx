import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "../auth/LoginPage";
import AdminLayout from "../layouts/AdminLayout";
import ClienteLayout from "../layouts/ClienteLayout";
import EntrenadorLayout from "../layouts/EntrenadorLayout";

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />

        <Route path="/admin/*" element={<AdminLayout />} />
        <Route path="/cliente/*" element={<ClienteLayout />} />
        <Route path="/entrenador/*" element={<EntrenadorLayout />} />
      </Routes>
    </BrowserRouter>
  );
}

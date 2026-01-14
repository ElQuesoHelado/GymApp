import axiosInstance from "./axiosConfig";

export const getSesiones = () =>
  axiosInstance.get("/sesiones");

export const cancelarSesion = () => { }
// axiosInstance.get("/sesiones");
export const crearSesion = () => { }
export const listarSesionesPorUsuario = () => { }


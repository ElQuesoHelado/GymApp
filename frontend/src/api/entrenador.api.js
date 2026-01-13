import axiosInstance from "./axiosConfig";

export const getEntrenadorHome = () =>
  axiosInstance.get("/entrenador/home");

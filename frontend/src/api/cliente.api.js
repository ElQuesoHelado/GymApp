import axiosInstance from "./axiosConfig";

export const getClienteHome = () =>
  axiosInstance.get("/cliente/home");

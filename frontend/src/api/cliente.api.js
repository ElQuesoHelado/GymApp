import axiosInstance from "./axiosConfig";

export const getClienteHome = () =>
  axiosInstance.get("/cliente/home");

export const getClienteMembresia = () =>
  axiosInstance.get("/cliente/membresia");

export const getClientePlan = () =>
  axiosInstance.get("/cliente/plan");

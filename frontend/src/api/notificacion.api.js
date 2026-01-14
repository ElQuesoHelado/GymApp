import axiosInstance from "./axiosConfig";

export const getNotificaciones = () =>
  axiosInstance.get("/notificaciones");

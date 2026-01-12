import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

console.log("API_URL:", import.meta.env.VITE_API_URL);

const axiosInstance = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

axiosInstance.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      const status = error.response.status;

      if (status === 401) {
        window.location.href = "/";
      }

      if (status === 403) {
        alert("No tienes permisos para acceder a este recurso");
      }
    }

    return Promise.reject(error);
  }
);

export default axiosInstance;

import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://backend:8080/api';
//const API_URL = "http://localhost:8080/api"

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

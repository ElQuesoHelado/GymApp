import axios from './axiosConfig';

const BASE_URL = 'http://localhost:8080/sesiones';

export const listarSesionesPorUsuario = (usuarioId) => axios.get(`${BASE_URL}/usuario/${usuarioId}`);

export const crearSesion = (sesion) => axios.post(BASE_URL, sesion);

export const cancelarSesion = (id) => axios.put(`${BASE_URL}/${id}/cancelar`);
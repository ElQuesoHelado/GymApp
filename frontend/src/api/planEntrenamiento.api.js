import axios from './axiosConfig';

const PLANES_API = 'http://localhost:8081/api/planes-entrenamiento';
const RUTINAS_API = 'http://localhost:8081/api/rutinas';
const EJERCICIOS_API = 'http://localhost:8081/api/ejercicios';

// Planes de Entrenamiento
export const listarPlanes = () => axios.get(PLANES_API);
export const getPlan = (id) => axios.get(`${PLANES_API}/${id}`);
export const crearPlan = (planData) => axios.post(PLANES_API, planData);
export const eliminarPlan = (id) => axios.delete(`${PLANES_API}/${id}`);

// Rutinas
export const listarRutinas = () => axios.get(RUTINAS_API);
export const getRutina = (id) => axios.get(`${RUTINAS_API}/${id}`);
export const crearRutina = (rutinaData) => axios.post(RUTINAS_API, rutinaData);
export const eliminarRutina = (id) => axios.delete(`${RUTINAS_API}/${id}`);

// Ejercicios
export const listarEjercicios = () => axios.get(EJERCICIOS_API);
export const getEjercicio = (id) => axios.get(`${EJERCICIOS_API}/${id}`);
export const crearEjercicio = (ejercicioData) => axios.post(EJERCICIOS_API, ejercicioData);
export const eliminarEjercicio = (id) => axios.delete(`${EJERCICIOS_API}/${id}`);

export const dashboardRoutes = {
  CLIENTE: [
    { label: 'Inicio', path: '/cliente', element: <ClienteHome /> },
    { label: 'Membresía', path: '/cliente/membresia', element: <Membresia /> },
    { label: 'Planes', path: '/cliente/planes', element: <PlanesCliente /> },
    { label: 'Notificaciones', path: '/cliente/notificaciones', element: <Notificaciones /> }
  ],
  ENTRENADOR: [
    { label: 'Inicio', path: '/entrenador', element: <EntrenadorHome /> },
    { label: 'Clientes', path: '/entrenador/clientes', element: <ClientesAsignados /> },
    { label: 'Planes', path: '/entrenador/planes', element: <PlanesEntrenador /> },
    { label: 'Notificaciones', path: '/entrenador/notificaciones', element: <Notificaciones /> }
  ]
};

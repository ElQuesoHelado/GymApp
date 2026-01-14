# Gimnasio App

## Integrantes:

- Alva Cornejo, Jose Javier
- Chavez Medina, Fernando Jesús
- Palomino Rivadeneyra, Misael Jesús
- Rivera Cruz, Diego Benjamín
- Supo Molina, Gerald Steve
- Zapana Pariapaza, Gonzalo Rodrigo

## Proposito

Ayudar a tanto usuarios de gimnasios, entrenadores, y administradores de gimnasios a lograr una experiencia de entrenamiento
más fluida y personalizada, maximizando la eficiencia en el uso de recursos del gimnasio. Ademas que esta app 
demuestre eficiencia entre los tipos de usuario y el funcionamiento, sin exponer datos personales, convservando los cambios
necesarios para los planes de entrenamiento y también aplicando practicas de codificación correctas.

## Actores del sistema (alto nivel)

En GymApp se identifican claramente estos actores:

- Administrador
- Entrenador
- Cliente
- Sistema (servicios automáticos / notificaciones) 

## Funcionalidades a Alto Nivel por Actor

###  1. Administrador

El Administrador gestiona la operación general del gimnasio.

#### Funcionalidades:

- Autenticarse en el sistema
- Gestionar usuarios (crear, editar, eliminar)
- Gestionar entrenadores y clientes
- Gestionar tipos de membresía
- Visualizar estadísticas del gimnasio
- Supervisar sesiones de entrenamiento
- Gestionar planes de entrenamiento
- Recibir notificaciones del sistema

### 2. Entrenador

El Entrenador administra el entrenamiento de los clientes.

#### Funcionalidades:

- Autenticarse en el sistema
- Visualizar su perfil
- Gestionar clientes asignados
- Crear y modificar planes de entrenamiento
- Programar sesiones de entrenamiento
- Consultar sesiones programadas
- Cancelar o reprogramar sesiones
- Recibir notificaciones relacionadas a sesiones

### 3. Cliente

El Cliente consume los servicios del gimnasio.

#### Funcionalidades:

- Autenticarse en el sistema
- Visualizar su perfil
- Consultar su membresía activa
- Visualizar planes de entrenamiento asignados
- Consultar sesiones de entrenamiento
- Recibir notificaciones del sistema

### 4. Sistema (Automático)

#### Funcionalidades automáticas del sistema:

- Validar credenciales de acceso
- Controlar disponibilidad de horarios
- Gestionar estados de membresías
- Enviar notificaciones automáticas
- Registrar eventos del sistema

## Users relevantes
### Entrenadores
| Usuario | Password |
|---------|----------|
| mariag  | abcdef   |
| davids  | 123abc   |
| sofiag  | welcome  |

### Clientes
| Usuario  | Password |
|----------|----------|
| joaquinf | ranger   |
| natalias | hockey   |
| ricardom | robert   |

### Admins
| Usuario   | Password |
|-----------|----------|
| ecastro   | elena    |
| dtorres   | david    |
| svillalba | sofive   |

## Diagrama casos de uso 
https://www.plantuml.com/plantuml/uml/dLNDZXit3BxFKn3RYtsmiUV2MCGnia4F0Le7pPsMkBDiDDA4bBO_JHweXnoAF89Vh9Qi32Q2QoGvpOZy-0r_-6cUQKI9QN2TeuS8CO1m_nZ1if2977odRzcFA3W0cXZaTVpJ4U3lf66WcJCf6LI2lpk0vsZUzXAIjxlWWi0Fz_VtsHp4abJJxUrjzw7h2YVygWc5Gptj79EFL4ylV1JoQ8DKmzOEx5cZ55iDXStxrMgAd1idWWRSy5JoczUiaGO4I_0pza5l01MgiHYwBdS6VUy8Rbxou54vm7OSRqhjYssAv2CRD7pyv9Sv75DyxA0yOBr-VmUBdpmRHW4bBQWVDnlsnYLBUdVt6RfPhs6nm-6GaIRuAAXq_1Spclw8v6qBVkx2kqGPdSZ71fYJsvFX0mlu4FcXv1WyQKcocBI3-d8cVE6V6EVmckp4sk1VePYIx130qV6ZnMledKCjl09Z48WyXZRbpoE5qy0MRrWJEluB1IH5zgZB1h4d9NcQDRRnxuBNv6B-e4DFciTCv_qOCYgq0Iz9imfGOA3X8AJ7ZzXYJcjnpLyRsy7_i9rxiJMHd-QEfdFNetVe3Rdl3dz30gGHRG1BNrJImONwwXBlgS_ooRxZFp6SWqViJqE4Pf-lSDI1bZXeTT3wLwj6Yp2Jztd2GjDwB2-ww_0lZhv1RvLxFz41bi0Bk5V1vZs-yk7IOiSQoyrYpjkXkRzDodKRUg7SCFgDnB3XHXUNWtv97ByrPeVE99SbYox8QJ0Axn8DQ55RANvvOJRgIEURLPSjRYfUQ0nIQwwHnqzb2aewNKFJeOFfjMOQ_0FBCDDsKru3BuhuMlIUHWczuDVIbueiwnWy7zYnHLigGOVwFO6FGKhxhbn_4q-xMtD7_S5bQ_dfP8gvO8hgIX9bNo_BxaFtZBnDW_iF
![image](./assets/modelo_casos_uso.png)

[//]: # (![image]&#40;https://github.com/user-attachments/assets/69462cb0-e1b8-4ef0-8be7-0fe12818f62f&#41;)

![image](./assets/Presentation.png)
![image](./assets/Servicios.png)
![image](./assets/Dominio.png)
![image](./assets/Membresias.png)
![image](./assets/Usuarios.png)
![image](./assets/Notificaciones.png)
![image](./assets/Sesiones.png)
![image](./assets/PlanesEntrenamiento.png)
![image](./assets/Repositorio.png)

## Estilos de Programación y Arquitectura

### 1. Arquitectura Monolítica (en Capas)

#### Descripción
La aplicación sigue una **arquitectura monolítica en capas**. Esto significa que todo el código del proyecto (desde la interfaz de usuario -simulada por el controlador API-, la lógica de negocio, hasta el acceso a datos) reside en una única base de código y se despliega como una sola unidad.

A pesar de ser un monolito, internamente el código está bien organizado en distintas **capas lógicas** para separar las responsabilidades:

* **Capa de Presentación (`com.soft.gymapp.presentation.controladores`):** Maneja las solicitudes HTTP entrantes y las respuestas salientes. Es responsable de la comunicación con el cliente.
* **Capa de Servicio (Lógica de Negocio) (`com.soft.gymapp.servicio` y `com.soft.gymapp.servicio.impl`):** Contiene las reglas de negocio, validaciones complejas y la orquestación de operaciones. Aquí reside la "inteligencia" de la aplicación.
* **Capa de Repositorio (Acceso a Datos) (`com.soft.gymapp.repositorio` y `com.soft.gymapp.repositorio.sqlite`):** Se encarga de la interacción con la fuente de datos (en este ejemplo, una simulación en memoria). Define las operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
* **Capa de Dominio (`com.soft.gymapp.dominio`):** Contiene las entidades de negocio (`Usuario`, `CuentaUsuario`, `Notificacion`) que representan los conceptos clave de la aplicación.

#### ¿Por qué esta elección?
Esta arquitectura es ideal para proyectos pequeños a medianos debido a su simplicidad en el desarrollo inicial, pruebas y despliegue. Mantiene una buena separación de preocupaciones internas sin la complejidad añadida de la comunicación entre múltiples servicios distribuidos.

#### Dónde verlo en el código:
* `src/main/java/com/soft/gymapp/presentation/controladores/UsuarioController.java`
* `src/main/java/com/soft/gymapp/servicio/UsuarioService.java` y `src/main/java/com/soft/gymapp/servicio/impl/UsuarioServiceImpl.java`
* `src/main/java/com/soft/gymapp/repositorio/UsuarioRepositorio.java` y `src/main/java/com/soft/gymapp/repositorio/sqlite/UsuarioRepositoriolmpl.java`
* `src/main/java/com/soft/gymapp/dominio/usuarios/Usuario.java` y `CuentaUsuario.java`

---

### 2. Estilo de Programación: Cookbook (Libro de Recetas)

#### Descripción
El estilo "Cookbook" (Libro de Recetas) es un patrón de diseño que consiste en descomponer un proceso o tarea compleja en una serie de **"recetas" o "pasos" más pequeños, atómicos y bien definidos**. Cada "receta" es un método auxiliar (a menudo privado) que realiza una parte específica de la tarea general. El método principal actúa como el "chef", que orquesta estas recetas en la secuencia correcta para completar la "comida" (la tarea completa).

Este estilo mejora la legibilidad, la mantenibilidad y la reusabilidad del código al hacer que cada paso sea explícito y fácil de entender.

#### ¿Dónde y cómo se aplica?

##### a) En la Capa de Servicio (`UsuarioServiceImpl`) - Lógica de Negocio:

Se ha aplicado el estilo Cookbook en el método `registrarUsuario` dentro de `UsuarioServiceImpl.java`. Este proceso complejo se divide en las siguientes "recetas":

* `receta_ValidarDatosRegistro()`: Se encarga de todas las validaciones de los datos de entrada del usuario.
* `receta_HashearContrasena()`: Realiza el proceso de hashing de la contraseña proporcionada.
* `receta_CrearEntidadUsuario()`: Construye y ensambla el objeto `Usuario` (incluyendo `CuentaUsuario`) a partir de los datos validados.
* `receta_GuardarUsuario()`: Persiste el objeto `Usuario` finalizado utilizando el repositorio.

El método `registrarUsuario` orquesta estas recetas para llevar a cabo el flujo completo de registro.

**Beneficios:** La lógica de registro es muy clara, paso a paso, y cada parte es un método aislado y probado.

#### Dónde verlo en el código:
* `src/main/java/com/soft/gymapp/servicio/impl/UsuarioServiceImpl.java` (Ver `registrarUsuario` y los métodos `private receta_*`)

##### b) En la Capa de Presentación (`UsuarioController`) - Formato de Respuesta:

Aunque la lógica principal se delega al servicio, se ha incluido una pequeña "receta" en el controlador para ilustrar cómo el estilo Cookbook puede aplicarse incluso a tareas más pequeñas y repetitivas a nivel de presentación:

* `receta_FormatearRespuesta()`: Un método privado que estandariza la estructura de las respuestas JSON que se envían al cliente, asegurando consistencia en el `status`, `message`, `data` y `errors`.

**Beneficios:** Garantiza un formato de respuesta API consistente, lo que facilita el consumo por parte de los clientes y mejora la claridad del controlador al separar la lógica de formato de la lógica de delegación.

#### Dónde verlo en el código:
* `src/main/java/com/soft/gymapp/presentation/controladores/UsuarioController.java` (Ver `receta_FormatearRespuesta` y cómo se usa en los métodos POST y GET).
 1. Cookbook Style (Estilo Recetario)
Descripción: Métodos diseñados como recetas reutilizables y encapsuladas, que realizan tareas específicas sin depender del contexto externo.

Aplicación:
El método calcularCaloriasQuemadas(int duracion) representa una unidad autocontenida de lógica, fácil de reutilizar:

```java
/**
 * Calcula una estimación de calorías quemadas en base a la duración del ejercicio.
 * @param duracion duración del ejercicio en minutos
 * @return calorías quemadas (estimadas)
 */
public float calcularCaloriasQuemadas(int duracion) {
    // Por ahora se retorna la duración como valor de calorías para propósitos de ejemplo.
    return duracion;
}
```
2. Lazy-River Style (Estilo Río Perezoso)
Descripción: Procesamiento controlado y eficiente de datos, evitando cálculos innecesarios, comúnmente aplicado con estructuras como StringBuilder o flujos de datos.

Aplicación (Implícita):
Aunque no se usa explícitamente StringBuilder en esta clase, se observa una intención de procesamiento seguro y paulatino en los setters mediante validaciones antes de modificar los datos. Ejemplo:

public void setNombre(String nombre) {
    if (nombre != null && !nombre.trim().isEmpty()) {
        this.nombre = nombre.trim();
    }
}

3. Things Style (Objetos como Cosas del Dominio)
Descripción: Las clases representan fielmente elementos del dominio del problema, con atributos y comportamientos propios.

Aplicación:
La clase Ejercicio es una representación directa del concepto de ejercicio físico en el dominio del gimnasio. Cuenta con propiedades como nombre, descripcion, repeticiones, series, y una relación con Rutina:

@Entity
@Table(name = "ejercicio")
public class Ejercicio {
    @Id
    private Integer id;

    private String nombre;
    private String descripcion;
    private int repeticiones;
    private int series;

    @ManyToOne
    private Rutina rutina;

    // Métodos relevantes...
}
Esto permite un diseño orientado al dominio, claro y escalable.

## Practicas Clean Code
### Nombres
```java
public void cambiarPassword(String nuevaPassword, String passwordActual);
```

### Funciones
#### Responsabilidad unica
```java
public void cambiarPassword(String nuevoPassword, String actualPassword) {
    validarPassword(actualPassword);
    validarNuevoPassword(nuevoPassword);
}
```

### Comentarios
```java
/*
* Nueva contrasenia debe cumplir requisitos de longitud y seguridad
*/
private void validarNuevoPassword(String nuevoPassword);
```
### Estructura de Código Fuente 
#### Agrupacion de campos y metodos
```java
// Constantes
private static final int LONGITUD_MINIMA_PASSWORD = 6;

// Campos
private String username;
private String password;
private EstadoCuentaUsuario estado;

// Logica interna
private void validarNuevoPassword(String nuevoPassword);
private void validarPassword(String password);

// API
public void cambiarPassword(String nuevoPassword, String actualPassword);
public void bloquearCuenta();

// Getters/Setters
/*...*/


```

### Objetos/Estructura de Datos 
#### Uso de enums en lugar de strings
```java
public enum EstadoCuentaUsuario {
    ACTIVA, INACTIVA, BLOQUEADA
}
```
### Tratamiento de Errores
#### En validacion de credenciales
```java
if (nuevoPassword.length() < 6) {
    throw new IllegalArgumentException("Contrasenia muy corta");
}

if (nuevoPassword.equals(password)) {
    throw new IllegalArgumentException("Contrasenia identica a anterior");
}
```
### Clases
#### En cumplir el criterio de Responsabilidad única y Cohesión. Igual aplica separación de responsabilidades y la importancia de las interfaces
```java
public interface UsuarioRepositorio {
void guardar(Usuario usuario);
void actualizar(Usuario usuario);
void eliminarPorId(int id);
Optional<Usuario> buscarPorId(int id);
List<Usuario> listarTodos();
Optional<Usuario> buscarPorEmail(String email);
Optional<Usuario> buscarPorDNI(String DNI);
}
```

## Principios SOLID
### Principio de Responsabilidad Unica
Los repositorios del proyecto tienen como unica funcion el acceso a datos, junto con algunas consultas
un tanto derivadas(acceder a objeto de valor TipoMembresia desde repositorio Membresia).
```java
public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {
    List<Membresia> findByTipoNombreContainingIgnoreCase(String nombre);
    List<Membresia> findByTipoPrecioLessThan(float precioMaximo);
    List<Membresia> findByTipoDuracionDiasGreaterThanEqual(int diasMinimos);
}
```

### Principio de Abierto/Cerrado
Dicho repositorio puede extenderse a funcionalidades/consultas nuevas y mas complejas,
pero no se modifica las proporcionadas por JPA.
```java
public interface MembresiaRepositorio extends JpaRepository<Membresia, Integer> {
    List<Membresia> findByTipoNombreContainingIgnoreCase(String nombre);
    /* ... */
}
```

### Principio de Sustitucion de Liskov

### Principio de Segregacion de Interfaces

### Principio de Inversion de Dependencias
Aplicado en UsuarioServiceImpl (módulo de alto nivel), no depende de la implementación concreta de UsuarioRepositoriolmpl
(módulo de bajo nivel/detalle). En su lugar, depende de la abstracción UsuarioRepositorio a través de la inyección por
constructor (o @Autowired en este caso).
```java
// UsuarioServiceImpl (alto nivel) depende de la abstracción UsuarioRepositorio
@Autowired
private UsuarioRepositorio usuarioRepositorio; // Depende de la interfaz, no de la implementación específica
```
##  Módulos y Principales Servicios REST

Esta sección describe los principales servicios REST expuestos por el backend del sistema **GymApp**, organizados por módulos funcionales.  
Los servicios siguen una arquitectura REST y son documentables mediante **Swagger / OpenAPI**.

---

###  Módulo: Autenticación (`AuthController`)
**Propósito:** Gestionar la autenticación y la sesión de los usuarios del sistema.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| POST | `/auth/login` | username, password | Autenticación de usuario |
| POST | `/auth/logout` | — | Cierre de sesión |
| GET | `/auth/me` | — | Obtiene el usuario autenticado |

**Modelos clave:** Usuario

---

###  Módulo: Usuarios (`UsuarioController`)
**Propósito:** Gestión y consulta de información de usuarios.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/usuarios/me` | — | Obtener perfil del usuario |
| PUT | `/usuarios/me` | UsuarioDTO | Actualizar información personal |
| GET | `/usuarios` | — | Listar usuarios |

**Modelos clave:** Usuario, Rol

---

###  Módulo: Administración (`AdminController`)
**Propósito:** Funcionalidades exclusivas para el rol administrador.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/admin/dashboard` | — | Resumen general administrativo |
| GET | `/admin/usuarios` | — | Gestión de usuarios |
| POST | `/admin/usuarios` | UsuarioDTO | Crear usuario |

**Modelos clave:** Usuario, Rol

---

###  Módulo: Clientes (`ClienteController`)
**Propósito:** Servicios disponibles para clientes del gimnasio.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/cliente/membresia` | — | Consultar membresía activa |
| GET | `/cliente/sesiones` | — | Historial de sesiones |
| GET | `/cliente/planes` | — | Planes de entrenamiento asignados |

**Modelos clave:** Cliente, Membresia, Sesion, PlanEntrenamiento

---

###  Módulo: Entrenadores (`EntrenadorController`)
**Propósito:** Gestión de entrenadores y clientes asignados.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/entrenador/clientes` | — | Clientes asignados |
| POST | `/entrenador/sesiones` | SesionDTO | Registrar sesión |
| GET | `/entrenador/planes` | — | Planes creados |

**Modelos clave:** Entrenador, Cliente, Sesion, PlanEntrenamiento

---

###  Módulo: Membresías (`MembresiaController`)
**Propósito:** Gestión de membresías del gimnasio.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/membresias` | — | Listar membresías |
| POST | `/membresias` | MembresiaDTO | Crear membresía |
| PUT | `/membresias/{id}` | MembresiaDTO | Actualizar membresía |
| DELETE | `/membresias/{id}` | id | Eliminar membresía |

**Modelos clave:** Membresia, TipoMembresia, EstadoMembresia

---

###  Módulo: Sesiones (`SesionController`)
**Propósito:** Gestión de sesiones de entrenamiento.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/sesiones` | — | Listar sesiones |
| POST | `/sesiones` | SesionDTO | Crear sesión |
| PUT | `/sesiones/{id}` | SesionDTO | Modificar sesión |
| DELETE | `/sesiones/{id}` | id | Cancelar sesión |

**Modelos clave:** Sesion, Horario, Usuario

---

###  Módulo: Planes de Entrenamiento (`PlanEntrenamientoController`)
**Propósito:** Administración de planes de entrenamiento.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/planes-entrenamiento` | — | Listar planes |
| POST | `/planes-entrenamiento` | PlanDTO | Crear plan |
| PUT | `/planes-entrenamiento/{id}` | PlanDTO | Actualizar plan |
| DELETE | `/planes-entrenamiento/{id}` | id | Eliminar plan |

**Modelos clave:** PlanEntrenamiento, Rutina

---

###  Módulo: Rutinas (`RutinaRestController`)
**Propósito:** Gestión de rutinas de entrenamiento.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/rutinas` | — | Listar rutinas |
| POST | `/rutinas` | RutinaDTO | Crear rutina |
| PUT | `/rutinas/{id}` | RutinaDTO | Actualizar rutina |
| DELETE | `/rutinas/{id}` | id | Eliminar rutina |

**Modelos clave:** Rutina, Ejercicio

---

###  Módulo: Notificaciones (`NotificacionController`)
**Propósito:** Gestión de notificaciones del sistema.

| Método | Endpoint | Parámetros | Descripción |
|------|--------|-----------|------------|
| GET | `/notificaciones` | — | Listar notificaciones |
| POST | `/notificaciones` | NotificacionDTO | Crear notificación |
| PUT | `/notificaciones/{id}/leida` | id | Marcar notificación como leída |

**Modelos clave:** Notificacion, Usuario

##  Documentación de Servicios REST – Swagger / OpenAPI

El proyecto **GymApp** expone y documenta sus servicios REST utilizando el estándar **OpenAPI 3.0**, a través de la herramienta **Swagger**.  
Esta documentación permite visualizar, explorar y probar los endpoints disponibles del backend de forma interactiva.

---

###  Herramienta utilizada
- **Swagger UI**
- **OpenAPI 3.0**
- Integración con **Spring Boot**

---

###  Acceso a la documentación
Una vez levantado el backend, la documentación Swagger se encuentra disponible en:

http://localhost:8080/swagger-ui.html
o
http://localhost:8080/swagger-ui/index.html


---

###  Contenido documentado
La interfaz Swagger documenta automáticamente:

- Módulos y controladores REST
- Endpoints disponibles (GET, POST, PUT, DELETE)
- Parámetros de entrada
- Cuerpos de solicitud (Request Body)
- Respuestas HTTP
- Modelos de datos (DTOs y entidades)
- Códigos de estado HTTP

---

###  Pruebas desde Swagger
Swagger permite ejecutar pruebas directas sobre los servicios REST:

- Envío de peticiones HTTP
- Visualización de respuestas en tiempo real
- Validación de contratos de entrada y salida
- Pruebas con distintos roles de usuario (según autenticación)

---

###  Seguridad y autenticación
Los endpoints protegidos requieren autenticación previa.  
Swagger permite interactuar con estos servicios una vez que el usuario se encuentra autenticado en el sistema.

---

###  Beneficios del uso de OpenAPI
- Documentación centralizada y actualizada
- Facilita la integración frontend–backend
- Reduce errores de comunicación entre capas
- Mejora el mantenimiento y escalabilidad del sistema

---

La documentación Swagger constituye la referencia oficial de los servicios REST del proyecto **GymApp**.

## Pipeline CI/CD

El proyecto **GymApp** implementa un pipeline de **Integración Continua y Entrega Continua (CI/CD)** utilizando **Jenkins**, ejecutado sobre contenedores Docker para garantizar portabilidad, reproducibilidad y automatización del proceso de construcción, análisis, pruebas y despliegue.

---

### Entorno de Ejecución
El pipeline se ejecuta dentro de un contenedor **Docker**, con acceso al Docker del host, permitiendo:
- Construcción de imágenes Docker
- Ejecución de Docker Compose
- Reutilización de dependencias Maven
- Aislamiento del entorno de ejecución

---

### 🧩 Etapas del Pipeline

#### 1.- Clean & Checkout
- Instalación de herramientas necesarias (Java 17, Maven, Node.js, npm, Git)
- Clonación del repositorio desde GitHub

---

#### 2.- Backend Build & Tests
- Compilación del backend con Maven
- Ejecución de pruebas unitarias e integración mediante `mvn clean verify`

---

#### 3.- Frontend Build
- Instalación controlada de dependencias con `npm ci`
- Construcción del frontend con `npm run build`

---

#### 4.- Análisis Estático (SonarQube)
- Análisis de calidad del código backend
- Evaluación de bugs, code smells y duplicación
- Uso del estándar **SonarQube / OpenAPI**

---

#### 5.- Pruebas de Seguridad
Ejecución paralela de:
- **OWASP Dependency Check** para dependencias del backend
- **npm audit** para dependencias del frontend

---

#### 6.- Construcción de Imágenes Docker
- Construcción de imagen Docker del backend
- Construcción de imagen Docker del frontend con configuración dinámica

---

#### 7.- Despliegue Continuo
- Detención de contenedores previos
- Despliegue automático del sistema completo mediante **Docker Compose**

---

###  Beneficios del Pipeline CI/CD
- Automatización completa del ciclo de vida del software
- Detección temprana de errores
- Validación de seguridad y calidad
- Entrega confiable y reproducible

---

El pipeline CI/CD garantiza que **GymApp** mantenga altos estándares de calidad, seguridad y estabilidad en cada iteración del desarrollo.

### Resumen de Etapas del Pipeline CI/CD

| Etapa | Descripción | Herramientas | Resultado |
|------|------------|--------------|-----------|
| Clean & Checkout | Preparación del entorno y clonación del repositorio | Docker, Git, Java 17, Maven, Node.js | Código fuente disponible |
| Backend Build & Tests | Compilación y pruebas del backend | Maven | Backend validado |
| Frontend Build | Instalación de dependencias y build del frontend | npm, Node.js | Frontend construible |
| Análisis Estático | Evaluación de calidad del código backend | SonarQube | Métricas de calidad |
| Seguridad Backend | Análisis de vulnerabilidades en dependencias Java | OWASP Dependency Check | Reporte de seguridad |
| Seguridad Frontend | Auditoría de dependencias JavaScript | npm audit | Vulnerabilidades detectadas |
| Build Docker Images | Construcción de imágenes Docker | Docker | Imágenes listas |
| Deploy | Despliegue automático del sistema | Docker Compose | Aplicación en ejecución |

---

### Cobertura de Requisitos CI/CD

| Requisito | Implementado | Evidencia |
|---------|-------------|----------|
| Construcción automática | ✔️ | Maven / npm |
| Análisis estático | ✔️ | SonarQube |
| Pruebas unitarias | ✔️ | mvn verify |
| Pruebas funcionales | ✔️ | Build + REST |
| Pruebas de seguridad | ✔️ | OWASP, npm audit |
| Despliegue automático | ✔️ | Docker Compose |

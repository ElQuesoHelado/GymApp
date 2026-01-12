# Configuracion CI y puertos

## Puertos

| Tipo | Backend | Frontend |
| --------------- | --------------- | --------------- |
| Desarrollo | 8080 | 5173 |
| Produccion/deploy | 8081 | 3000 |
| Jenkins | 9090| |

Todos los servidores pueden estar ejecutados al mismo tiempo

## Configuracion CI

### En root para levantar jenkins+sonarqube

```sh
sudo docker compose up --build -d
```

### Configuracion Jenkins

1. En <http://localhost:9090>, password inicial, se ejecuta:

    ```sh
    sudo docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
    ```

1. Install de plugins sugeridos
1. Creacion de cuenta: (admin,admin123)
1. URL deberia ser en 9090

#### Plugins

En opciones, plugins, disponibles:

1. Docker
1. Docker Pipeline
1. SonarQube Scanner for Jenkins

#### Token SonarQube

En <http://localhost:9000/>, con cuenta (admin,admin)

1. Cambio password a: admin123
1. En administracion, security(users)
1. En tokens, generar token, nombre: jenkins-token
1. Copiar token

#### Token sonar en Jenkins

1. En configuracion, sistema
1. En Sonarqube servers
1. Add SonarQube
    - Name: SonarQube
    - Server URL: <http://sonarqube:9000>
    - Server authentication token: Add, jenkins, Kind(secret text)
        - Secret: el token
        - Id : sonarqube-token
    - Add

#### Crear pipeline

1. Home -> create a job, nombre: GymApp-CI, tipo pipeline
1. Checkmarks:
    - Github project: <https://github.com/ElQuesoHelado/GymApp>
1. Pipeline -> Pipeline script from SCM, scm git, link repo

#### Reset Docker Jenkins

```sh
sudo docker compose down
sudo docker compose up --build -d
```

### Ejecutar

1. Seleccionar GymApp-CI
1. Build Now

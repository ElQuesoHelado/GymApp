pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/ElQuesoHelado/GymApp.git'
            }
        }

        stage('Build') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'

                }
                
                dir('frontend') {
                    sh 'npm ci'  
                    sh 'npm run build' 
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir('backend') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                dir('backend') {
                    sh 'mvn test'
                }
            }
        }

        stage('Functional Tests') {
            steps {
                echo 'Ejecutando pruebas funcionales (controllers)...'
                dir('backend') {
                    sh '''
                        mvn test \
                        -Dtest=*ControllerTest \
                        -DfailIfNoTests=false
                    '''
                }
            }
        }
        stage('Performance Tests') {
            steps {
                dir('backend/performance-tests') {
                    sh '''
                    jmeter -n \
                    -t gymapp_test.jmx \
                    -l results.jtl
                    '''
                }
            }
        }
        stage('Security Tests') {
            parallel {
                stage('Backend - OWASP') {
                    steps {
                        dir('backend') {
                            sh 'mvn org.owasp:dependency-check-maven:check'
                        }
                    }
                }
                stage('Frontend - npm audit') {
                    steps {
                        dir('frontend') {
                            sh 'npm audit --audit-level=high || true'
                        }
                    }
                }
            }
        }
        stage('Empaquetamiento y Despliegue') {
            steps {
                echo 'Empaquetando aplicación con Docker y desplegando con docker-compose...'
                sh '''
                    docker-compose down
                    docker-compose build
                    docker-compose up -d
                '''
            }
        }


    }
}

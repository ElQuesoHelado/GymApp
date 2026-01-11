pipeline {
    agent {
        docker {
            image 'maven:3.9-eclipse-temurin-17'
            args '-v $HOME/.m2:/root/.m2'
            reuseNode true
        }
    }

    environment {
        SONAR_PROJECT_KEY = "gymapp"
    }

    options {
        skipDefaultCheckout(true)
        disableConcurrentBuilds()
    }

    stages {
        stage('Clean & Checkout') {
            steps {
                git url: 'https://github.com/ElQuesoHelado/GymApp.git', branch: 'master'
            }
        }

        stage('Backend Build & Tests') {
            steps {
                dir('backend') {
                    sh '''
                        mvn clean verify
                    '''
                }
            }
        }

        stage('Frontend Build') {
            agent {
                docker {
                    image 'node:20'
                    reuseNode true
                }
            }
            steps {
                dir('frontend') {
                    sh '''
                        npm ci
                        npm run build
                    '''
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    dir('backend') {
                        sh '''
                            mvn sonar:sonar \
                              -Dsonar.projectKey=$SONAR_PROJECT_KEY
                        '''
                    }
                }
            }
        }

        stage('Security Checks') {
            parallel {
                stage('Backend OWASP') {
                    steps {
                        dir('backend') {
                            sh 'mvn org.owasp:dependency-check-maven:check'
                        }
                    }
                }

                stage('Frontend Audit') {
                    agent {
                        docker {
                            image 'node:20'
                            reuseNode true
                        }
                    }
                    steps {
                        dir('frontend') {
                            sh 'npm audit --audit-level=high || true'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh '''
                    docker build -t gymapp-backend ./backend
                    docker build -t gymapp-frontend ./frontend
                '''
            }
        }

        stage('Deploy (Docker)') {
            steps {
                sh '''
                    docker-compose -f docker-compose.deploy.yml down
                    docker-compose -f docker-compose.deploy.yml up -d
                '''
            }
        }
    }
}

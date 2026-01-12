pipeline {
    agent {
        docker {
            image 'docker:latest'
            args '--privileged --network=host -v /var/run/docker.sock:/var/run/docker.sock -v $HOME/.m2:/root/.m2'
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
                sh '''
                    apk add --no-cache bash curl openjdk17 maven nodejs npm git
                '''
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
                              -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                              -Dsonar.host.url=http://localhost:9000 \
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
                            sh '''
                                mvn org.owasp:dependency-check-maven:check \
                                  -DnvdApiKey=6bb8156e-45fb-4200-8ea7-f03e806fb6cc \
                                  -DnvdApiDelay=2000
                                '''
                        }
                    }
                }

                stage('Frontend Audit') {
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
                        docker build \
                          --build-arg VITE_API_URL=http://localhost:8081/api \
                          -t gymapp-frontend ./frontend
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

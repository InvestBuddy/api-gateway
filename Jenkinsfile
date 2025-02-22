pipeline {
    agent any

    tools {
        maven 'Maven_3.9.9' //  Replace with the actual name if it's different
        jdk 'JDK_21'        // Replace with the actual name if it's different
    }

    environment {
        IMAGE_NAME = "abakhar217/api-gateway-service:api-gateway-service-${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                 // Checkout the code from the repository using the configured Git credentials
                checkout([$class: 'GitSCM',
                          branches: [[name: 'master']],
                          userRemoteConfigs: [[url: 'https://github.com/InvestBuddy/api-gateway.git', credentialsId: 'git']]])
            }
        }

        stage('Build JAR') {
           steps {
                script {
                    withEnv(["JAVA_HOME": "${tool 'JDK_21'}", "PATH+MAVEN": "${tool 'Maven_3.9.9'}/bin"]) {
                        // Run mvn clean package to build the application
                       bat 'mvn clean package -Dmaven.test.failure.ignore=true'
                    }
                }
            }
        }

        stage('Check if JAR exists') {
            steps {
                script {
                    // Ensure the JAR file exists before proceeding
                   if (!fileExists('target/api-gateway-0.0.1-SNAPSHOT.jar')) {
                       error "api-gateway-0.0.1-SNAPSHOT.jar not found! Build failed."
                   }
                }
            }
        }
       
        stage('Build and SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {  // Ensure this matches your SonarQube config name
                    bat 'mvn sonar:sonar -Dsonar.login=%SONAR_TOKEN%' // Ensure your Sonar token is available
               }
           }
        }
  
        stage('Build Docker Image') {
            steps {
                script {
                   // Build the Docker image with the .jar file inside the image
                  bat "docker build -t ${IMAGE_NAME} ."
               }
            }
        }

        stage('Push Docker Image') {
             steps {
                script {
                    // Use the credentials stored in Jenkins for Docker Hub
                   withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                       // Log in to Docker Hub
                      bat "echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD% "
                       // Push the image to Docker Hub
                       bat "docker push ${IMAGE_NAME}"
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                 script {
                    // Replace a placeholder in user-service.yml with the build number
                    if (isUnix()) {
                        sh "sed -i 's#<BUILD_NUMBER>#${BUILD_NUMBER}#g' api-gateway-service.yml"
                     } 
                     else {
                        bat "powershell -Command \"(Get-Content ${WORKSPACE}/api-gateway-service.yml) -replace '<BUILD_NUMBER>', '${BUILD_NUMBER}' | Set-Content ${WORKSPACE}/api-gateway-service.yml\""
                     }
                   withKubeConfig([credentialsId: 'kubectl']) {  // Ensure this matches your Kubernetes config name
                        if (isUnix()) {
                            sh 'kubectl apply -f api-gateway-service.yml'
                         } else {
                            bat 'kubectl apply -f api-gateway-service.yml'
                         }
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline completed. Final status: ${currentBuild.currentResult}"
           bat 'docker system prune -f'
        }
        success {
            echo "Pipeline succeeded! Build number: ${env.BUILD_NUMBER}, Job name: ${env.JOB_NAME}"
        }
        unstable {
           echo "Pipeline marked as UNSTABLE. Possible cause: Quality Gate failure or warnings."
        }
        failure {
            echo "Pipeline failed!"
            echo "Error Details: ${currentBuild.description ?: 'No detailed error provided.'}"
           script {
                currentBuild.description = "Failure occurred during ${env.STAGE_NAME}. Check logs."
            }
        }
        aborted {
            echo "Pipeline was aborted by user or timeout."
        }
    }
}

pipeline{
    agent any
    environment {
        DOCKERHUB_CREDS = credentials('dockerhub-token')
    }
    stages{
        stage('Build Image'){
            steps{
                sh 'docker build -t bpinkerton/flash-bash-api:latest .'   
            }
        }
        
        stage('Deploy Image'){
            steps{
                sh 'docker login --username=${DOCKERHUB_CREDS_USR} --password=${DOCKERHUB_CREDS_PSW}'
                sh 'docker push bpinkerton/flash-bash-api:latest'
            }
        }
    }
}

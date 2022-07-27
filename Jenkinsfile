pipeline {
    environment {
        imageName = "bpinkerton/flash-bash-api"
        dockerImage = ""
    }
    agent any
    stages {
        stage('Cloning Git'){
            steps {
                checkout scm
            }
        }
        stage('Build Image'){
            steps {
                script{
                    dockerImage = docker.build imageName   
                }
            }
        }
    }
}

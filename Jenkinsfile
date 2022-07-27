pipeline{
    agent any
    stages{
        stage('Test'){
            steps{
                echo 'Executing pipeline'
            }
        }
        stage('Build Image'){
            image = docker.build("bpinkerton/flash-bash-api", "-p 8080:8080")
        }
    }
}

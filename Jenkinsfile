pipeline{
    agent any
    stages{
        stage('Test'){
            steps{
                echo 'Executing pipeline'
            }
        }
        stage('Build Image'){
            steps {
                def image = docker.build("bpinkerton/flash-bash-api", "-p 8080:8080")
            }
        }
    }
}
pipeline{
    agent { dockerfile true }
    stages{
        stage('Build Image'){
            steps {
                def image = docker.build("bpinkerton/flash-bash-api", "-p 8080:8080")
            }
        }
    }
}
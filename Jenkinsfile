node {
    def app
    
    stage('Cloen Repo'){
        checkout scm
    }
    
    stage('Build Image){
        app = docker.build("bpinkerton/flash-bash-api")      
    }
}

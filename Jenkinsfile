node {
    checkout scm
    
    def customImage = docker.build("bpinkerton/flash-bash-api")
    customImage.push()
}

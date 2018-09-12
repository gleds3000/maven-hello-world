#!groovy

pipeline {
    agent { label 'linux_01' }
    //tools { 
      //  maven 'Maven354 3.0.5' 
     //   jdk 'jdk8' 
    //} 
    stages {
        stage('Build') {
            steps {
                echo 'Construindo..'
                script {'sh java -version'}
                 sh '''
                    mvn --version
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    def pom = readMavenPom file: 'pom.xml'
                    print pom.version
                    mvn -B versions:set -DnewVersion=${pom.version}-${BUILD_NUMBER}
                    mvn -B -Dmaven.test.skip=true clean package
                    stash name: "artifact", includes: "target/maven-hello-*.war"
                ''' 
            }
        }
        stage('Test') {
            steps {
                echo 'testando..'
            }
        }
        stage('Aprovar') {
            steps{
                echo 'Aguardar ok do supervisor'
                timeout(time:3, unit:'DAYS') {
                input 'Aprova esse deploy?'
            }
        }
        stage('Package') {
            steps {
                echo 'Empacotando....'
            }
        }
        
        stage('Nexus') {
            steps {
                echo 'Versionando e guardando....'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Implantando no Ambiente @ParamAmbiente DEV CERT HOM....'
            }
        }
    }
}

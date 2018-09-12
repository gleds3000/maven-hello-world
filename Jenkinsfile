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
                ''' 
            }
        }
        stage('Test') {
            steps {
                echo 'testando..'
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

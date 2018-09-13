#!groovy

pipeline {
    agent { label 'linux_01' }
    
    stages {
        stage('Build') {
            steps {
                echo 'Construindo..'
                //deleteDir()
               // script {'sh java -version'}
                 sh '''
                    ls
                    cd my-app
                    mvn -B versions:set -DnewVersion=${BUILD_NUMBER}
                    mvn -B -Dmaven.test.skip=true clean package
                ''' 
            }
            post {
                success {
                    stash name: 'artifact', includes: 'my-app/target/my-app*.jar'
                }
            }
            
        }
        stage('Test') {
            steps {
                echo 'testando..'
                sh '''
                    cd my-app
                    mvn test
                    '''
               // sh 'make check || true' 
               // junit '**/target/*.xml'
            }
        }
        stage('Aprovar') {
            steps{
                echo 'Aguardar ok do supervisor'
                timeout(time:3, unit:'DAYS') {
                    input 'Aprova esse deploy?'
                }    
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

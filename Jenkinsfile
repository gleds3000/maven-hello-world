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
                // timeout(time:3, unit:'DAYS') {
                 //   input 'Aprova esse deploy?'
                //}    
            }
        }
        stage('Sonar') {
            steps {
                echo 'Analisando o codigo....'
                
                sh '''
                    cd my-app
                    mvn sonar:sonar \
                    -Dsonar.host.url=http://192.168.56.111:9000 \
                    -Dsonar.login=780cf45127bd25aa23afb29e830036d32641f5ad
                '''
            }
        }
        
        stage('Nexus') {
            steps {
                echo 'Versionando e guardando....'
                sh '''
                    TIME=`date +%d%m%Y_%H%M`
                    projectname="my-app"
                    pwd
                    # cd /var/lib/jenkins/workspace/${JOB_NAME}/${projectname}
                    mkdir jarfiles
                    echo $TIME
                    
                '''
            }
        }
        stage('Deploy') {
            steps {
                echo 'Implantando no Ambiente @ParamAmbiente DEV CERT HOM....'
            }
        }
    }
}

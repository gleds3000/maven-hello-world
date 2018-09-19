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
                    #TIME=date  #`date +%d%m%Y_%H%M`
                    projectname="my-app"
                    ###leitura do POM ####
                    #pom = readMavenPom file: '${JOB_NAME}/$projectname/pom.xml'
                    #def versao = pom.version
                    #echo $versao
                    
                    #### fim da leitura do pom ####
                    pwd
                    ls
                    cd $projectname
                    if [ ! -d jarfiles ] 
                    then
                        mkdir jarfiles
                        cd jarfiles
                    else 
                        cd jarfiles
                    fi
                    echo date
                    
                    #### separa os pacotes na jarfiles ##
                    # find /var/jenkins/workspace/${JOB_NAME}/$projectname -name "*my*.jar" -not -path "./jarfiles/*" -exec cp jarfiles/;
                    find /var/jenkins/workspace/CSF/my-app/ -name "*my*.jar" -not -path "./jarfiles/*" -exec cp {} /var/jenkins/workspace/CSF/jarfiles/ \\;
                    
                    echo date
                    '''
                    echo "Inicio da subida para o nexus"
                    //  Upload no Nexus 
                    nexusArtifactUploader artifacts: [
                    [artifactId: 'my-app', classifier: '', file: '/var/jenkins/workspace/CSF/jarfiles/my-app-61.jar', type: 'jar']
                    ], 
                    credentialsId: '6fbf0166-da65-4a02-ba61-d30074b616f2', 
                    groupId: 'com.mycompany.app', 
                    nexusUrl: '192.168.56.111:8081', 
                    nexusVersion: 'nexus3', 
                    protocol: 'http', 
                    repository: 'piloto/', 
                    version: '0.0.3'
                    
                    //  fim do upload
                
            }
        }
        stage('Deploy') {
            steps {
                echo 'Implantando no Ambiente @ParamAmbiente DEV CERT HOM....'
            }
        }
    }
}

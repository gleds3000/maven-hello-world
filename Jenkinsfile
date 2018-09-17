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
                    TIME=date  #`date +%d%m%Y_%H%M`
                    projectname="my-app"
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
                    echo $TIME                    
                    find /var/jenkins/workspace/${JOB_NAME}/$projectname -name "*my*.jar" -not -path "./jarfiles/*" -exec cp -rf {} jarfiles/;
                    # cd jarfiles
                    find /var/jenkins/workspace/${JOB_NAME}/$projectname/jarfiles -name "*my*.jar" -exec basename {} .jar/; &gt;&gt; filenames
                    file=filenames
                    #Nexus Detalhes
                    Nexus_Host="http://192.168.56.111"
                    Nexus_Port="8081"
                    User="admin"
                    Passwd="admin123"
                    # URL do Repositorio
                    
                    URL=":repository/csf-my-app/"
                    groupid="com.mycompany.app"
                    artifactid="my-app"
                    #version="1.0.0-SNAPSHOT"
                    timestamp=$TIME
                    echo
                    echo " Carregar Artefatos para o Nexus"
                    echo
                    while IFS= read -r LINE; do
                        curl -v -u $User:$Passwd --upload-file $LINE.jar http://$Nexus_Host:$Nexus_Port$URL/$timestamp/$groupid/$artifactid/$LINE-$BUILD_NUMBER.jar
                    if [ $? -eq 0 ] ; then
                        echo "Instalando Artefatos - Sucesso " &gt;&gt; /tmp/jenkinslog
                        else
                        echo "Instalando pacote Nexus" 1&gt;&amp;2 &gt;&gt; /tmp/jenkinslog
                    fi
                    done &lt; "$file"
                    
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

pipeline {
    // agent => mode d'exécution 
    // any => exécution locale
    agent any
    // tools => prerequisites
    tools {
        maven 'mvn'
        jdk 'jdk17'
    }
    stages {
        // stage ('Initialize') {
        //     steps {
        //         sh '''
        //             echo "PATH = ${PATH}"
        //             echo "M2_HOME = ${M2_HOME}"
        //         '''
        //     }
        // }
        stage('test') {
            // label => selection d'un noeud "worker"
            agent {
                label 'agent'
            }
            // ici il faut enclencher le multithreading avec maven
            // on pourrait avec un design !=
            // parallel {
            //     sh 'mvn test -Dmaven.IncludeTags=Unit'
            //     sh 'mvn verify -Dmaven.ExcludeTags=Unit,E2E'
            // } 
            steps {
                sh 'mvn verify'
                stash includes: 'target/site/jacoco-both/jacoco.xml', name: 'coverage'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                    recordCoverage(tools: [[parser: 'JACOCO']],
                        id: 'jacoco', name: 'JaCoCo Coverage',
                        sourceCodeRetention: 'EVERY_BUILD') 
                }
            }
        }
        stage('Quality') {
            agent {
                label 'agent'
            }
            steps {
                withCredentials([
                    string(credentialsId: 'sonar_token', variable: 'SONAR_TOKEN'),
                ]) {
                    unstash 'coverage'
                    sh '''mvn compile sonar:sonar \
                          -Dsonar.projectKey=dev \
                          -Dsonar.host.url=http://jenkins.myusine.fr:9000 \
                          -Dsonar.login=$SONAR_TOKEN \
                          -Dsonar.java.binaries=target \
                          -Dsonar.qualitygate.wait=true \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco-both/jacoco.xml

                   '''    
                }
            }
        }
    }
}
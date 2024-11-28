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
            steps {
                sh 'mvn test'
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
    }
}
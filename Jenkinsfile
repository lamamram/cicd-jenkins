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
        stage ('Initialize') {
            when {
                expression { false }
            }
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "${M2_HOME}"
                '''
            }
        }
        stage('test') {
            // conditions d'exécutions
            when {
                expression { false }
            }
            // label => selection d'un noeud "worker"
            agent {
                label 'agent'
            }
            // ici il faut enclencher le multithreading avec maven
            // on pourrait avec un design !=
            // parallel {
            //     sh 'mvn test -Dgroups=Unit'
            //     sh 'mvn verify -Dgroups=IT'
            // } 
            steps {
                sh 'mvn verify -Dgroups="Unit,IT"'
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
            when {
                expression { false }
            }
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
        stage('Test Docker') {
            when { expression { true } }
            agent {
                docker {
                    // customisation
                    // 1. -v utiliser le dépôt de dep de maven de jenkins
                    // 2 réutiliser ce conteneur pour différent stages
                    reuseNode true
                    image 'maven:3.9.9-eclipse-temurin-17-alpine'
                    args  '''-u root -v /var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/mvn:/root/.m2 --net jenkins-net'''
                }
            }
            steps {
                sh '''
                   tar -xvzf geckodriver-v0.35.0-linux64.tar.gz -C /usr/bin/
                   chmod +x /usr/bin/geckodriver
                   mvn clean test -Dtest=CucumberTest
                   '''
                   // mvn clean test -Dgroups=E2E
            }
        }
    }
}
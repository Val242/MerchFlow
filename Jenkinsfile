pipeline {
	agent any
	tools {
	    maven "MAVEN3.9"
	    jdk "JDK21"
	}

    stages {
        stage('Fetch code') {
            steps {
                git branch: 'main', url: 'https://github.com/Val242/MerchFlow.git'
            }
        }

        stage('UNIT TEST') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn package -DskipTests'
            }
            post {
                success {
                    echo 'Archiving MerchFlow WAR...'
                    archiveArtifacts artifacts: 'target/*.war'
                }
            }
        }

        stage('Checkstyle Analysis') {
            steps {
                sh 'mvn checkstyle:checkstyle'
            }
        }

        stage('Sonar Code analysis') {
            environment {
                scannerHome = tool 'sonar8.5'
            }
            steps {
                withSonarQubeEnv('sonarserver') {
                    sh '''${scannerHome}/bin/sonar-scanner \
                    -Dsonar.projectKey=merchflow \
                    -Dsonar.projectName=MerchFlow \
                    -Dsonar.projectVersion=0.0.1-SNAPSHOT \
                    -Dsonar.sources=src/main/java \
                    -Dsonar.tests=src/test/java \
                    -Dsonar.java.binaries=target/classes \
                    -Dsonar.java.test.binaries=target/test-classes \
                    -Dsonar.junit.reportPaths=target/surefire-reports \
                    -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml'''
            }
        }
        }
    }

}
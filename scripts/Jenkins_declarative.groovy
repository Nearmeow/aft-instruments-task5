def mvn = "/var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/maven_3.8.7/bin/mvn"

pipeline {
    agent any
    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: '')
        string(name: 'TAG', defaultValue: 'chrome', description: 'Выбор браузера')
    }
    stages {
        stage('Build') {
            steps {
                sh "${mvn} clean compile"
            }
        }
        stage('Run Tests') {
            steps {
                sh "${mvn} test -Dbrowser=${params.TAG}"
            }
        }
        stage('Allure Report Generation') {
            steps {
                allure includeProperties: false,
                        jdk: '',
                        results: [[path: 'target/reports/allure-results']]
            }
        }
    }
    post {
        always {
            cleanWs notFailBuild: true
        }
    }
}
def mvn = "/var/jenkins_home/tools/hudson.tasks.Maven_MavenInstallation/3.6.3/bin/mvn"

pipeline {
    agent { label 'linux' }
    parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: '')
        string(name: 'BROWSER', defaultValue: 'chrome', description: 'браузер для запуска тестов')
    }
    stages {
        stage('Build') {
            steps {
                sh "${mvn} clean compile"
            }
        }
        stage('Run Tests') {
            steps {
                sh "${mvn} test -Dbrowser=${params.BROWSER}"
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
def mvn = "/var/lib/jenkins/tools/hudson.tasks.Maven_MavenInstallation/maven_3.8.7/bin/mvn"

node {
    stage('Checkout SCM') {
        checkout(
                [$class: 'GitSCM',
                 branches: [[name: "refs/heads/master"]],
                 userRemoteConfigs: [[url: 'https://github.com/Nearmeow/aft-instruments-task5.git']]]
        )
    }
    stage('Build') {
        sh "${mvn} clean compile"
    }
    stage('Run Tests') {
        try {
            sh "${mvn} test -Ddrowser=\"${TAG}\""
        }
        catch (Exception e) {
            echo "Test run was broken"
            throw e
        }
        finally {
            stage('Allure Report Generation') {
                allure includeProperties: false,
                        jdk: '',
                        results: [[path: 'target/reports/allure-results']]
            }
            cleanWs notFailBuild: true
        }
    }
}
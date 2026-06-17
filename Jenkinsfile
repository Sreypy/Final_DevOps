pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    tools {
        maven 'Maven3'
    }

    environment {
        EMAIL_TO = 'srengty@gmail.com'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test (SQLite)') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh '''
                ansible-playbook \
                  -i ansible/inventory.ini \
                  ansible/playbook.yml
                '''
            }
        }
    }

    post {
        failure {
            mail to: "${EMAIL_TO}",
                 subject: "Build Failed: ${env.JOB_NAME}",
                 body: "Build failed. Check Jenkins console output."
        }

        success {
            echo 'Build, test, and deployment completed successfully ✅'
        }
    }
}


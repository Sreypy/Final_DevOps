pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    tools {
        maven 'Maven3'
    }

    environment {
        MAVEN_OPTS = '-Djdk.instrument.traceUsage'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('Deploy with Ansible') {
            steps {
                // ✅ Run ansible inside the web container instead
                bat 'docker exec demo-web-1 bash -c "apt-get install -y ansible && ansible-playbook -i /app/ansible/inventory.ini /app/ansible/playbook.yml"'
            }
        }
    }

    post {
        failure {
            mail to: "srengty@gmail.com, ${env.GIT_AUTHOR_EMAIL}",
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                    Build failed!

                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Commit by: ${env.GIT_AUTHOR_NAME} (${env.GIT_AUTHOR_EMAIL})
                    Branch: ${env.GIT_BRANCH}

                    Check details at: ${env.BUILD_URL}
                """
        }

        success {
            echo 'Build, test and deployment completed successfully!'
        }
    }
}
pipeline {
    agent any

    parameters {
        choice(name: 'SuiteType', choices: ['smoke', 'sanity', 'regression'], description: 'Choose Test Suite')
        string(name: 'Branch', defaultValue: 'qa', description: 'Git Branch to Test')
        choice(name: 'Browser', choices: ['chrome', 'firefox', 'safari'], description: 'Select Browser')
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "🔁 Checking out branch: ${params.Branch}"
                checkout([$class: 'GitSCM',
                          branches: [[name: "*/${params.Branch}"]],
                          userRemoteConfigs: [[url: 'https://your-repo-url.git']]
                ])
            }
        }

        stage('Run Tests') {
            steps {
                echo "🚀 Running ${params.SuiteType} suite on ${params.Browser}"
                sh """
                    mvn clean test \
                    -DsuiteXmlFile=src/test/resources/testng/${params.SuiteType}testng.xml \
                    -Dbrowser=${params.Browser} \
                    -Dbranch=${params.Branch} \
                    -Dsuite=${params.SuiteType}
                """
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'test-output/ExtentReports/**/*.html, test-output/ExtentReports/**/*.png', allowEmptyArchive: true
                echo "📦 Reports archived from test-output/ExtentReports/"
            }
        }
    }

    post {
        always {
            echo " Pipeline execution completed"
        }
        failure {
            echo " Build failed"
        }
        success {
            echo " Build succeeded"
        }
    }
}

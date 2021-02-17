#!/bin/bash --login


pipeline {
    agent none

    stages {
        stage('Checkout') {
            agent { label 'slave-01' }
            steps {
                deleteDir()
                checkout scm
            }
        }
        stage('Config Env') {
            agent { label 'slave-01' }
            steps {
                sh 'cd fastlane'
                sh 'bundle install'
            }
        }
    }

    post {
        success {
            echo 'This will run only if successful'
        }
    }
}


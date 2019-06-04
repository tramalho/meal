#!/bin/bash --login


pipeline {
    agent none

    stages {
        stage('Build') {
            agent { label 'slave-node​' }
            steps {
                echo 'Building..'
                sh '''
                '''
            }
        }
    }

    post {
        success {
            echo 'This will run only if successful'
        }
    }
}


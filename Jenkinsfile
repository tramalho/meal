node {
    stage('Checkout') {
        deleteDir()
        checkout scm
        sh 'cd fastlane'
    }
    stage('build') {
        sh 'fastlane buildApp'
    }
 }

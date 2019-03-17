node {
    stage('Checkout') {
        deleteDir()
        checkout scm
        sh 'cd fastlane'
    }
    stage('Build') {
        sh 'fastlane buildApp'
    }
    stage('Static Analyze') {
        sh 'fastlane staticAnalyze'
    }
    stage('Test and Coverage') {
        sh 'fastlane testAndCoverage'
        junit '**/result_report/*.xml'
    }
 }

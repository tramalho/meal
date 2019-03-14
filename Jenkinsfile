node {
    stage('Checkout') {
        deleteDir()
        checkout scm
        sh 'cd fastlane'
    }
    stage('build') {
        sh 'fastlane buildApp'
    }
    stage('Static Analyze') {
        sh 'fastlane staticAnalyze'
    }
    stage('Test and Coverage') {
        sh 'fastlane testAndCoverage'
    }
 }

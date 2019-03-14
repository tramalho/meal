node {
    stage('Checkout') {
        deleteDir()
        checkout scm
        sh 'cd fastlane'
    }
    stage('build') {
        sh 'bundle exec fastlane buildApp'
    }
    stage('Static Analyze') {
        sh 'bundle exec fastlane staticAnalyze'
    }
    stage('Test and Coverage') {
        sh 'bundle exec fastlane testAndCoverage'
    }
 }

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
       // publish html
       publishHTML target: [
           allowMissing: false,
           alwaysLinkToLastBuild: false,
           keepAll: true,
           reportDir: '',
           reportFiles: 'index.html',
           reportName: 'Unified Report'
       ]
    }
 }

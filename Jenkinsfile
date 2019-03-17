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
        publishReport('build/reports', 'lint-results-debug.html', 'Lint Report')
    }

    stage('Test and Coverage') {
        sh 'fastlane testAndCoverage'

       publishReport('build/result_report', 'index.html', 'Unified Report')
    }

    def publishReport(reportDirectory, reportFileName, reportName) {
       publishHTML target: [
           allowMissing: false,
           alwaysLinkToLastBuild: false,
           keepAll: true,
           reportDir: reportDirectory,
           reportFiles: reportFileName,
           reportName: reportName
       ]
    }
 }

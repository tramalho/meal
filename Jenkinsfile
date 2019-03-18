node {
    stage('Checkout') {
        deleteDir()
        checkout scm
        sh 'cd fastlane'
    }
    stage('Build') {
        sh 'fastlane buildApp'
    }
    stage('Lint Analyze') {
        sh 'fastlane staticAnalyze'
        androidLint pattern: '**/lint-results*.xml'
    }

    stage('Test and Coverage') {
        sh 'fastlane testAndCoverage'
        junit '**/test-results/test/*.xml'
        publishReport('build/result_report', 'index.html', 'Unified Report')
    }
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

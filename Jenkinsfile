#!/bin/bash -l

node {
    stage('Checkout') {
        deleteDir()
        checkout scm
    }
    stage('Config Env') {
        sh 'cd fastlane'
    }    
    stage('Build') {
//        sh 'sudo gem update --system'
//        sh "gem install unf_ext -v '0.0.7.6' --source https://rubygems.org/"
//        sh 'sudo gem uninstall fastlane'
        sh 'bundle update --bundler'        
        sh 'bundle install'
        sh 'bundle exec fastlane buildApp'
    }
    stage('Lint Analyze') {
        sh 'bundle exec fastlane staticAnalyze'
        androidLint pattern: '**/lint-results*.xml'
    }
    stage('Test and Coverage') {
        sh 'bundle exec fastlane testAndCoverage'
        // junit '**/test-results/test/*.xml'
        junit '**/build/test-results/**/*.xml'
        publishReport('build/result_report', 'index.html', 'Unified Report')
    }
    stage('SonarQube analysis') {
        sh './gradlew --info sonarqube'
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

@Library('my-library')_
pipeline {

    agent {

        label "DockerSlaveAP52"

    }

    tools {

        // Note: this should match with the tool name configured in your jenkins instance (JENKINS_URL/configureTools/)

        maven "maven"
        jdk "jdk1.8.0_131"

    }

    stages {
       stage("Build") {
            steps {
              mavenBuild();
            }
        }

       stage('Unitesting') {
            steps {
                 sonarRun()
            }
        }

        stage('Building and Pushing Container Image') {
            steps {
                cleanupApp()
                dockerBuild()
            }
        }

        stage("Deploying Application to Dev") {
          when {
    	  expression {
               return env.BRANCH_NAME == 'Prokarma-devops';
               }
             }
            steps {
               developmentVar()
               kubeDeploy("${env.NAMESPACE}", "${env.APPNAME}", "${env.SERVICEPORT}", "${env.ENVIRONMENT}")
            }
        }   
        stage('Approvals') {
            steps {
                approval()

            }
        }

        stage("Deploying Application to QA") {
            steps {
              qaVar()
              kubeDeploy("${env.NAMESPACE}", "${env.APPNAME}", "${env.SERVICEPORT}", "${env.ENVIRONMENT}")
            }
        }
    }
}

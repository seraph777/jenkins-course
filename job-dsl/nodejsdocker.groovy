job('NodeJS Docker example') {
    scm {
        git('git://github.com/seraph777/docker-demo.git') 
        {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('seraph777')
            node / gitConfigEmail('oliveira.jorgelalberto@gmail.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    stages {
        stage ('Build Image') {
            steps {
                dockerBuildAndPublish {
                    repositoryName('seraph777/docker-nodejs-demo')
                    tag('${GIT_REVISION,length=9}')
                    registryCredentials('dockerhub')
                    forcePull(false)
                    forceTag(false)
                    createFingerprints(false)
                    skipDecorate()
                }
            }
        }
        stage ('Mundo') {
            steps {
              sh 'echo "This is halloween !"'
            }
        }


    }
}

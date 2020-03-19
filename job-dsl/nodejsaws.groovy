job('NodeJS on AWS ECR') {
    scm {
        git('git://github.com/hambalihan/docker-demo.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('hambalihan')
            node / gitConfigEmail('hambalihani@gmail.com')
        }
    }
    triggers {
        scm('H/5 * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in 
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('docker-nodejs-demo')
            tag('${GIT_REVISION,length=9}')
            registryCredentials('AWSECR')
            forcePull(false)
            forceTag(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
}

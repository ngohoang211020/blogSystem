pipeline {
    agent {
        label 'hoang-ubuntu'
    }
    environment {
        appUser = "blogsystem"
        appName = "blog-system"
        appType = "jar"
        processName = "${appName}.${appType}"
        folderDeploy = "/datas/${appUser}"
        buildMavenScript = "mvn clean install -DskipTests=true"
        buildImageScript = "docker build . -t ${appUser}:latest"
        copyScript = "sudo cp target/${processName} ${folderDeploy}"
        permissionScript = "sudo chown -R ${appUser}. ${folderDeploy}"
        killScript = "sudo kill -9 \$(ps -ef| grep ${processName} | grep -v grep | awk '{print \$2}')"
      //runScript = 'sudo su ${appUser} -c "cd ${folderDeploy}; cloudinary.key=117784362653246 cloudinary.secret-key=XfcUsfz7LK7cdjj7Q5rf6BrxCEo java -jar ${processName} > nohup.out 2>&1 &"'
        dockerRunScript = "docker-compose up -d"
    }
    
    stages {
        stage('build maven') {
            steps {
                sh(script: """ ${buildMavenScript}  """, label: "Build with maven")
            }
        }
        stage('build image and push to registry') {
            steps {
                sh(script: """ ${buildScript}  """, label: "Build with maven")
            }
        }
        stage('deploy') {
            steps {
                sh(script: """ ${dockerRunScript}  """, label: "Run project")
            }
        }
    }
}
